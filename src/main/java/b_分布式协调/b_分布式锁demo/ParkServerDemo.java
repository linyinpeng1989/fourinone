package b_分布式协调.b_分布式锁demo;

import com.fourinone.BeanContext;

public class ParkServerDemo {
    public static void main(String[] args) {
        BeanContext.startPark();
    }
}