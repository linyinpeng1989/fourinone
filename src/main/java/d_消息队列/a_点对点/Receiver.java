package d_消息队列.a_点对点;

import com.fourinone.BeanContext;
import com.fourinone.ObjectBean;
import com.fourinone.ParkLocal;

import java.util.List;

/**
 * Created by linyp on 2017/3/5.
 */
public class Receiver {
    private static ParkLocal pl = BeanContext.getPark();

    public static Object receive(String queue) {
        Object obj = null;
        List<ObjectBean> oblist = null;

        // 轮询queue是否有最新消息，若有新消息，则获取第一个消息并返回第一个节点的消息内容，并删除该节点并退出轮询
        while (true) {
            oblist = pl.get(queue);
            if (oblist != null) {
                ObjectBean ob = oblist.get(0);
                obj = ob.toObject();
                pl.delete(queue, ob.getNode());
                break;
            }
        }
        return obj;
    }

    public static void main(String[] args) {
        System.out.println(receive("queue1"));
        System.out.println(receive("queue1"));
        System.out.println(receive("queue1"));
    }
}
