package b_分布式协调.d_多节点权限操作demo;

import com.fourinone.BeanContext;
import com.fourinone.ObjectBean;
import com.fourinone.ParkLocal;

import java.util.List;

/**
 * Created by linyp on 2017/2/25.
 */
public class ParkGet {
    public static void main(String[] args) {
        // 获取ParkServer用户接口
        ParkLocal pl = BeanContext.getPark();

        // 获取节点d1n1，节点权限为AuthPolicy.OP_READ
        ObjectBean d1n1 = pl.get("d1", "n1");// 获取节点
        System.out.println("get d1n1: " + d1n1.toObject());

        d1n1 = pl.update("d1", "n1", "v1-update");//更新节点
        if (d1n1 != null)
            System.out.println("update node d1n1 success!");
        else
            System.out.println("update node d1n1 failure!");

        List<ObjectBean> d1 = pl.delete("d1");// 删除domain
        if (d1 != null)
            System.out.println("delete domain d1 success!");
        else
            System.out.println("delete domain d1 failure!");

        d1n1 = pl.delete("d1", "n1");// 删除节点
        if (d1n1 != null)
            System.out.println("delete node d1n1 success!");
        else
            System.out.println("delete node d1n1 failure!");


        // 获取节点d2n2，节点权限为AuthPolicy.OP_READ_WRITE
        ObjectBean d2n2 = pl.get("d2", "n2");// 获取节点
        System.out.println("get d2n2: " + d2n2.toObject());

        d2n2 = pl.update("d2", "n2", "v2-update");//更新节点
        if (d2n2 != null)
            System.out.println("update node d2n2 success!");
        else
            System.out.println("update node d2n2 failure!");

        List<ObjectBean> d2 = pl.delete("d2");// 删除domain
        if (d2 != null)
            System.out.println("delete domain d2 success!");
        else
            System.out.println("delete domain d2 failure!");

        d2n2 = pl.delete("d2", "n2");// 删除节点
        if (d2n2 != null)
            System.out.println("delete node d2n2 success!");
        else
            System.out.println("delete node d2n2 failure!");


        // 获取节点d3n3，节点权限为AuthPolicy.OP_ALL
        ObjectBean d3n3 = pl.get("d3", "n3");// 获取节点
        System.out.println("get d3n3: " + d3n3.toObject());

        d3n3 = pl.update("d3", "n3", "v3-update");//更新节点
        if (d3n3 != null)
            System.out.println("update node d3n3 success!");
        else
            System.out.println("update node d3n3 failure!");

        List<ObjectBean> d3 = pl.delete("d3");// 删除domain
        if (d3 != null)
            System.out.println("delete domain d3 success!");
        else
            System.out.println("delete domain d3 failure!");

        d3n3 = pl.delete("d3", "n3");// 删除节点
        if (d3n3 != null)
            System.out.println("delete node d3n3 success!");
        else
            System.out.println("delete node d3n3 failure!");


        // 获取节点d4n4，节点权限为AuthPolicy.OP_ALL
        ObjectBean d4n4 = pl.get("d4", "n4");// 获取节点
        System.out.println("get d4n4: " + d4n4.toObject());

        d4n4 = pl.update("d4", "n4", "v4-update");//更新节点
        if (d4n4 != null)
            System.out.println("update node d4n4 success!");
        else
            System.out.println("update node d4n4 failure!");

        // 由于创建进程已经强行指定该domain可删除setDeletable(d4)，因此这里可以删除掉
        List<ObjectBean> d4 = pl.delete("d4");// 删除domain
        if (d4 != null)
            System.out.println("delete domain d4 success!");
        else
            System.out.println("delete domain d4 failure!");

        // 这里删除节点会失败，因为上面已经删除了该domain下所有节点
        d4n4 = pl.delete("d4", "n4");// 删除节点
        if (d4n4 != null)
            System.out.println("delete node d4n4 success!");
        else
            System.out.println("delete node d4n4 failure!");
    }
}
