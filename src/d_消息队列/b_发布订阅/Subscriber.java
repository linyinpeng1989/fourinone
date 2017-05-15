package d_消息队列.b_发布订阅;

import com.fourinone.*;

import java.util.ArrayList;

/**
 * Created by linyp on 2017/3/5.
 */
public class Subscriber implements LastestListener {
    private static ParkLocal pl = BeanContext.getPark();

    @Override
    public boolean happenLastest(LastestEvent le) {
        ObjectBean ob = (ObjectBean) le.getSource();
        // 若有多个消息，用ArrayList进行分装并传递
        ArrayList arr = (ArrayList) ob.toObject();
        // do something ：这里仅作输出处理
        System.out.println("published message: " + arr);

        // 清空内容，等待下一个消息
        ObjectBean newob = pl.update(ob.getDomain(), ob.getNode(), new ArrayList<>());
        le.setSource(newob);
        // 如果返回false则会一直监控变化，继续有新的变化时还会进行事件调用；如果返回true则完成本次事件调用后就终止
        return false;
    }

    public static void subscrib(String topic, String subscribeName, LastestListener listener) {
        ArrayList arr = new ArrayList();
        // 创建初始化节点，domain为主题，node为订阅名，值为一个空的数组
        ObjectBean ob = pl.create(topic, subscribeName, arr);
        // 为节点添加监听
        pl.addLastestListener(topic, subscribeName, ob, listener);
    }

    public static void main(String[] args) {
        subscrib("topic1", args[0], new Subscriber());
    }

}
