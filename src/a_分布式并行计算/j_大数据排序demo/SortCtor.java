package a_分布式并行计算.j_大数据排序demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

import java.util.Calendar;

/**
 * Created by linyp on 2017/2/24.
 */
public class SortCtor extends Contractor {
    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = getWaitingWorkers("SortWorker");
        System.out.println("wks.length: " + wks.length + "; " + inhouse);

        // 第一步:将原始数据分组分类
        inhouse.setObj("step", 1);
        doTaskBatch(wks, inhouse);

        // 第二步:分组分类数据的合并处理
        inhouse.setObj("step", 2);
        doTaskBatch(wks, inhouse);

        // 第三步:分组分类数据的排序处理
        inhouse.setObj("step", 3);
        WareHouse[] hmarr = doTaskBatch(wks, inhouse);
        int total = 0;
        for (int i = 0; i < hmarr.length; i++) {
            Object num = hmarr[i].getObj("total");
            if (num != null)
                total += (int) num;
        }
        inhouse.setObj("total", total);
        return inhouse;
    }

    public static void main(String[] args) {
        Contractor a = new SortCtor();
        WareHouse wh = new WareHouse();
        Calendar calendar = Calendar.getInstance();
        long begin = calendar.getTimeInMillis();
        a.doProject(wh);
        calendar = Calendar.getInstance();
        long end = calendar.getTimeInMillis();
        System.out.println("total: " + wh.getObj("total") + ", time: " + (end - begin) / 1000 + "s");
    }
}
