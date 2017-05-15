package a_分布式并行计算.l_实时流计算demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

/**
 * Created by linyp on 2017/2/25.
 */
public class StreamCtorA extends Contractor {
    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = super.getWaitingWorkers("StreamWorkerA");
        System.out.println("wks.length: " + wks.length);

        WareHouse result = wks[0].doTask(inhouse);
        while (true) {
            if (result.getStatus() != WareHouse.NOTREADY)
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        StreamCtorA sc = new StreamCtorA();
        for (int i = 0; i < 10; i++) {
            WareHouse msg = new WareHouse();
            msg.put("msg", "hello" + i);
            WareHouse wh = sc.giveTask(msg);
            System.out.println(wh);
        }
        sc.exit();
    }
}
