package a_分布式并行计算.k_工人服务化模式demo;

import com.fourinone.BeanContext;

/**
 * Created by linyp on 2017/2/21.
 * 工人服务化模式应用（config.xml中工人模块部分，设置为<SERVICE>true</SERVICE>，
 * 保证工人可以对外提供多个客户端的请求服务）
 */
public class ParkServerDemo {
    public static void main(String[] args) {
        BeanContext.startPark();
    }
}
