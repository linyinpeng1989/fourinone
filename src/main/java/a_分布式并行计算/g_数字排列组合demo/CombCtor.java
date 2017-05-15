package a_分布式并行计算.g_数字排列组合demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

import java.util.Calendar;

/**
 * Created by linyp on 2017/2/21.
 */
public class CombCtor extends Contractor {
    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = getWaitingWorkers("CombWorker");
        System.out.println("wks.length: " + wks.length + ";" + inhouse);
        inhouse.setObj("wknum", wks.length);
        // 批量执行任务，所有工人完成后返回
        WareHouse[] hmarr = doTaskBatch(wks, inhouse);
        int total = 0;
        for (WareHouse hm : hmarr) {
            total += (int) hm.getObj("total");
        }
        System.out.println("total: " + total);
        return inhouse;
    }

    public static void main(String[] args) {
        CombCtor a = new CombCtor();
        WareHouse wh = new WareHouse("comb", Integer.valueOf(args[0]));
        Calendar calendar = Calendar.getInstance();
        long begin = calendar.getTimeInMillis();
        // 链式处理
        a.doProject(wh);
        calendar = Calendar.getInstance();
        long end = calendar.getTimeInMillis();
        System.out.println("time: " + (end - begin) / 1000 + "s");
        a.exit();
    }
}
