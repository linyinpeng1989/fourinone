package a_分布式并行计算.h_计算圆周率demo;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

/**
 * Created by linyp on 2017/2/21.
 */
public class PiWorker extends MigrantWorker {
    public double m = 0.0, n = 0.0;

    public PiWorker(double m, double n) {
        this.m = m;
        this.n = n;
    }

    public WareHouse doTask(WareHouse inhouse) {
        double pi = 0.0;
        for (double i = m; i < n; i++) {
            pi += Math.pow(-1, i + 1) / (2 * i - 1);
        }
        System.out.println(4 * pi);
        inhouse.setObj("pi", 4 * pi);
        return inhouse;
    }

    public static void main(String[] args) {
        PiWorker mw = new PiWorker(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        mw.waitWorking(args[0], Integer.parseInt(args[1]), "PiWorker");
    }
}
