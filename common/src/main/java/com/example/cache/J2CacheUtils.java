package com.example.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  author:Liumq
 */
public class J2CacheUtils {
    /**
     * 系统缓存
     */
    private static String SYS_CACHE_NAME = "sysCache";


    private static final ConcurrentHashMap<String, Object> CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 获取SYS_CACHE_NAME缓存
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        return get(SYS_CACHE_NAME, key);
    }

    /**
     * 写入SYS_CACHE_NAME缓存
     *
     * @param key
     * @return
     */
    public static void put(String key, Object value) {
        put(SYS_CACHE_NAME, key, value);
    }

    /**
     * 从SYS_CACHE_NAME缓存中移除
     *
     * @param key
     * @return
     */
    public static void remove(String key) {
        remove(SYS_CACHE_NAME, key);
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static Object get(String cacheName, String key) {
        return CACHE_MAP.get(toKey(cacheName, key));
    }


    private static String toKey(String cacheName, String key) {
        return String.format("%s-%s", cacheName, key);
    }

    /**
     * 写入缓存
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public static void put(String cacheName, String key, Object value) {
        CACHE_MAP.put(toKey(cacheName, key), value);
    }

    /**
     * 从缓存中移除
     *
     * @param cacheName
     * @param key
     */
    public static void remove(String cacheName, String key) {
        CACHE_MAP.remove(toKey(cacheName, key));
    }

    /**
     * 获取SYS_CACHE缓存的所有key
     *
     * @return
     */
    public static Collection<String> keys() {
        return keys(SYS_CACHE_NAME);
    }
    /**
     * 获取缓存的所有key
     *
     * @param cacheName
     * @return
     */
    public static Collection<String> keys(String cacheName) {
        Collection<String> cal = new HashSet();
        String prefix = cacheName + "_";
        for (String s : CACHE_MAP.keySet()) {
            if (s.startsWith(prefix)) {
                cal.add(s);
            }
        }
        return cal;
    }

    /**
     * Clear the cache
     *
     * @param cacheName: Cache region name
     */
    public static void clear(String cacheName) {
        Iterator<ConcurrentHashMap.Entry<String, Object>> it = CACHE_MAP.entrySet().iterator();
        String prefix = cacheName + "_";
        Map.Entry<String, Object> entry;
        while(it.hasNext()) {
            entry = it.next();
            if (entry.getKey().startsWith(prefix))
                it.remove();//使用迭代器的remove()方法删除元素
        }
    }

    /**
     * 判断某个缓存键是否存在
     *
     * @param region Cache region name
     * @param key    cache key
     * @return true if key exists
     */
    public static boolean exists(String key) {
        return CACHE_MAP.containsKey(toKey(SYS_CACHE_NAME, key));
    }

}
