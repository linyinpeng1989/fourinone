package a_分布式并行计算.k_工人服务化模式demo;

import com.fourinone.BeanContext;
import com.fourinone.StartResult;

/**
 * Created by linyp on 2017/2/25.
 */
public class ClientMain {
    public static void main(String[] args) {
        // 同时启动2个客户端调用Hello服务，将结果输出到相应日志
        StartResult<Integer> ctor1 = BeanContext.tryStart("java", "-cp", "fourinone.jar;", "CtorClient", "client1");
        ctor1.print("log/ctor1.log");
        StartResult<Integer> ctor2 = BeanContext.tryStart("java", "-cp", "fourinone.jar;", "CtorClient", "client2");
        ctor2.print("log/ctor2.log");
    }
}
