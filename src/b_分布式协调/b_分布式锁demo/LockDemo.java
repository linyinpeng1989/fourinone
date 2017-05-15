package b_分布式协调.b_分布式锁demo;

import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.ObjectBean;

import java.util.List;

public class LockDemo {
    public void lockutil(String node) {
        ParkLocal pl = BeanContext.getPark();
        // 放入队列
        ObjectBean ob = pl.create("lock", node, node);

        System.out.print("try get lock.");

        // 轮询判断domain lock的第一个元素是否是自己的node，
        // 如果是则执行，执行完将自己的node删除，代表释放锁
        // 如果不是则继续等待
        while (true) {
            List<ObjectBean> oblist = pl.get("lock");
            String curnode = (String) oblist.get(0).toObject();
            //System.out.println(curnode);
            if (curnode.equals(node)) {
                System.out.println("ok, get lock and doing...");
                try {
                    Thread.sleep(8000);
                } catch (Exception e) {
                    // do something
                }
                pl.delete("lock", node);
                System.out.println("done.");
                break;
            } else
                System.out.print(".");
        }
    }

    public static void main(String[] args) {
        LockDemo ld = new LockDemo();
        ld.lockutil(args[0]);
    }
}