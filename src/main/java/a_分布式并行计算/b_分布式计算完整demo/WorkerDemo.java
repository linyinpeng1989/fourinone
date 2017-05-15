package a_分布式并行计算.b_分布式计算完整demo;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

public class WorkerDemo extends MigrantWorker {
    private String workname;

    public WorkerDemo(String workname) {
        this.workname = workname;
    }

    public WareHouse doTask(WareHouse inhouse) {
        String v = inhouse.getString("id");
        System.out.println(workname + " inhouse: " + v);
        return new WareHouse("id", v + "-" + workname + "-");
    }

    public static void main(String[] args) {
        WorkerDemo wd = new WorkerDemo(args[0]);
        wd.waitWorking("localhost", Integer.parseInt(args[1]), "workdemo");
    }
}