package com.yusys.agile.redis.config;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.List;

/**
 * @description redis配置类
 *  
 * @date 2020/04/26
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisCacheConfiguration {

    private static final String REDISSON_NODES_PREFIX = "redis://";
    @Autowired
    private RedisProperties redisProperties;

    @Configuration
    @ConditionalOnClass({Redisson.class})
    protected class RedissonConfiguration {
        /**
         * @description redisson哨兵模式客户端
         * @return org.redisson.api.RedissonClient
         */
        @Bean
        RedissonClient redissonSentinel() {
            List<String> nodes = redisProperties.getSentinel().getNodes();
            List<String> addressList = getNodeAddresses(nodes);
            String[] addresses = new String[addressList.size()];
            addressList.toArray(addresses);
            Config config = new Config();
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers()
                .setMasterName(redisProperties.getSentinel().getMaster())
                .addSentinelAddress(addresses)
                .setTimeout(Integer.parseInt(String.valueOf(redisProperties.getTimeout().toMillis())));//Redis server response timeout
            String password = redisProperties.getPassword();
            if (StringUtils.isNotBlank(password)) {
                sentinelServersConfig.setPassword(password);
            }
            return Redisson.create(config);
        }

        /**
         * @description redisson集群模式客户端
         * @return org.redisson.api.RedissonClient
         */
        /*@Bean
        RedissonClient redissonCluster() {
            List<String> nodes = redisProperties.getCluster().getNodes();
            List<String> addressList = getNodeAddresses(nodes);
            String[] addresses = new String[addressList.size()];
            addressList.toArray(addresses);
            Config config = new Config();
            ClusterServersConfig clusterServersConfig = config.useClusterServers()
                    .addNodeAddress(addresses)
                    .setTimeout(Integer.parseInt(String.valueOf(redisProperties.getTimeout().toMillis())));
            String password = redisProperties.getPassword();
            if (StringUtils.isNotBlank(password)) {
                clusterServersConfig.setPassword(password);
            }
            return Redisson.create(config);
        }*/
    }

    /**
     * @description 查询节点
     * @param nodes
     * @return java.util.List
     */
    private List<String> getNodeAddresses(List<String> nodes) {
        List<String> addressList = Lists.newArrayList();
        Iterator<String> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            String node = iterator.next();
            if (!node.startsWith(REDISSON_NODES_PREFIX)) {
                node = REDISSON_NODES_PREFIX + node;
            }
            addressList.add(node);
        }
        return addressList;
    }
}
