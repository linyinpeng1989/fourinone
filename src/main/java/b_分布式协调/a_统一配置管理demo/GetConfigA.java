package b_分布式协调.a_统一配置管理demo;

import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.ObjectBean;

public class GetConfigA {
    public static void main(String[] args) {
        ParkLocal pl = BeanContext.getPark();
        ObjectBean oldob = null;
        // 轮询监控资源状态是否发生变化
        while (true) {
            ObjectBean newob = pl.getLastest("zhejiang", "hangzhou", oldob);
            if (newob != null) {
                System.out.println(newob);
                oldob = newob;
                System.out.println(oldob.getName());
                System.out.println(oldob.getDomain());
                System.out.println(oldob.getNode());
            }
        }
    }
}