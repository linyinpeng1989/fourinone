package a_分布式并行计算.b_分布式计算完整demo;

import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

public class CtorDemo extends Contractor {
    private String ctorname;

    CtorDemo(String ctorname) {
        this.ctorname = ctorname;
    }

    public WareHouse giveTask(WareHouse inhouse) {
        WorkerLocal[] wks = getWaitingWorkers("workdemo");
        System.out.println("wks.length:" + wks.length);

        String outStr = inhouse.getString("id");
        WareHouse[] hmarr = new WareHouse[wks.length];

        int data = 0;
        // 总共有20个任务，使用data表示，用j来进行数量限制
        for (int j = 0; j < 20; ) {
            // 循环遍历每个工人的状态
            for (int i = 0; i < wks.length; i++) {
                // 如果工人处于空闲状态，则分配任务
                if (hmarr[i] == null) {
                    WareHouse wh = new WareHouse();
                    wh.put("id", ctorname + (data++));
                    hmarr[i] = wks[i].doTask(wh);
                }
                // 检测工人是否完成任务，若完成则设置为空闲状态
                else if (hmarr[i].getStatus() != WareHouse.NOTREADY) {
                    System.out.println(hmarr[i]);
                    outStr += hmarr[i];
                    hmarr[i] = null;
                    j++;
                }
            }
        }

        inhouse.setString("id", outStr);
        return inhouse;
    }

    public static void main(String[] args) {
        Contractor a = new CtorDemo("OneCtor");
        // 通过toNext设置下一个环节对应的工头
        a.toNext(new CtorDemo("TwoCtor")).toNext(new CtorDemo("ThreeCtor"));
        WareHouse house = new WareHouse("id", "begin ");
        // 调用Contractor的giveTask(WareHouse inhouse, boolean chainProcess)方法，进而回调到CtorDemo的giveTask(WareHouse inhouse)方法
        // chainProcess为true表示链式处理
        System.out.println(a.giveTask(house, true));
    }
}