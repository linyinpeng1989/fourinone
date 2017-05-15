package e_分布式文件系统.a_获取集群文件方式;

import com.fourinone.FttpAdapter;
import com.fourinone.FttpException;

/**
 * Created by linyp on 2017/3/5.
 */
public class FttpRootDemo {
    public static void main(String[] args) {
        try {
            String[] fttproots = FttpAdapter.fttpRoots();
            for (int i = 0; i < fttproots.length; i++) {
                System.out.println(fttproots[i]);

                FttpAdapter fa = new FttpAdapter("fttp://" + fttproots[i]);
                String[] roots = fa.listRoots();
                for (int j = 0; j < roots.length; j++) {
                    System.out.println(roots[j]);
                }
                System.out.println("");
            }
        } catch (FttpException e) {
            e.printStackTrace();
        }
    }
}
