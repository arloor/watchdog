package com.arloor.watchdog.sentinel.status;

import java.util.Objects;
import java.util.Set;

public class Info {
    private String host;
    private String port;
    private boolean active;
    private Set<String> failReports;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return Objects.equals(host, info.host) && Objects.equals(port, info.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
