package b_分布式协调.d_多节点权限操作demo;

import com.fourinone.AuthPolicy;
import com.fourinone.BeanContext;
import com.fourinone.ObjectBean;
import com.fourinone.ParkLocal;

/**
 * Created by linyp on 2017/2/25.
 */
public class ParkSet {
    public static void main(String[] args) {
        // 获取ParkServer用户接口
        ParkLocal pl = BeanContext.getPark();
        // 在domain dl下创建节点node n1，指定权限为只读
        ObjectBean d1n1 = pl.create("d1", "n1", "v1", AuthPolicy.OP_READ);
        if (d1n1 != null)
            System.out.println("d1n1 with AuthPolicy.OP_READ create success！");

        // 在domain d2下创建节点node n2，指定权限为读写
        ObjectBean d2n2 = pl.create("d2", "n2", "v2", AuthPolicy.OP_READ_WRITE);
        if (d2n2 != null)
            System.out.println("d2n2 with AuthPolicy.OP_READ_WRITE create success！");

        // 在domain d3下创建节点node n3，指定权限为所有
        ObjectBean d3n3 = pl.create("d3", "n3", "v3", AuthPolicy.OP_ALL);
        if (d3n3 != null)
            System.out.println("d3n3 with AuthPolicy.OP_ALL create success！");

        // 在domain d4下创建节点node n4，指定权限为所有，并且创建完成强行设置为其他进程可删除
        ObjectBean d4n4 = pl.create("d4", "n4", "v4", AuthPolicy.OP_READ_WRITE);
        if (d4n4 != null)
            System.out.println("d4n4 with AuthPolicy.OP_ALL create success！");
        boolean r = pl.setDeletable("d4");
        if (r)
            System.out.println("set d4 deletable！");
    }
}
