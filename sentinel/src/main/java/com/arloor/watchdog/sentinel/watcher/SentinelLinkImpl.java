package com.arloor.watchdog.sentinel.watcher;

import com.arloor.watchdog.thrift.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SentinelLinkImpl implements Link.Iface {
    private static final Logger log = LoggerFactory.getLogger(SentinelLinkImpl.class);

    @Override
    public Response ping(Request body) throws TException {
        return new Response(1, "success", List.of(new Report("localhost", 100, true)));
    }
}
