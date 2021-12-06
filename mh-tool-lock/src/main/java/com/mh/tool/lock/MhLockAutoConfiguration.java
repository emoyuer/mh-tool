package com.mh.tool.lock;

import com.mh.tool.lock.config.RedissonProperties;
import com.mh.tool.lock.impl.DistributedRedisLock;
import com.mh.tool.lock.utils.MhLockUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author: yjx
 * @createTime: 2021/1/5
 * @description:
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
@ComponentScan({"com.mh.tool.lock"})
public class MhLockAutoConfiguration {

    @Autowired
    private RedissonProperties redissonProperties;

    /**
     * 哨兵模式自动装配
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    RedissonClient redisson() {
        Config config = new Config();

        //哨兵模式
        if (!StringUtils.isEmpty(redissonProperties.getMasterName())) {
            SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddresses())
                    .setMasterName(redissonProperties.getMasterName())
                    .setTimeout(redissonProperties.getTimeout())
                    .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                    .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());
            if (!StringUtils.isEmpty(redissonProperties.getPassword())) {
                serverConfig.setPassword(redissonProperties.getPassword());
            }
        }

        //单机模式
        if (!StringUtils.isEmpty(redissonProperties.getAddress())) {
            SingleServerConfig serverConfig = config.useSingleServer()
                    .setAddress(redissonProperties.getAddress())
                    .setTimeout(redissonProperties.getTimeout())
                    .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                    .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
            if (!StringUtils.isEmpty(redissonProperties.getPassword())) {
                serverConfig.setPassword(redissonProperties.getPassword());
            }
        }

        //集群模式
        if (!StringUtils.isEmpty(redissonProperties.getClusters())) {
            String[] nodes = redissonProperties.getClusters().split(",");
            //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = "redis://" + nodes[i];
            }
            //用集群server
            ClusterServersConfig clusterServersConfig = config.useClusterServers()
                    //设置集群状态扫描时间
                    .setScanInterval(2000)
                    .addNodeAddress(nodes);
            if (!StringUtils.isEmpty(redissonProperties.getPassword())) {
                clusterServersConfig.setPassword(redissonProperties.getPassword());
            }
        }
        return Redisson.create(config);
    }

    /**
     * 装配locker类，并将实例注入到MhLockUtil中
     *
     * @return
     */
    @Bean
    DistributedLock distributedLocker() {
        DistributedLock locker = new DistributedRedisLock();
        MhLockUtil.setLocker(locker);
        return locker;
    }

}
