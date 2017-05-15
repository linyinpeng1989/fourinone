package a_分布式并行计算.a_分布式计算上手demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

public class SimpleCtor extends Contractor {
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = getWaitingWorkers("simpleworker");
        System.out.println("wks.length:" + wks.length);

        WareHouse wh = new WareHouse("word", "hello");
        WareHouse result = wks[0].doTask(wh);

        // 校验任务是否完成
        while (true) {
            if (result.getStatus() == WareHouse.READY) {
                System.out.println("result:" + result);
                break;
            }
        }
        // 任务不需要返回值
        return null;
    }

    public static void main(String[] args) {
        SimpleCtor a = new SimpleCtor();
        a.giveTask(null);
    }
}