package c_分布式缓存.a_分布式缓存;

import com.fourinone.BeanContext;

/**
 * Created by linyp on 2017/2/27.
 */
public class CacheServer {
    public static void main(String[] args) {
        String[][] cacheServerA = new String[][]{{"localhost","2000"}, {"localhost", "2001"}};
        String[][] cacheServerB = new String[][]{{"localhost","2002"}, {"localhost", "2003"}};
        String[][] cacheServerC = new String[][]{{"localhost","2004"}, {"localhost", "2005"}};

        String[][] cacheServer = null;
        if (args[0].equals("A"))
            cacheServer= cacheServerA;
        else if (args[0].equals("B"))
            cacheServer = cacheServerB;
        else if (args[0].equals("C"))
            cacheServer = cacheServerC;

        // 如果使用配置文件配置地址端口，则直接使用BeanContext.startCache();启动
        BeanContext.startCache(cacheServer[0][0], Integer.parseInt(cacheServer[0][1]), cacheServer);
    }
}
