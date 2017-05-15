package d_消息队列.b_发布订阅;

import com.fourinone.BeanContext;
import com.fourinone.ObjectBean;
import com.fourinone.ParkLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linyp on 2017/3/5.
 */
public class Publisher {
    private static ParkLocal pl = BeanContext.getPark();

    public static Object publish(String topic, Object obj) {
        // 获取domain下面的所有节点，即所有的订阅者
        List<ObjectBean> oblist = pl.get(topic);
        if (oblist != null) {
            for (ObjectBean ob : oblist) {
                ArrayList arr = (ArrayList) ob.toObject();
                arr.add(obj);
                pl.update(ob.getDomain(), ob.getNode(), arr);
            }
            return obj;
        } else
            return null;
    }

    public static void main(String[] args) {
        publish("topic1", "hello world!");
    }
}
