package com.yusys.agile.versionmanager.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExternalSystemAPIConfigBean {

    private Map<String, String> configMap = new ConcurrentHashMap<>();

    public synchronized Map<String, String> getConfigMap() {
        return configMap;
    }

    public synchronized void setConfigMap(Map<String, String> configMap) {
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            this.configMap.put(entry.getKey(), entry.getValue());
        }
    }
}
