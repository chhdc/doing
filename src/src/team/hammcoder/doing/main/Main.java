/*doing主类
 *分析参数
 */
package team.hammcoder.doing.main;
import team.hammcoder.doing.MakeUnit.*;

public class Main {

	public static MakeUnit make = new MakeUnit();
	
	
	public static void main(String[] args) {
		double startTime = System.currentTimeMillis();
		
		if(args.length == 0) {
			System.out.println("Not input data\nInput doing help get help");
			return;
		}
		
		if(args.length==1 && args[0].equals("help")) {
			System.out.println("Doing version B0.1.0\n"
					+ "usage: doing [Command] File\n"
					+ "Command list:\n"
					+ "help --print help and quit\n"
					+ "make --make file then quit\n");
			return;
		}
		
		
		
		else if(args.length >= 1 && args[0].equals("make")) {
			String makefile = "make.doing";
			
			if(args.length >= 2) {
				makefile = args[1];
			}
			
			
			if(make.Make(makefile) == make.ERROR) {
				System.out.println("error in:" + make.row);
				System.out.println("build failure");
				return;
			}
			
			double endTime = System.currentTimeMillis();
			System.out.println("build success\n" + 
			"Use " + ((endTime - startTime)/1000) + " S");
			return;
		}
		
		else {
			System.out.println("Command not found or Insufficient parameters");
			return;
		}
		
		
		
		
		
	}

	
	
	
	
	
	
}
