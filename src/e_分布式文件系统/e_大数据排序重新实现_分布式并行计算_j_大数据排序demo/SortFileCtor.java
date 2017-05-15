package e_分布式文件系统.e_大数据排序重新实现_分布式并行计算_j_大数据排序demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

import java.util.Date;

/**
 * Created by linyp on 2017/3/7.
 */
public class SortFileCtor extends Contractor {
    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = super.getWaitingWorkers("SortWorker");
        System.out.println("wks.length: " + wks.length + "; " + inhouse);
        int total = 0;

        inhouse.setObj("step", 1);
        super.doTaskBatch(wks, inhouse);

        inhouse.setObj("step", 2);
        super.doTaskBatch(wks, inhouse);

        inhouse.setObj("step", 3);
        WareHouse[] wareHouses = super.doTaskBatch(wks, inhouse);
        for (int i = 0; i < wareHouses.length; i++) {
            Object num = wareHouses[i].get("total");
            if (num != null) {
                total += (int) num;
            }
        }
        inhouse.setObj("total", total);
        return inhouse;
    }

    public static void main(String[] args) {
        Contractor a = new SortFileCtor();
        WareHouse wh = new WareHouse();
        long begin = (new Date()).getTime();
        a.doProject(wh);
        long end = (new Date()).getTime();
        System.out.println("total: " + wh.get("total") + ", time: " + (end - begin) / 1000 + "s");
    }
}
