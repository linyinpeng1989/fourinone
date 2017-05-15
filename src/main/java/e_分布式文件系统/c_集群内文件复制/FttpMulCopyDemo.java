package e_分布式文件系统.c_集群内文件复制;

import com.fourinone.FileAdapter;
import com.fourinone.FttpAdapter;
import com.fourinone.FttpException;
import com.fourinone.Result;

import java.util.Date;

/**
 * Created by linyp on 2017/3/5.
 */
public class FttpMulCopyDemo {
    public static void main(String[] args) {
        long begin = (new Date()).getTime();
        try {
            Result<FttpAdapter>[] rs = new Result[4];
            String fromFile = "fttp://192.168.0.1/home/someone/fttp/tmp/a.log";
            FttpAdapter fa1 = new FttpAdapter(fromFile);
            FttpAdapter fa2 = new FttpAdapter(fromFile);
            FttpAdapter fa3 = new FttpAdapter(fromFile);
            FttpAdapter fa4 = new FttpAdapter(fromFile);
            rs[0] = fa1.tryCopyTo("fttp://192.168.0.2/home/someone/fttp/tmp/a.log", FileAdapter.m(1));
            rs[1] = fa2.tryCopyTo("fttp://192.168.0.3/home/someone/fttp/tmp/a.log", FileAdapter.m(1));
            rs[2] = fa3.tryCopyTo("fttp://192.168.0.4/home/someone/fttp/tmp/a.log", FileAdapter.m(1));
            rs[3] = fa4.tryCopyTo("fttp://192.168.0.5/home/someone/fttp/tmp/a.log", FileAdapter.m(1));

            int n = 0;
            while (n < 4) {
                for (int i = 0; i < rs.length; i++) {
                    if (rs[i] != null && rs[i].getStatus() != Result.NOTREADY) {
                        System.out.println(i + ", getStatus: " + rs[i].getStatus() + ", getResult: " + rs[i].getResult());
                        rs[i] = null;
                        n++;
                    }
                }
            }
            fa1.close();
            fa2.close();
            fa3.close();
            fa4.close();
        } catch (FttpException e) {
            e.printStackTrace();
        }
        long end = (new Date()).getTime();
        System.out.println("time: " + (end - begin) / 1000 + "s");
    }
}
