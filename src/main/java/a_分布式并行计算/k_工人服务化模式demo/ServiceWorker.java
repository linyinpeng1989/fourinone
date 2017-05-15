package a_分布式并行计算.k_工人服务化模式demo;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

/**
 * Created by linyp on 2017/2/25.
 */
public class ServiceWorker extends MigrantWorker {
    @Override
    protected WareHouse doTask(WareHouse inhouse) {
        // 取出参数的值
        String inputStr = inhouse.getString("InputString");
        // 收到服务请求后，返回hello
        return new WareHouse("Result", inputStr + ", hello!");
    }

    public static void main(String[] args) {
        ServiceWorker wm = new ServiceWorker();
        // 启动服务，设置Worker部分配置 <SERVICE>true</SERVICE>
        wm.waitWorking("localhost", Integer.parseInt(args[0]), "HelloService");
    }
}
