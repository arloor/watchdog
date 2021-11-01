package com.arloor.watchdog.sentinel;

import com.arloor.watchdog.Worker;
import com.arloor.watchdog.sentinel.watcher.Client;
import com.arloor.watchdog.sentinel.watcher.SentinelLinkImpl;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.event.ActionListener;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws TException, InterruptedException {
        Worker.serveAsync(9090, 32, new SentinelLinkImpl());
        Thread.sleep(100);
        Client.main(args);
        System.exit(0);
//        SpringApplication.run(Application.class, args);
    }

}

