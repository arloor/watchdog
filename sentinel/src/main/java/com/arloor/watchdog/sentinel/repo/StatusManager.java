package com.arloor.watchdog.sentinel.repo;

import com.arloor.watchdog.sentinel.status.Epoch;
import com.arloor.watchdog.sentinel.status.Info;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StatusManager {
    private Epoch epoch=new Epoch();
    private Set<Info> infos=new HashSet<>();

}
