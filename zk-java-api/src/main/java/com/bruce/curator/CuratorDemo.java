package com.bruce.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description Curator操作ZK的CRUD
 * @data 2020/10/6 10:22
 **/
public class CuratorDemo {

    // zk集群服务器地址
    private static final String CONNECTION_STR = "192.168.1.11:2181";

    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECTION_STR)
                .connectionTimeoutMs(3000)
                .sessionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        curatorFramework.start(); // 启动

        try {
            // 创建临时有序节点
            for (int i = 0; i < 10; i++) {
                createNode(curatorFramework);
                TimeUnit.SECONDS.sleep(3);
            }

            // 创建节点数据
//            createNode(curatorFramework);

            // 修改节点数据
//            updateNode(curatorFramework);
            // 删除节点
//            deleteNode(curatorFramework);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建节点
     *
     * @param curatorFramework
     */
    private static void createNode(CuratorFramework curatorFramework) throws Exception {
        // 设置节点的操作权限
//        List<ACL> aclList = Collections.singletonList(
//                new ACL(ZooDefs.Perms.READ, new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")))
//        );

//        List<ACL> aclList = Collections.singletonList(
//                new ACL(ZooDefs.Perms.READ, new Id("ip", "192.168.1.7"))
//        );

        List<ACL> aclList = ZooDefs.Ids.OPEN_ACL_UNSAFE;

        /**
         * {@link ZooDefs.Perms} : 节点的可操作权限
         *
         * Scheme : new Id("scheme","");对节点的访问限制
         * ip：基于ip来限制访问ip
         * digest：基于用户密码字符串来限制
         * world：是开放的，没有限制
         * super：超级权限
         */

        // 返回创建当前节点
        String createNode = curatorFramework.create()
                // 连同父节点一起创建
                .creatingParentsIfNeeded()

                // 创建持久有序节点，临时有序节点不能创建子节点
//                .withMode(CreateMode.PERSISTENT_SEQUENTIAL)

                // 创建临时有序节点
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)

                // 设置节点的操作权限
                .withACL(aclList)

                // 创建节点路径
                .forPath("/temp", "123".getBytes());

        System.out.println(createNode);

        List<String> childrenNode = curatorFramework.getChildren().forPath("/bruce");

        System.out.println(childrenNode);

    }

    /**
     * 修改节点值
     *
     * @param curatorFramework
     */
    private static void updateNode(CuratorFramework curatorFramework) throws Exception {
        Stat stat = curatorFramework.setData()
                // 设置节点值
                .forPath("/bruce", "abc".getBytes());

        System.out.println(stat);
    }

    /**
     * 删除节点
     *
     * @param curatorFramework
     * @throws Exception
     */
    private static void deleteNode(CuratorFramework curatorFramework) throws Exception {
        Stat stat=new Stat();
        String value=new String(curatorFramework.getData().storingStatIn(stat).forPath("/data"));

        System.out.println(value);

        curatorFramework.delete().withVersion(stat.getVersion()).forPath("/data");
    }

}
