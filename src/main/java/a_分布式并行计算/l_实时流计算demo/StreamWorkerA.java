package a_分布式并行计算.l_实时流计算demo;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

/**
 * Created by linyp on 2017/2/25.
 */
public class StreamWorkerA extends MigrantWorker {
    @Override
    protected WareHouse doTask(WareHouse inhouse) {
        System.out.println(inhouse);
        // do something
        StreamCtorB sc = new StreamCtorB();
        WareHouse msg = new WareHouse();
        msg.put("msg", inhouse.getString("msg") + "，from StreamWorkerA");
        WareHouse wh = sc.giveTask(msg);
        sc.exit();
        return wh;
    }

    public static void main(String[] args) {
        StreamWorkerA wd = new StreamWorkerA();
        wd.waitWorking(args[0], Integer.parseInt(args[1]), "StreamWorkerA");
    }
}
