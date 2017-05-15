package e_分布式文件系统.c_集群内文件复制;

import com.fourinone.FttpAdapter;
import com.fourinone.FttpException;

import java.util.Date;

/**
 * Created by linyp on 2017/3/5.
 */
public class FttpCopyDemo {
    public static void main(String[] args) {
        try {
            long begin = (new Date()).getTime();
            FttpAdapter fromFile = new FttpAdapter("fttp://192.168.0.1/home/someone/fttp/tmp/a.log");
            // 如果需要并行复制，使用tryCopyTo，立即返回Result<FttpAdapter>对象，
            // 需要检查Result的getStatus校验是否复制完成，状态显示就绪代表复制已完成，此时可以获取到复制后的文件对象
            // 详见FttpMulCopyDemo
            FttpAdapter toFile = fromFile.copyTo("fttp://192.168.0.2/home/someone/fttp/tmp/a.log");
            if (toFile != null)
                System.out.println("copy ok.");
            long end = (new Date()).getTime();
            System.out.println("time: " + (end - begin) / 1000 + "s");
        } catch (FttpException e) {
            e.printStackTrace();
        }
    }
}
