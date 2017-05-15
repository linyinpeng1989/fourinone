package d_消息队列.a_点对点;

import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;

import java.io.Serializable;

/**
 * Created by linyp on 2017/3/5.
 */
public class Sender {
    private static ParkLocal pl = BeanContext.getPark();

    public static void send(String queue, Object obj) {
        // 在queue上创建一个匿名节点存放消息
        pl.create(queue, (Serializable) obj);
    }

    public static void main(String[] args) {
        send("queue1", "hello");
        send("queue1", "world");
        send("queue1", "mq");
    }
}
