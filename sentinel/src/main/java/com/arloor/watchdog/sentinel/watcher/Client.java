package com.arloor.watchdog.sentinel.watcher;

import com.arloor.watchdog.sentinel.exception.ClientAbortException;
import com.arloor.watchdog.thrift.Link;
import com.arloor.watchdog.thrift.Request;
import com.arloor.watchdog.thrift.Response;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class Client implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private final TSocket transport;
    private final Link.Client thriftClient;
    private AtomicBoolean closed = new AtomicBoolean(false);

    public Client(String host, int port) throws TTransportException {
        transport = new TSocket(host, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        thriftClient = new Link.Client(protocol);
    }

    @Override
    public void close() {
        transport.close();
        closed.set(true);
    }

    public Response ping() throws ClientAbortException {
        if (closed.get()) {
            throw new ClientAbortException();
        }
        try {
            return thriftClient.ping(new Request(1, ""));
        } catch (TTransportException e) {
            log.error("socket unlink!", e);
            closed.set(true);
            throw new ClientAbortException();
        } catch (TProtocolException e) {
            log.error("wrong protocol", e);
        } catch (TApplicationException e) {
            log.error("应用层错误", e);
        } catch (TException e) {
            log.error("其他错误", e);
        }
        return null;
    }

    public static void main(String[] args) {
        final ExecutorService pool = Executors.newFixedThreadPool(5);
        IntStream.range(0, 5).forEach(t -> {
            pool.execute(() -> {
                try (Client localhost = new Client("localhost", 9090)) {
                    for (int i = 0; i < 5; i++) {
                        Response pong = localhost.ping();
                        log.info("{}", pong);
                    }
                } catch (TTransportException e) {
                    log.error("cannot connect");
                } catch (ClientAbortException e) {
                    log.error("client abort");
                }
            });
        });


        pool.shutdown();
        try {
            final boolean termination = pool.awaitTermination(100, TimeUnit.SECONDS);
            if (!termination) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
