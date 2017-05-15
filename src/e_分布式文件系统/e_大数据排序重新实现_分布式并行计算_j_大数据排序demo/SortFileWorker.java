package e_分布式文件系统.e_大数据排序重新实现_分布式并行计算_j_大数据排序demo;

import com.fourinone.*;
import com.fourinone.FileAdapter.IntReadAdapter;

import java.io.File;
import java.util.*;

/**
 * Created by linyp on 2017/3/6.
 */
public class SortFileWorker extends MigrantWorker {

    private int n = -1, max = -1, every = -1, m = -1, index = -1;
    private String path;
    private Map<Integer, List<Integer>> wharr = new HashMap<>();
    private Random rad = new Random(47);
    private Workman[] wms = null;
    private FileAdapter[] faws = null;

    public SortFileWorker(int n, int max, int every, String path) {
        this.n = n;
        this.max = max;
        this.every = every;
        this.path = path;
        faws = new FileAdapter[n];
    }

    @Override
    protected WareHouse doTask(WareHouse inhouse) {
        try {
            int step = (int) inhouse.getObj("step");
            if (wms == null) {
                wms = super.getWorkerAll();
                m = wms.length;
                // 初始化FileAdapter，m=4,n=8的情况，文件划分为00,01,10,11,20,21,30,31
                for (int i = 0; i < n; i++) {
                    faws[i] = new FileAdapter(new File(path).getParent() + "\\output\\" + i * m / n + i % (n / m));
                    faws[i].delete();
                }
            }
            // 获取工人在集群中的位置
            index = super.getSelfIndex();
            System.out.println("wknum: " + m + "; step: " + step);
            WareHouse resultWh = new WareHouse("ok", 1);

            // 第一步骤：分段读取服务器上源文件，并将数据分类输出到子文件中
            if (step == 1) {
                FileAdapter fa = new FileAdapter(path);
                IntReadAdapter ira = null;
                int begin = 0;
                int[] rls = null;
                while (true) {
                    // 分段读取服务器上源文件，每次处理固定大小
                    ira = fa.getIntReader(begin, every);
                    rls = ira.readIntAll();
                    if (rls != null) {
                        for (int i : rls) {
                            // 计算属于哪一个范围
                            Integer numi = (int) (new Long(i) * new Long(n) / new Long(max));
                            List<Integer> arr = wharr.get(numi);
                            if (arr == null)
                                arr = new ArrayList<>();
                            arr.add(i);
                            wharr.put(numi, arr);
                        }

                        // 将分类文件写入文件
                        for (int j = 0; j < n; j++) {
                            if (wharr.containsKey(j)) {
                                faws[j].getIntWriter().writeListInt(wharr.remove(j));
                            }
                        }

                        // 计算偏移量，下次从该位置的后一位开始读取
                        begin += rls.length;
                    } else break;
                }
                fa.close();
            }
            // 第二步骤：将不属于本机处理的分类文件，传输到对应的服务器上
            else if (step == 2) {
                for (int j = 0; j < n / m; j++) {
                    for (int i = 0; i < m; i++) {
                        if (i != index) {
                            if (faws[i * n / m + j].exists()) {
                                int[] itsn = faws[i * n / m + j].getIntReader().readIntAll();
                                Workman wm = wms[i];
                                WareHouse whij = new WareHouse();
                                whij.put("i", i);
                                whij.put("j", j);
                                whij.put("v", itsn);
                                System.out.println(i + "- receive: " + wm.receive(whij));
                                faws[i * n / m + j].close();
                            }
                        }
                    }
                }
            }
            // 第三步骤：对属于本机处理的分类文件分别进行排序处理
            else if (step == 3) {
                int[] arrl = null;
                int total = 0;
                // 对属于本机处理的分类文件（如00,01）分别进行排序处理
                for (int j = 0; j < n / m; j++) {
                    if (faws[index * n / m + j].exists()) {
                        arrl = faws[index * n / m + j].getIntReader().readIntAll();
                        ArrayAdapter.ListInt is = ArrayAdapter.getListInt();
                        is.sort(arrl);
                        total += arrl.length;
                        faws[index * n / m + j].getIntWriter(0, arrl.length).writeInt(arrl);
                        faws[index * n / m + j].close();
                    }

                    for (int i = 0; i < m; i++) {
                        if (i != index && faws[i * n / m + j].exists()) {
                            faws[i * n / m + j].delete();
                        }
                    }
                }
                resultWh.setObj("total", total);
                System.out.println("over.");
            }
            return resultWh;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    /**
     * 接收并追加写入对应的分类文件中
     * @param inhouse
     * @return
     */
    @Override
    protected boolean receive(WareHouse inhouse) {
        Integer i = (Integer) inhouse.get("i");
        Integer j = (Integer) inhouse.get("j");
        int[] v = (int[]) inhouse.get("v");
        faws[i * n / m + j].getIntWriter().writeInt(v);
        return true;
    }

    public static void main(String[] args) {
        SortFileWorker mw = new SortFileWorker(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5]);
        mw.waitWorking(args[0], Integer.parseInt(args[1]), "SortWorker");
    }
}
