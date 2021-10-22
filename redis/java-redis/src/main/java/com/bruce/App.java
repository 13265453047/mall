package com.bruce;

import com.bruce.util.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class App {


    private static final String KEY = "key";

    //总库存
    private static long nKuCuen = 0;
    //商品key名字
    private static String shangpingKey = "computer_key";
    //获取锁的超时时间 秒
    private static int timeout = 2 * 1000;

    private static JedisUtil jedis;

    public static void main(String[] args) {
//        String value = "1";
//
//        JedisUtil jedis = JedisUtil.getInstance();
//        boolean setResult = jedis.setnx(KEY, value);
//        System.out.println("setResult = " + setResult);
//
//        int delResult = jedis.delnx(KEY, value);
//        System.out.println("delResult = " + delResult);


        // 构造很多用户
        List<String> users = new ArrayList<>(100001);
        IntStream.range(0, 1000).parallel().forEach(b -> users.add("神牛-" + b));

        // 初始化库存
        nKuCuen = 5;

        jedis = JedisUtil.getInstance();
        // 抢到商品的用户
        List<String> shopUsers = new ArrayList<>();

        qiang(shopUsers, users);

        System.out.println("shopUsers = " + shopUsers);
    }

    /**
     * 模拟开抢
     *
     * @param shopUsers : 强到的人
     * @param users     ： 需要抢的人
     * @return
     */
    private static void qiang(List<String> shopUsers, List<String> users) {
        // 未抢到商品的用户，继续争抢
        List<String> unShopUsers = new ArrayList<>();

        if (nKuCuen <= 0) {
            return;
        }

        // 模拟开抢
        users.parallelStream().forEach(b -> {
            String shopUser = doQiang(b, unShopUsers);
            if (shopUser.length() > 0) {
                shopUsers.add(shopUser);
            }
        });

        if (unShopUsers.size() > 0 && nKuCuen > 0) {
            qiang(shopUsers, unShopUsers);
        }
    }

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * 模拟抢单动作
     *
     * @param b
     * @param unShopUsers
     * @return
     */
    private static String doQiang(String b, List<String> unShopUsers) {
        // 用户开抢时间
        long startTime = System.currentTimeMillis();

        // 未抢到的情况下，30秒内继续获取锁
        while ((startTime + timeout) >= System.currentTimeMillis()) {
            // 商品是否剩余
            if (nKuCuen <= 0) {
                break;
            }
            /**
             * 此处设置锁过期时间若是小于抢单后执行时间
             * 会存在多个线程同时获取锁，存在并发安全问题，多扣库存
             */
            if (jedis.setnx(shangpingKey, b, 1000 * 2)) {
                // 用户b拿到锁
                System.out.println("用户" + b + "拿到锁..." + Thread.currentThread().getName());
                try {
                    // 商品是否剩余
                    if (nKuCuen <= 0) {
                        break;
                    }

                    // 以Redis技术实现的Redisson使用的延期手段叫看门狗的一种技术名称
                    executor.submit(() -> {
                        // 通过某种机制检测当前抢单的线程目前还在执行逻辑中国，
                        // 若发现redis中的key达到阈值，就进行延期处理
                        // TODO

                    });

                    // 模拟生成订单耗时操作，方便查看：神牛-50 多次获取锁记录
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 抢购成功，商品递减，记录用户
                    nKuCuen -= 1;

                    // 抢单成功跳出
                    System.out.println("用户" + b + "抢单成功跳出...所剩库存：" + nKuCuen);

                    return b + "抢单成功，所剩库存：" + nKuCuen;
                } finally {
                    System.out.println("用户 " + b + "释放锁...");
                    // 释放锁
                    jedis.delnx(shangpingKey, b);
                }
            } else {
                //用户b没拿到锁，在超时范围内继续请求锁，不需要处理
//                if (b.equals("神牛-50") || b.equals("神牛-69")) {
//                    logger.info("用户{}等待获取锁...", b);
//                }
            }
        }
        unShopUsers.add(b);
        return "";
    }

}
