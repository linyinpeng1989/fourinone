package e_分布式文件系统.b_访问操作远程文件;

import com.fourinone.FttpAdapter;
import com.fourinone.FttpAdapter.*;
import com.fourinone.FttpException;

/**
 * Created by linyp on 2017/3/5.
 */
public class FttpOperateDemo {
    public static void printPorp(FileProperty property) {
        System.out.println("exists: " + property.exists());
        System.out.println("isFile: " + property.isFile());
        System.out.println("isDirectory: " + property.isDirectory());
        System.out.println("isHidden: " + property.isHidden());
        System.out.println("canRead: " + property.canRead());
        System.out.println("canWrite: " + property.canWrite());
        System.out.println("lastModifiedDate: " + property.lastModifiedDate());
        System.out.println("length: " + property.length());
        System.out.println("getParent: " + property.getParent());
        System.out.println("getName: " + property.getName());
        System.out.println("getPath: " + property.getPath());
        if (property.isDirectory())
            System.out.println("fp.list(): " + property.list().length);
        System.out.println("");
    }

    public static void main(String[] args) {
        try {
            FttpAdapter dir = new FttpAdapter("fttp://localhost/d:/home/fttp/tmp");
            dir.createDirectory();
            FileProperty dirProp = dir.getProperty();
            printPorp(dirProp);

            FttpAdapter f1 = new FttpAdapter(dirProp.getPath(), "1.log");
            FttpAdapter f2 = null;
            FttpAdapter f3 = null;

            if (dirProp.exists()) {
                f1.createFile();
                f2 = f1.rename("2.log");
                f3 = f2.copyTo("fttp://localhost/d:/home/fttp/tmp/3.log");
            }

            FileProperty[] childProps = dir.getChildProperty();
            for (int i = 0; i < childProps.length; i++) {
                printPorp(childProps[i]);
            }

            System.out.println(f1.delete());
            System.out.println(f2.delete());
            System.out.println(f3.delete());
            System.out.println(dir.delete());

            dir.close();
            f1.close();
            f2.close();
            f3.close();
        } catch (FttpException e) {
            e.printStackTrace();
        }
    }

}
