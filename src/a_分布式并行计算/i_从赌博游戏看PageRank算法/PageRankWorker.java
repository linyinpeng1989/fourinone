package a_分布式并行计算.i_从赌博游戏看PageRank算法;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

/**
 * Created by linyp on 2017/2/23.
 */
public class PageRankWorker extends MigrantWorker {
    public String page = null;
    public String[] links = null;

    public PageRankWorker(String page, String[] links) {
        this.page = page;
        this.links = links;
    }

    @Override
    public WareHouse doTask(WareHouse inhouse) {
        Double pr = (Double) inhouse.getObj(page);
        System.out.println(pr);

        WareHouse outhouse = new WareHouse();
        for (String p : links) {
            // 对包括的链接PR投票
            outhouse.setObj(p, pr / links.length);
        }
        return outhouse;
    }

    public static void main(String[] args) {
        String[] links = null;
        if ("A".equals(args[2]))
            links = new String[]{"B", "C"};  // A页面包含B、C链接
        else if ("B".equals(args[2]))
            links = new String[]{"C"};
        else if ("C".equals(args[2]))
            links = new String[]{"A"};

        PageRankWorker mw = new PageRankWorker(args[2], links);
        mw.waitWorking(args[0], Integer.parseInt(args[1]), "pagerankworker");
    }
}
