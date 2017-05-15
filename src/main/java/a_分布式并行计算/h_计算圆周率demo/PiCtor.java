package a_分布式并行计算.h_计算圆周率demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

import java.util.Calendar;

/**
 * Created by linyp on 2017/2/21.
 */
public class PiCtor extends Contractor {
    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = getWaitingWorkers("PiWorker");
        System.out.println("wks.length: " + wks.length);
        WareHouse[] hmarr = doTaskBatch(wks, inhouse);

        double pi = 0.0;
        for (WareHouse hm : hmarr) {
            pi += (Double) hm.getObj("pi");
        }
        System.out.println("pi: " + pi);
        return inhouse;
    }

    public static void main(String[] args) {
        PiCtor a = new PiCtor();
        Calendar calendar = Calendar.getInstance();
        long begin = calendar.getTimeInMillis();
        a.giveTask(new WareHouse());
        calendar = Calendar.getInstance();
        long end = calendar.getTimeInMillis();
        System.out.println("time: " + (end - begin) / 1000 + "s");
        a.exit();
    }
}
