package a_分布式并行计算.c_工人互相SayHello;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

public class HelloCtor extends Contractor {
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = getWaitingWorkers("helloworker");
        System.out.println("wks.length:" + wks.length);
        WareHouse wh = new WareHouse("word", "hello, i am your Contractor.");
        // 通过doTaskBatch进行批量任务处理，需要等待每个工人都执行完毕才返回结果（阻塞）
        WareHouse[] hmarr = doTaskBatch(wks, wh);

        // 打印每个工人处理结果
        for (WareHouse result : hmarr)
            System.out.println(result);

        return null;
    }

    public static void main(String[] args) {
        HelloCtor a = new HelloCtor();
        a.giveTask(null);
        // 为了节省资源利用，工头运行结束后不会退出jvm，需要使用exit方法强行退出
        a.exit();
    }
}