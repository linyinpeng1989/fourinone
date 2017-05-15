package b_分布式协调.c_集群管理demo;

import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.ObjectBean;
import com.fourinone.AuthPolicy;

import java.util.List;

public class GroupServer {
    public static void main(String[] args) {
        ParkLocal pl = BeanContext.getPark();
        pl.create("group", args[0], args[0], AuthPolicy.OP_ALL, true);

        List<ObjectBean> oldls = null;
        // 轮询监控资源状态是否发生变化（GroupManager宕机、某些GroupServer宕机或掉线等）
        while (true) {
            List<ObjectBean> newls = pl.getLastest("group", oldls);
            if (newls != null) {
                System.out.println("Group:" + newls);
                // 将最新的变化赋给oldls
                oldls = newls;
            }
        }
    }
}