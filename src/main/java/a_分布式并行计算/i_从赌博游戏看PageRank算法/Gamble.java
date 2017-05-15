package a_分布式并行计算.i_从赌博游戏看PageRank算法;

/**
 * Created by linyp on 2017/2/23.
 * 从赌博游戏看PageRank算法
 */
public class Gamble {
    public static double x1 = 1.0, x2 = 1.0, x3 = 1.0;

    public static void playGame() {
        double x2_income = x1 / 2.0;
        double x3_income = x1 / 2.0 + x2;
        double x1_income = x3;
        x1 = x1_income;
        x2 = x2_income;
        x3 = x3_income;
        System.out.println("x1: " + x1 + ", x2:" + x2 + ", x3: " + x3);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            System.out.println("第" + i + "轮");
            playGame();
        }
    }
}
