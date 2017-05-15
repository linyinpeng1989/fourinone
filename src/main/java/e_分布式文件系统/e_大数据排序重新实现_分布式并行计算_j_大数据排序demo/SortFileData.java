package e_分布式文件系统.e_大数据排序重新实现_分布式并行计算_j_大数据排序demo;

import com.fourinone.FileAdapter;
import com.fourinone.FileAdapter.IntWriteAdapter;

import java.util.List;
import java.util.Random;

/**
 * Created by linyp on 2017/3/6.
 * 初始化四份数据，用作排序的源数据
 */
public class SortFileData {
    public static void createData(int total, int max, String path) {
        System.out.println("create " + total + " number(max:" + max + ") to " + path + "...");
        int every = 500000;
        FileAdapter fa = new FileAdapter(path);
        fa.delete();
        Random rad = new Random();
        while (total > 0) {
            IntWriteAdapter wa = fa.getIntWriter();
            int[] nums = new int[total < every ? total : every];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = rad.nextInt(max);
            }
            wa.writeInt(nums);
            total -= nums.length;
        }
        System.out.println("create done.");
        fa.close();
    }

    public static void checkData(String path) {
        FileAdapter fa = new FileAdapter(path);
        List<Integer> rls = fa.getIntReader(0, 100).readListIntAll();
        System.out.println(rls);
    }

    public static void main(String[] args) {
        createData(25000000, 10000000, "data/2008/data");
        checkData("data/2008/data");
        createData(25000000, 10000000, "data/2009/data");
        checkData("data/2009/data");
        createData(25000000, 10000000, "data/2010/data");
        checkData("data/2010/data");
        createData(25000000, 10000000, "data/2011/data");
        checkData("data/2011/data");
    }
}
