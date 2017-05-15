package e_分布式文件系统.d_ListInt相关;

import com.fourinone.ArrayAdapter;
import com.fourinone.ArrayAdapter.*;
import com.fourinone.FileAdapter;
import com.fourinone.FileAdapter.*;

import java.util.*;

/**
 * Created by linyp on 2017/3/6.
 */
public class ListIntTest {
    public void intWrite(String path, int num) {
        System.out.println("write " + num + " number to " + path + "...");
        FileAdapter fa = new FileAdapter(path);
        fa.delete();
        IntWriteAdapter wa = fa.getIntWriter();
        Random rad = new Random();
        int[] nums = new int[num];
        for (int i = 0; i < num; i++) {
            int thenum = rad.nextInt(10000000);
            nums[i] = thenum;
        }
        wa.writeInt(nums);
        System.out.println("write done.");
        fa.close();
    }

    public void intRead(String path) {
        System.out.println("read number from " + path + "...");
        FileAdapter fa = new FileAdapter(path);
        IntReadAdapter ra = null;
        int total = 0;
        for (int n = 0; n < 10; n++) {
            ra = fa.getIntReader(n * 10, 10);
            int[] its = ra.readIntAll();
            for (int i : its) {
                System.out.println(i);
                total++;
            }
        }
        System.out.println("total: " + total);
        fa.close();
    }

    public void listintCapacity() {
        System.out.println("insert int into ListInt ...");
        ListInt ai = ArrayAdapter.getListInt();
        Random rad = new Random();
        for (int i = 0; i < 20000000; i++) {
            ai.add(rad.nextInt(10000000));
        }
        System.out.println(ai.size() + " number be inserted.");
    }

    public void arraylistCapacity() {
        System.out.println("insert int into ArrayList ...");
        List<Integer> al = new ArrayList<>();
        Random rad = new Random();
        int i = 0;
        while (i < 20000001) {
            al.add(rad.nextInt(10000000));
            if (i % 10000000 == 0)
                System.out.println(i + " number be inserted.");
            i++;
        }
    }

    public void listintSort() {
        FileAdapter fa = new FileAdapter("data.txt");
        int[] rls = fa.getIntReader().readIntAll();
        ListInt is = ArrayAdapter.getListInt();
        System.out.println("ListInt sort begin ...");
        long begin = (new Date()).getTime();
        is.sort(rls);
        long end = (new Date()).getTime();
        System.out.println("sort done time: " + (end - begin) / 1000 + "s");

        System.out.println("check top 100 data:");
        for (int i = 0; i < 10; i++) {
            System.out.println(rls[i]);
        }
    }

    public void arraylistSort() {
        FileAdapter fa = new FileAdapter("data.txt");
        List<Integer> rls = fa.getIntReader().readListIntAll();
        System.out.println("ArrayList sort begin ...");
        long begin = (new Date()).getTime();
        Collections.sort(rls);
        long end = (new Date()).getTime();
        System.out.println("sort done time: " + (end - begin) / 1000 + "s");

        System.out.println("check top 100 data:");
        for (int i = 0; i < 10; i++) {
            System.out.println(rls.get(i));
        }
    }

    public static void writeReadTest() {
        ListIntTest test = new ListIntTest();
        test.intWrite("data.txt", 100);
        test.intRead("data.txt");
    }

    public static void capacityTest() {
        ListIntTest test = new ListIntTest();
        test.listintCapacity();
        test.arraylistCapacity();
    }

    public static void sortTest(int num) {
        ListIntTest test = new ListIntTest();
        test.intWrite("data.txt", num);
        test.listintSort();
        test.arraylistSort();
    }

    public static void main(String[] args) {
        if (args[0].equals("0")) {
            writeReadTest();
        } else if (args[0].equals("1")) {
            capacityTest();
        } else if (args[0].equals("2")) {
            sortTest(5000000);
        }
    }
}
