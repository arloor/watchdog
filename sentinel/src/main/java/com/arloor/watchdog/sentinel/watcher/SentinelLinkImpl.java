package com.arloor.watchdog.sentinel.watcher;

import com.arloor.watchdog.thrift.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SentinelLinkImpl implements Link.Iface {
    private static final Logger log = LoggerFactory.getLogger(SentinelLinkImpl.class);

    @Override
    public Pong ping(Request body) throws TException {
        return new Pong(1, "done", Role.SENTINEL, new Info(List.of(new Target("localhost", 123, Role.SENTINEL, true))));
    }
}
