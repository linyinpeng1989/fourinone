package c_分布式缓存.b_缓存过期及清空;

import com.fourinone.BeanContext;
import com.fourinone.ObjectBean;
import com.fourinone.ParkLocal;

import java.util.Date;
import java.util.List;

/**
 * Created by linyp on 2017/3/2.
 */
public class ClearTest {
    public static void main(String[] args) {
        try {
            ParkLocal pl = BeanContext.getPark();

            ObjectBean ob1 = pl.create("china", "city1", "beijing");
            System.out.println(new Date() + ":");
            System.out.println(ob1);

            Thread.sleep(1000 * 30);

            ObjectBean ob2 = pl.create("china", "city2", "shanghai");
            System.out.println(new Date() + ":");
            System.out.println(ob2);

            ObjectBean ob3 = pl.create("china", "city3", "shenzhen", true);
            System.out.println(new Date() + ":");
            System.out.println(ob3);

            while (true) {
                List<ObjectBean> oblist = pl.get("china");
                System.out.println(new Date() + ":");
                System.out.println(oblist);
                Thread.sleep(1000 * 10);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
