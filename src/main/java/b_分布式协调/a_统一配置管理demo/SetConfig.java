package b_分布式协调.a_统一配置管理demo;

import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.ObjectBean;

public class SetConfig
{
	public static void main(String[] args)
	{
		ParkLocal pl = BeanContext.getPark();
		// 创建缓存节点，domain为zhejiang，node为hangzhou
		ObjectBean xihu = pl.create("zhejiang", "hangzhou", "xihu");
		try{Thread.sleep(8000);}catch(Exception e){}
		// 更新缓存节点值，domain为zhejiang，node为hangzhou
		ObjectBean yuhang = pl.update("zhejiang", "hangzhou", "yuhang");
	}
}