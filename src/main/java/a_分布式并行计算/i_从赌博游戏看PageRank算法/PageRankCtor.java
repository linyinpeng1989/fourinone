package a_分布式并行计算.i_从赌博游戏看PageRank算法;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

import java.util.Iterator;

/**
 * Created by linyp on 2017/2/23.
 */
public class PageRankCtor extends Contractor {
    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = getWaitingWorkers("pagerankworker");
        System.out.println("wks.length: " + wks.length);

        for (int i = 0; i < 500; i++) {
            WareHouse[] hmarr = doTaskBatch(wks, inhouse);
            WareHouse prwh = new WareHouse();
            for (WareHouse result : hmarr) {
                for (Iterator iter = result.keySet().iterator(); iter.hasNext(); ) {
                    String page = (String) iter.next();
                    Double pagepr = (Double) result.getObj(page);
                    if (prwh.containsKey(page))
                        pagepr += (Double) prwh.getObj(page);
                    prwh.setObj(page, pagepr);
                }
            }
            inhouse = prwh; // 迭代
            System.out.println("NO." + i + ":" + inhouse);
        }
        return inhouse;
    }

    public static void main(String[] args) {
        PageRankCtor a = new PageRankCtor();
        WareHouse inhouse = new WareHouse();
        inhouse.setObj("A", 1.00d); //A的pr初始值
        inhouse.setObj("B", 1.00d); //B的pr初始值
        inhouse.setObj("C", 1.00d); //C的pr初始值
        a.giveTask(inhouse);
        a.exit();
    }
}
