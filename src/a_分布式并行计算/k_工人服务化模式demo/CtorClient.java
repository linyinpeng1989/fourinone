package a_分布式并行计算.k_工人服务化模式demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

/**
 * Created by linyp on 2017/2/25.
 */
public class CtorClient extends Contractor {
    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        // 获取提供Hello服务的地址，这里假设只有一个，但可以是多个
        WorkerLocal[] wks = super.getWaitingWorkers("HelloServices");
        System.out.println("wks.length: " + wks.length);

        // 请求服务并传入InputString参数， 然后等待结果完成后返回
        WareHouse[] results = super.doTaskBatch(wks, inhouse);
        return results[0];
    }

    public String sayHello(String name) {
        // 封装输入参数InputString，然后实际调用通用服务接口
        WareHouse result = this.giveTask(new WareHouse("InputString", name));
        // 返回服务请求的结果
        return result.getString("Result");
    }

    public static void main(String[] args) {
        CtorClient a = new CtorClient();
        String serviceResult = a.sayHello(args[0]);
        System.out.println(serviceResult);
        a.exit();
    }
}
