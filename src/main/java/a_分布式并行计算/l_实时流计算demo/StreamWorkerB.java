package a_分布式并行计算.l_实时流计算demo;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

/**
 * Created by linyp on 2017/2/25.
 */
public class StreamWorkerB extends MigrantWorker {
    @Override
    protected WareHouse doTask(WareHouse inhouse) {
        System.out.println(inhouse);
        // do something
        inhouse.put("msg", inhouse.getString("msg") + ", from StreamWorkerB");
        return inhouse;
    }

    public static void main(String[] args) {
        StreamWorkerB wd = new StreamWorkerB();
        wd.waitWorking(args[0], Integer.parseInt(args[1]), "StreamWorkerB");
    }
}
