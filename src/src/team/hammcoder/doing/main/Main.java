/*doing主类
 *分析参数
 */
package team.hammcoder.doing.main;
import team.hammcoder.doing.MakeUnit.*;

public class Main {

	public static MakeUnit make = new MakeUnit();
	
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Not input data\nInput doing help get help");
			return;
		}
		
		if(args[0].equals("help")) {
			System.out.println("Doing version B0.1.0\n"
					+ "usage: doing [Command] File\n"
					+ "Command list:\n"
					+ "help --print help and quit\n"
					+ "make --make file then quit\n");
			return;
		}
		
		
		
		else if(args.length == 2 && args[0].equals("make")) {
			if(make.Make(args[1]) == -1) {
				System.out.println("error in:" + make.row);
				System.out.println("build failure");
				return;
			}
			System.out.println("build success");
			return;
		}
		
		else {
			System.out.println("Command not found");
			return;
		}
		
		
		
		
		
	}

	
	
	
	
	
	
}
