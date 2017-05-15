package b_分布式协调.a_统一配置管理demo;

import com.fourinone.BeanContext;

/**
 * 如何实现公共配置管理
 */
public class ParkMasterSlave
{
	public static void main(String[] args)
	{
		String[][] master = new String[][]{{"localhost","1888"},{"localhost","1889"}};
		String[][] slave = new String[][]{{"localhost","1889"},{"localhost","1888"}};
		
		String[][] server = null;
		if(args[0].equals("M"))
			server = master;
		else if(args[0].equals("S"))
			server = slave;
		
		BeanContext.startPark(server[0][0],Integer.parseInt(server[0][1]), server);
	}
}