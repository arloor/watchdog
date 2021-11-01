package com.arloor.watchdog;

import com.arloor.watchdog.thrift.Link;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker {
    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    public static final void serve(int port, int numThreads, Link.Iface impl) {
        try {
            TServerTransport serverTransport = new TServerSocket(port);
            Link.Processor<Link.Iface> processor = new Link.Processor<Link.Iface>(impl);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                    .minWorkerThreads(numThreads)
                    .maxWorkerThreads(numThreads)
                    .processor(processor)
            );
            log.info("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static final void serveAsync(int port, int numThreads,Link.Iface impl) {
        new Thread(() -> serve(port, numThreads,impl), "watch-dog-worker").start();
    }
}
