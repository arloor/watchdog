package com.arloor.watchdog.sentinel;

import com.arloor.watchdog.Worker;
import com.arloor.watchdog.sentinel.watcher.Client;
import com.arloor.watchdog.sentinel.watcher.SentinelLinkImpl;
import com.arloor.watchdog.thrift.Response;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.arloor.watchdog.sentinel.Application.NodeFlag.CLUSTER_NODE_PFAIL;

@SpringBootApplication
public class Application {
    private static long iteration = 0;
    private static String ip = "";
    private static final long handshake_timeout = 1000;

    private static final Map<String, ClusterNode> nodes = new ConcurrentHashMap<>();
    private static int statsPfailNodes = 0;
    private static final int cluster_node_timeout = 20000;
    private static Map<String, Object> meta = new HashMap<>();

    private enum NodeFlag {
        CLUSTER_NODE_MYSELF,
        CLUSTER_NODE_NOADDR,
        CLUSTER_NODE_PFAIL,
    }

    public static final class ClusterNode {
        private final Set<NodeFlag> flags = ConcurrentHashMap.newKeySet();
        private Client client;

        public Client getClient() {
            return client;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws TException, InterruptedException {
        Worker.serveAsync(9090, 32, new SentinelLinkImpl());
        Thread.sleep(100);
        Client.main(args);
        System.exit(0);
//        SpringApplication.run(Application.class, args);
    }

    public static final void clusterCron() {
        iteration++;
        // todo:获取外部ip
        // todo:随机ping一台机器

        // 如果
        for (Map.Entry<String, ClusterNode> entry : nodes.entrySet()) {
            final ClusterNode node = entry.getValue();
            final Response pong = node.getClient().ping();
            if (pong.getStatus()!=200){
                node.flags.add(CLUSTER_NODE_PFAIL);
            }
        }
    }

}

