package c_分布式缓存.a_分布式缓存;

import com.fourinone.BeanContext;

/**
 * Created by linyp on 2017/2/27.
 */
public class CacheFacadeDemo {
    public static void main(String[] args) {
        // 启动facade，根据读写请求自动判断数据落到后端哪台CacheServer上
        BeanContext.startCacheFacade();
        System.out.println("CacheFacade is ok ...");
    }
}
