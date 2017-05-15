package a_分布式并行计算.l_实时流计算demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

/**
 * Created by linyp on 2017/2/25.
 */
public class StreamCtorB extends Contractor {
    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = super.getWaitingWorkers("StreamWorkerB");
        System.out.println("wks.length: " + wks.length);

        WareHouse[] hmarr = super.doTaskBatch(wks, inhouse);

        WareHouse result = new WareHouse();
        result.put("B1", hmarr[0]);
        result.put("B2", hmarr[1]);
        return result;
    }

}
