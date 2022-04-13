# 工程简介
基于ShardingSphere-JDBC + Spring Boot + MyBatis 实现简单的读写分离。

```
JDK 1.8
Spring Boot 2.3.7.RELEASE
# mybatis-plus-boot-starter 3.1.0 (mybatis、mybatis-spring)
mybatis-spring-boot-starter 2.1.3 (mybatis、mybatis-spring)
ShardingSphere-JDBC 4.1.1
```

配置主从数据库，保证读请求到从节点，写请求到主节点，使用的连接池是 Spirng Boot 2.x 默认的 hikari
> 从整体性能上看 `hikari` 较 `Druid` 高些，但整体的功能不如 `Druid` 全面


# MySQL 数据库主从配置
```text
首先思考一个问题:在高并发的场景中,关于数据库都有哪些优化的手段？
常用的有以下的实现方法:读写分离、加缓存、主从架构集群、分库分表等，
在互联网应用中,大部分都是读多写少的场景,设置两个库,主库和读库,主库的职能是负责写,从库主要是负责读,
可以建立读库集群,通过读写职能在数据源上的隔离达到减少读写冲突、释压数据库负载、保护数据库的目的。
在实际的使用中,凡是涉及到写的部分直接切换到主库，读的部分直接切换到读库，这就是典型的读写分离技术。
```

```text
主从同步的局限性：
这里分为主数据库和从数据库,主数据库和从数据库保持数据库结构的一致,
主库负责写,当写入数据的时候,会自动同步数据到从数据库；
从数据库负责读,当读请求来的时候,直接从读库读取数据,主数据库会自动进行数据复制到从数据库中。
不过本篇博客不介绍这部分配置的知识,因为它更偏运维工作一点。
这里涉及到一个问题:
主从复制的延迟问题,当写入到主数据库的过程中,突然来了一个读请求,而此时数据还没有完全同步,
就会出现读请求的数据读不到或者读出的数据比原始值少的情况。具体的解决方法最简单的就是将读请求暂时指向主库,但是同时也失去了主从分离的部分意义。
也就是说在严格意义上的数据一致性场景中,读写分离并非是完全适合的,注意更新的时效性是读写分离使用的缺点。
```






# 延伸阅读
ShardingSphere 当前版本是 4.x，官网地址：https://shardingsphere.apache.org/index_zh.html
https://www.cnblogs.com/xiaoyuxixi/p/15484001.html

# 总结
1. 主从配置+读写分离可以很大程度上保证系统高可用性和整体性能，而且也是互联网应用的基础入门配置。
MySQL 可以利用 bin-log 实现主从同步，master 节点采用推方式向 slave 节点推送写入操作形成的日志，
slave 节点会将日志先写到本地的 relay-log 中，然后再写入数据库中，此过程中还会更新 master.info 文件，
记录本地同步的位置，以便下次增量同步。

2. 数据库层配置完成，最后要在应用中进行读写配置，
具体的配置可以公司内部自行开发或者采用比较稳定的开源框架，
本文采用的是 ShardingSphere-JDBC，只是个代理客户端，其原理就是分析 SQL 语句判断是读还是写，从而分发到不同的节点。

3. 如果需要更复杂的操作，比如跨库 join 等，那就需要代理中间件了，比如 ShardingSphere-Proxy 等。