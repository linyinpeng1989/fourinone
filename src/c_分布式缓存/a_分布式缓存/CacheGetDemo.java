package c_分布式缓存.a_分布式缓存;

import com.fourinone.BeanContext;
import com.fourinone.CacheLocal;
import com.fourinone.ParkLocal;

import java.util.Arrays;

/**
 * Created by linyp on 2017/2/27.
 */
public class CacheGetDemo {
    public static String[] getSmallCache() {
        ParkLocal pl = BeanContext.getPark();
        String[] keyArray = (String[]) pl.get("cache", "keyArray").toObject();
        System.out.println(Arrays.toString(keyArray));
        return keyArray;
    }

    public static void getBigCache(String[] keyArray) {
        CacheLocal cc = BeanContext.getCache();
        for (String k : keyArray) {
            System.out.println(cc.get(k, "key"));
        }
    }

    public static void main(String[] args) {
        String[] keyArray = getSmallCache();
        getBigCache(keyArray);
    }
}
