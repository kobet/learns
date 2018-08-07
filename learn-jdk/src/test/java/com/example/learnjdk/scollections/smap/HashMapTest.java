/**
 * @copyright remark holdings
 */
package com.example.learnjdk.scollections.smap;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kobe_t
 * @date 2018/7/2 17:39
 */
public class HashMapTest {

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    @Test
    public void test() {

        Map<String, String> map = new HashMap();
        // 同步的hashMap
        Map<String, String> synchionzedHashMap = Collections.synchronizedMap(map);
        map.put("1", "a");

        System.out.println(1 << 2);
        System.out.println(6 >>> 2);
        System.out.println(tableSizeFor(25555));

        System.out.println(4 | 4 >>> 1);

        System.out.println(8 & 4);
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * Returns index for hash code h.
     */
    private static int indexFor(int h, int length) {
        return h & (length - 1);
    }
}
