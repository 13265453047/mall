package com.bruce.juc.chm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * CHM的使用
 *
 * @author rcy
 * @version 1.0.0
 * @className: ChmExample
 * @date 2023-03-30
 */
public class ChmExample {

    // 浏览量统计
    private static ConcurrentMap<String, Integer> Liu_lan_count = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        mergeDemo();
    }

    /**
     * ConcurrentMap 操作的原子性处理
     *
     * @param key
     */
    public static void statisticCount(String key) {

        // 方式一：
        synchronized (ChmExample.class) {
            Integer count = Liu_lan_count.get(key);
            if (count != null) {
                Liu_lan_count.put(key, count + 1);
            } else {
                Liu_lan_count.put(key, 1);
            }
        }


        // 方式二：
        Liu_lan_count.merge(key, 1, Integer::sum);

        // 方式三：

        // 若果key不存在，则调用后面的mappingFunction计算，把计算的返回值作为value
        Liu_lan_count.computeIfAbsent(key, t -> 1);
        // 若果key不存在，将value储存，并返回value
        Liu_lan_count.putIfAbsent(key, 1);

        // 若果key存在，则修改，如果不存在，则返回null
        Liu_lan_count.computeIfPresent(key, (k, v) -> v + 1);

        // 方式四：
        Liu_lan_count.compute(key, (k, v) -> v + 1);
    }


    /**
     * merge的使用
     */
    public static void mergeDemo() {
        ConcurrentMap<Integer, Integer> map = new ConcurrentHashMap<>();

        map.put(7, 7);

        Stream.of(1, 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8).forEach(
                t -> map.merge(t, 5, Integer::sum)
        );

        System.out.println(map);

    }

}
