package com.yusys.agile.config;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class FdfsClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(FdfsClientConfig.class);
    @Resource
    private FdfsProperties fdfsProperties;

    @Bean
    public StorageClient storageClient() {
        Properties properties = new Properties();
//        properties.put(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, fdfsProperties.getConnectTimeout());
//        properties.put(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, fdfsProperties.getNetworkTimeout());
//        properties.put(ClientGlobal.PROP_KEY_CHARSET, fdfsProperties.getCharset());
//        properties.put(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT, fdfsProperties.getTrackerHttpPort());
//        properties.put(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN, fdfsProperties.getAntiStealToken());
//        properties.put(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY, fdfsProperties.getSecretKey());
//        properties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, fdfsProperties.getTrackerServer());
        TrackerClient trackerClient = null;
        StorageServer storageServer = null;
        StorageClient storageClient = null;
        try {
            ClientGlobal.initByProperties(properties);
            logger.info("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());
            trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (IOException e) {
            logger.error("fdfs client config error:{}", e);
        } catch (MyException e) {
            logger.error("fdfs client config error:{}", e);
        }
        return storageClient;
    }
}
