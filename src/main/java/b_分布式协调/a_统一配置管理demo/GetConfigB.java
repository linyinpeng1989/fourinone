package b_分布式协调.a_统一配置管理demo;

import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.LastestListener;
import com.fourinone.LastestEvent;
import com.fourinone.ObjectBean;

public class GetConfigB implements LastestListener {
    public boolean happenLastest(LastestEvent le) {
        ObjectBean ob = (ObjectBean) le.getSource();
        System.out.println(ob);
        return false;
    }

    public static void main(String[] args) {
        ParkLocal pl = BeanContext.getPark();
        // 注册监听
        pl.addLastestListener("zhejiang", "hangzhou", null, new GetConfigB());
    }
}