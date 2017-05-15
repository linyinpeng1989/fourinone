package a_分布式并行计算.g_数字排列组合demo;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

/**
 * Created by linyp on 2017/2/21.
 */
public class CombWorker extends MigrantWorker {
    // m表示字符串长度，如m=8，则数字排列组合的某一行可能为 11111111
    // n表示数字范围（1<=??<=n），如n=4，则可供选择的数字为1,2,3,4
    private int m = 0, n = 0, total = 0, index = -1;

    public WareHouse doTask(WareHouse inhouse) {
        total = 0;
        n = (int) inhouse.getObj("wknum");
        m = (int) inhouse.getObj("comb");
        // 获取自身所在的index
        index = getSelfIndex();
        System.out.println("index: " + index);
        comb(index + 1 + "");
        System.out.println("total: " + total);
        return new WareHouse("total", total);
    }

    /**
     * 递归
     * @param str
     */
    public void comb(String str) {
        for (int i = 1; i < n + 1; i++) {
            if (str.length() == m - 1) {
                //System.out.println(str + i);
                total++;
            } else {
                comb(str + i);
            }
        }
    }

    public static void main(String[] args) {
        CombWorker mw = new CombWorker();
        mw.waitWorking(args[0], Integer.valueOf(args[1]), "CombWorker");
    }
}
