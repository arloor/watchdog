package com.arloor.node;

import com.arloor.watchdog.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        Worker.serve(9090,32,null);
    }
}
