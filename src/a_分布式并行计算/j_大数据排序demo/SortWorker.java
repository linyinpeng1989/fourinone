package a_分布式并行计算.j_大数据排序demo;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;
import com.fourinone.Workman;

import java.util.*;

/**
 * Created by linyp on 2017/2/24.
 */
public class SortWorker extends MigrantWorker {
    // 排序数据的取值范围最大值
    private final int rammax;
    // 排序数据的总量（每个工人）
    private int totalMax;
    // 记录当前生成随机数据的数量
    private int total = 0;
    // 用于存放分类数据
    private Map<Integer, List<Integer>> wharr = new HashMap<>();

    private Random random = new Random(47);
    private int wknum = -1;
    private int index = -1;
    private Workman[] wms = null;

    public SortWorker(int rammax, int totalMax) {
        this.rammax = rammax;
        this.totalMax = totalMax;
    }

    public Integer[] getNumber() {
        if (total++ < totalMax) {
            int thenum = random.nextInt(rammax);
            int numi = (thenum * wknum) / rammax;
            return new Integer[]{numi, thenum};
        } else
            return new Integer[]{-1, -1};
    }

    @Override
    protected WareHouse doTask(WareHouse inhouse) {
        // 获取步骤
        int step = (int) inhouse.getObj("step");
        if (wms == null) {
            wms = getWorkerAll();
            wknum = wms.length;
        }
        // 获取自身位置
        index = getSelfIndex();
        System.out.println("wknum: " + wknum + "; step: " + step);
        WareHouse resultWh = new WareHouse("ok", 1);

        if (step == 1) {
            Integer[] num = null;
            while (true) {
                num = getNumber();
                if (num[0] != -1) {
                    // 取出该分类的list
                    List<Integer> arr = wharr.get(num[0]);
                    if (arr == null)
                        arr = new ArrayList<>();
                    arr.add(num[1]);
                    wharr.put(num[0], arr);
                } else break;
            }
        } else if (step == 2) {
            for (int i = 0; i < wms.length; i++) {
                if (i != index && wharr.containsKey(i)) {
                    List<Integer> othernum = wharr.remove(i);
                    Workman wm = wms[i];
                    // 将不属于自己的分类发送给其他工人
                    System.out.println(i + "-receive:" + wm.receive(new WareHouse(i, othernum)));
                }
            }
        } else if (step == 3) {
            List<Integer> curlist = wharr.get(index);
            // 对于属于自己分类的数据进行内存内排序
            Collections.sort(curlist);
            System.out.println(curlist);
            System.out.println(curlist.size());
            resultWh.setObj("total", curlist.size());
        }
        return resultWh;
    }

    @Override
    protected boolean receive(WareHouse inhouse) {
        List<Integer> thisnum = wharr.get(index);
        thisnum.addAll((List<Integer>) inhouse.get(index));
        return true;
    }

    public static void main(String[] args) {
        SortWorker mw = new SortWorker(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        mw.waitWorking(args[0], Integer.parseInt(args[1]), "SortWorker");
    }
}
