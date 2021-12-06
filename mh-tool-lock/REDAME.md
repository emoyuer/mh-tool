## 关于commons-lock 使用

## 引入依赖包

``` xml
        <dependency>
            <groupId>com.mh.tool</groupId>
            <artifactId>mh-tool-lock</artifactId>
            <version>1.0.0</version>
        </dependency>
```

## 添加配置文件yml

``` yaml
# redisson lock 单机模式
redisson:
  address: redis://10.0.254.172:6379
  password: 
  
# 哨兵模式
redisson:
  master-name: master
  password:
  sentinel-addresses: 10.47.91.83:26379,10.47.91.83:26380,10.47.91.83:26381
  
# 集群模式	
# redisson lock 集群模式
redisson:
  clusters: 10.10.1.1:7000,10.10.1.1:7001,10.10.1.1:7002,10.10.1.1:7003,10.10.1.1:7004,10.10.1.1:7005
  password:
```



## 使用规则

### 1.注解方式

``` yaml
  * 在需要使用分布式锁的方法上添加注解  @MhLock(lockKey = "#id") 
  * 注解中各个参数详解:
     *  lockKey   key可以自定义也可以使用方法中的参数(遵循spring el表达式)
     *  lockPrefix   key前缀
     *  lockSuffix   后缀
     *  separator    分割符
     *  最后组成的key为lockPrefix+separator+lockKey+separator+lockSuffix
     *  tryLock 是否使用尝试锁  默认不适用
     *  waitTime  尝试锁的等待时间
     *  leaseTime  锁的超时时间默认30
     *  timeUnit   时间单位 默认为秒
```

### 2.直接使用工具类

``` java

try{
    #超时时间15秒
    MhLockUtil.lock(key,TimeUnit.SECONDS,15)
     #需要加锁处理的业务逻辑
     ......
    }finally{
       #执行完成解锁
      MhLockUtil.unlock(key)
    }
```



