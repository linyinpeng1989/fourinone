package e_分布式文件系统.a_获取集群文件方式;

import com.fourinone.BeanContext;

/**
 * Created by linyp on 2017/3/5.
 */
public class FttpServer {
    public static void main(String[] args) {
        BeanContext.startFttpServer(args[0]);
    }
}
