package com.bruce.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description 节点的操作权限
 * @data 2020/10/6 13:23
 **/
public class AclDemo {

    private static final CuratorFramework curatorFramework;

    static {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.CONNECTION_STR)
                .connectionTimeoutMs(3000)
                .sessionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        curatorFramework.start(); // 启动
    }

    public static void main(String[] args) throws Exception {
//        demo1();
        demo2();
    }

    /**
     * 创建节点时添加操作权限
     *
     * @throws Exception
     */
    private static void demo1() throws Exception {
        List<ACL> list = new ArrayList<>();
//        ACL acl = new ACL(ZooDefs.Perms.READ,
//                new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));

//        ACL acl = new ACL(ZooDefs.Perms.READ,
//                new Id("world", "anyone"));

        ACL acl = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE,
                new Id("world", "anyone"));

        list.add(acl);
        curatorFramework.create().withMode(CreateMode.PERSISTENT).withACL(list).forPath("/auth", "abc".getBytes());
    }

    /**
     * 为创建完成的节点添加操作权限
     *
     * @throws Exception
     */
    private static void demo2() throws Exception {
        List<ACL> list = new ArrayList<>();
//        ACL acl = new ACL(ZooDefs.Perms.READ,
//                new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
        ACL acl = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE,
                new Id("world", "anyone"));

        list.add(acl);
//        curatorFramework.setACL().withACL(ZooDefs.Ids.CREATOR_ALL_ACL).forPath("/temp");
        curatorFramework.setACL().withACL(list).forPath("/temp");

    }

}
