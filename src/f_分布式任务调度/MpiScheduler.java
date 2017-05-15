package f_分布式任务调度;

import com.fourinone.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linyp on 2017/3/7.
 */
public class MpiScheduler extends Contractor {
    private static boolean runFlag = false;
    // 配置和队列管理
    private static ParkLocal pl = BeanContext.getPark();

    @Override
    public WareHouse giveTask(WareHouse inhouse) {
        // 初始化mpi机器列表配置，值为该mpi中所包含的机器数
        pl.create("mpi", "0", 50);
        pl.create("mpi", "1", 100);
        pl.create("mpi", "2", 50);

        // 调度结果数组
        List<StartResult<Integer>> rsarr = new ArrayList<>();
        while (true) {
            // 根据队列是否存在作业和是否有足够机器判断调度
            for (int i = 0; i < 3; i++) {
                // 不阻塞接收队列，获取到作业对象
                ObjectBean job = receive(i + "");
                if (job != null) {
                    WareHouse jobobj = (WareHouse) job.toObject();
                    // 获取申请机器数
                    int num = jobobj.getStringInt("computnum");
                    // 获取配置列表里剩下的机器数量
                    Integer iob = (Integer) pl.get("mpi", i + "").toObject();
                    // 若剩余的机器数大于等于申请机器数
                    if (iob >= num) {
                        System.out.print(i + " type spent " + num + " computer to doTask");
                        // 删除队列中该作业对象的队列节点
                        pl.delete(job.getDomain(), job.getNode());
                        pl.update("mpi", i + "", iob - num);
                        System.out.println(", and remain " + (iob - num));

                        // FileAdapter.createTempFile
                        // 调度job对象里的sh脚本
                        StartResult<Integer> res = BeanContext.tryStart(new FileAdapter(jobobj.getString("shdir")), "sh", jobobj.getString("sh"),
                                jobobj.getString("param"), jobobj.getString("computnum"), ">>log/" + i + ".log", "2>>&1");
                        res.setObj("job", jobobj);
                        // 将调度结果添加到结果数组
                        rsarr.add(res);
                    } else {
                        System.out.println(i + " type remain " + iob + " is not enough for " + num);
                    }
                }
            }

            // 记录完成的结果
            List<StartResult<Integer>> rmvrs = new ArrayList<>();
            // 检查结果数组是否完成调度
            for (StartResult<Integer> rs : rsarr) {
                WareHouse jobwh = (WareHouse) rs.get("job");
                int timeout = jobwh.getStringInt("timeout");

                // 检查结果是否完成或超时
                if (rs != null && rs.getStatus(StartResult.s(timeout)) != StartResult.NOTREADY) {
                    System.out.print("Result: " + rs.getResult());
                    Integer iob = (Integer) pl.get("mpi", jobwh.getString("mpiType")).toObject();
                    Integer newiob = iob + jobwh.getStringInt("computnum");
                    pl.update("mpi", jobwh.getString("mpiType"), newiob);
                    System.out.println(", and remain " + newiob + " " + jobwh);
                    rmvrs.add(rs);
                }
            }
            // 完成从结果数组删除
            rsarr.removeAll(rmvrs);
        }
    }

    // 发送到队列
    private static void send(String queue, Object obj) {
        // 在queue上创建一个匿名节点存放消息
        pl.create(queue, (Serializable) obj);
    }

    // 从队列接收
    private static ObjectBean receive(String queue) {
        ObjectBean ob = null;
        List<ObjectBean> oblist = pl.get(queue);
        if (oblist != null)
            ob = oblist.get(0);
        return ob;
    }

    public static void main(String[] args) {
        WareHouse jobwh = new WareHouse();
        jobwh.setString("mpiType", args[0]);    // mpi计算类型
        jobwh.setString("shdir", args[1]);      // sh脚本运行目录
        jobwh.setString("sh", args[2]);         // sh脚本名称
        jobwh.setString("param", args[3]);      // sh脚本参数
        jobwh.setString("computnum", args[4]);  // 申请计算机数量
        jobwh.setString("timeout", args[5]);    // 超时时间，单位为秒
        // 提交到队列
        send(args[0], jobwh);

        // 判断锁是否作为调度服务，不作为调度服务提交job后直接退出
        ObjectBean oblock = pl.get("mpi", "lock");
        if (oblock == null) {
            pl.create("mpi", "lock", true, true);
            MpiScheduler a = new MpiScheduler();
            a.giveTask(null);
        }
    }
}
