package c_分布式缓存.a_分布式缓存;

import com.fourinone.BeanContext;
import com.fourinone.CacheLocal;
import com.fourinone.ParkLocal;

/**
 * Created by linyp on 2017/2/27.
 */
public class CachePutDemo {
    public static void putSmallCache(String[] keyArray) {
        ParkLocal pl = BeanContext.getPark();
        // 创建domain/node
        pl.create("cache", "keyArray", keyArray);
    }

    public static String[] putBigCache() {
        CacheLocal cc = BeanContext.getCache();
        String[] keyArray = new String[100];
        for (int i = 0; i < 100; i++) {
            keyArray[i] = cc.add("key", "value" + i);
        }
        return keyArray;
    }

    public static void main(String[] args) {
        String[] keyArray = putBigCache();
        putSmallCache(keyArray);
    }
}
