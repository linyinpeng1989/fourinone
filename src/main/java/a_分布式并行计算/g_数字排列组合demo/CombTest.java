package a_分布式并行计算.g_数字排列组合demo;

import java.util.Calendar;

/**
 * Created by linyp on 2017/2/21.
 * 数字排列组合 -- 单机递归
 */
public class CombTest {
    int m = 0, n = 0, total = 0;

    public CombTest(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public void comb(String str) {
        for (int i = 1; i < n + 1; i++) {
            if (str.length() == m - 1) {
                //System.out.println(str + i);
                total++;
            }
            // 递归
            else {
                comb(str + i);
            }
        }
    }

    public static void main(String[] args) {
        CombTest ct = new CombTest(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        Calendar calendar = Calendar.getInstance();
        long begin = calendar.getTimeInMillis();
        ct.comb("");
        System.out.println("total: " + ct.total);
        calendar = Calendar.getInstance();
        long end = calendar.getTimeInMillis();
        System.out.println("time: " + (end - begin) / 1000 + "s");
    }
}
