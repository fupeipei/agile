package com.yusys.agile.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FdfsProperties {
    @Value("${fdfs.connect_timeout}")
    private String connectTimeout;
    @Value("${fdfs.network_timeout}")
    private String networkTimeout;
    @Value("${fdfs.charset}")
    private String charset;
    @Value("${fdfs.http.tracker_http_port}")
    private String trackerHttpPort;
    @Value("${fdfs.http.anti_steal_token}")
    private String antiStealToken;
    @Value("${fdfs.http.secret_key}")
    private String secretKey;
    @Value("${fdfs.tracker_server}")
    private String trackerServer;

    public String getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getNetworkTimeout() {
        return networkTimeout;
    }

    public void setNetworkTimeout(String networkTimeout) {
        this.networkTimeout = networkTimeout;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTrackerHttpPort() {
        return trackerHttpPort;
    }

    public void setTrackerHttpPort(String trackerHttpPort) {
        this.trackerHttpPort = trackerHttpPort;
    }

    public String getAntiStealToken() {
        return antiStealToken;
    }

    public void setAntiStealToken(String antiStealToken) {
        this.antiStealToken = antiStealToken;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTrackerServer() {
        return trackerServer;
    }

    public void setTrackerServer(String trackerServer) {
        this.trackerServer = trackerServer;
    }

}
