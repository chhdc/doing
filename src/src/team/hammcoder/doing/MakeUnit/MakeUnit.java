
package team.hammcoder.doing.MakeUnit;
import java.io.*;
import java.util.Scanner;

/*
 * 构建文件
 */


public class MakeUnit {
	//运行时
	private Runtime rc=Runtime.getRuntime();
	private Process ps;
	
	//文件行数
	public long row=0;
	
	//unit变量
	public String unit[];
	
	//文件名称
	private String globalFile;
	
	//返回值定义
	public final int OK = 0;
	public final int ERROR = -1;
	
	
	//构建文件
	public int Make(String file) {
		try {
			globalFile = file;
			//获取unit
		if(checking(file)==-1) {
			System.out.println("error:unit Name overlapping in " + row);
			return -1;
		}
		
		row = 0;
		
		
		//判断是否有Main构建unit
		boolean IFMain=false;
		
		for(String s:unit) {
			if(s.equals("Main")) {
				IFMain = true;
			}
			else {
				continue;
			}
		}
		
		//没有Main则打印警告退出
		if(!IFMain) {
			System.out.println("error:not find Main unit");
			return ERROR;
		}
		
		
		//执行Main
		return runUnit(file,"Main");

		}
		catch(FileNotFoundException e) {
			System.out.println("error:File Not Found");
			e.printStackTrace();
			return ERROR;
		}
		catch(IOException e) {
			System.out.println("error:IO error");
			e.printStackTrace();
			return ERROR;
		}
		catch(InterruptedException e) {
			System.out.println("error:Interrupted Exception");
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	
	
	//获取文件unit及判断是否重复
	public int checking(String file) throws IOException {
		//打开文件
		FileInputStream FIS = new FileInputStream(file);
		BufferedReader BR = new BufferedReader(new InputStreamReader(FIS));
		
		String bufs;
		int unit=0;
		
		
		
		//获取unit数量
		while(true) {
			bufs = BR.readLine();
			row++;
			
			if(bufs == null) {
				break;
			}
			
			String Linebuf[] = bufs.split("\\s+");
			
			//判断是否为unit定义
			if(Linebuf.length == 3 && Linebuf[0].equals("unit") && Linebuf[2].equals("begin")) {
				unit++;
				continue;
			}
			
		}
		
		//重新打开文件
		//获取unit名称
		row = 0;
		
		FIS.close();
		BR.close();
		
		this.unit = new String[unit];
		
		//re open file
		FIS = new FileInputStream(file);
		BR = new BufferedReader(new InputStreamReader(FIS));
		
		
		
		//get unit Name
		int ibuf=0;
		
		//循环获取unit名称
		while(true) {
			//初始化工作
			bufs = BR.readLine();
			row++;
			
			if(bufs == null) {
				break;
			}
			
			String Linebuf[] = bufs.split("\\s+");
			
			//判断是否为unit定义
		if(Linebuf.length == 3 && Linebuf[0].equals("unit") && Linebuf[2].equals("begin")) {
			//判断是否有重复unit
			for(String s:this.unit) {
				if(s == null) {
					continue;
				}
				if(s.equals(Linebuf[1])) {
					BR.close();
					return ERROR;
				}
			}
			
			//为unit附上名称
			this.unit[ibuf] = Linebuf[1];
			ibuf++;
			continue;
		}
		
		//循环结束
		}
		BR.close();
		FIS.close();
		
		return OK;
	}
	
	
	
	
	//执行unit
	public int runUnit(String file,String unitName) throws IOException, InterruptedException {
		FileInputStream FIS = new FileInputStream(file);
		BufferedReader BR = new BufferedReader(new InputStreamReader(FIS));
		
		String IN;
		
		row=0;
		
		//漂移至Main
		while(true) {
			IN = BR.readLine();
			if(IN == null) {
				System.out.println("Not found Unit:" + unitName);
				BR.close();
				return ERROR;
			}
			
			String buf[] = IN.split("\\s+");
			row++;
			
			
			if(buf.length == 3 && buf[0].equals("unit") && buf[1].equals(unitName) && buf[2].equals("begin")) {
				break;
			} else {
				continue;
			}	

		}
		
		
		
		//循环执行命令
		while(true) {
			IN = BR.readLine();
			row++;
			IN = IN.trim();
			
			if(IN == null) {
				break;
			}
			else if(IN.equals(" ") || IN.equals("") || IN.equals("\n") || IN.equals("\r") || IN.equals("\r\n")) {
				continue;
			}
			
			String buf[] = IN.split("//s+");
			
			if(buf.length == 1 && buf[0].equals("end")) {
				BR.close();
				return 0;
			}
			
			//判断末尾是否是 /
			//是则再读取一行
			if(IN.substring(IN.length()-1).equals("/")) {
				row++;
				String bufs = BR.readLine();
				bufs = bufs.trim();
				IN = IN.substring(0, IN.length()-2) + bufs;
			}
					
			
			
			
			//不是end和null或者空白字符
			//执行指令
			
			if(runCommand(IN) != OK) {
				System.out.println("warning:command return warning in:" + row + " is \"" + IN + "\"");
			}
			
			
			continue;
		}
		
		BR.close();
		return OK;
	}
	
	
	
	
	//执行指令
	public int runCommand(String Command) throws IOException, InterruptedException {
		Command = Command.trim();
		String Code[] = Command.split("\\s+");
		
		//shell命令
		if(Code.length >= 2 && Code[0].equals("shell")) {
			int StringFind = Command.indexOf("shell");
			
			//方法1 已弃用
			if(StringFind == -1) {
			
			/*String sCommand[] = new String[Code.length-1];
			//boolean b=false;
			//int a=0;
			
			//获取shell指令，去除开头的shell
			//for(String s:Code) {
				//if(b) {
					//sCommand[a] = s;
					//a++;
				//}
				//else {
					//b=true;
					//continue;
				//}
			//}
			
			//执行
			//ps = rc.exec(sCommand);
			//int re = ps.waitFor();
			
			//System.out.println(Command + ":" + re);*/
				
				System.out.println("error:shell command useage error in:" + row);
			return ERROR;
			}
			
			//方法2 推荐方法
			else {
				StringFind += 6;//"shell "占用6个位置
				
				ps = rc.exec(Command.substring(StringFind));
				int re = ps.waitFor();
				
				System.out.println(Command.substring(StringFind) + ":" + re);
				
				return re;
			}
		}
		
		
		//call调用指令
		else if(Code.length == 2 && Code[0].equals("call")) {
			return runUnit(globalFile,Code[1]);
		}
		
		
		// #注释，跳过
		else if(Code.length >= 1 && Code[0].equals("#")) {
			return OK;
		}
		
		
		//access 文件访问指令
		else if(Code.length >=2 && Code[0].equals("access")) {
			File f = new File(CutString(Command,"access",1));
			if(!f.exists()) {
				System.out.println("error:File Not Found:" + CutString(Command,"access",1));
				return ERROR;
			}
			else{
				return OK;
			}
		}
		
		
		//accdir 目录访问指令
		else if(Code.length >= 2 && Code[0].equals("accdir")) {
			File f = new File(CutString(Command,"accdir",1));
			if(!f.exists() || !f.isDirectory()) {
				System.out.println("error:Dir Not Found:" + CutString(Command,"accdir",1));
				return ERROR;
			}
			else {
				return OK;
			}
			
		}
		
		
		//exit 退出程序
		else if(Code.length == 1 && Code[0].equals("exit")) {
			System.out.println("exit the build");
			System.exit(OK);
			return OK;
		}
		
		
		//errexit 执行退出
		else if(Code.length >= 2 && Code[0].equals("errexit")) {
			if(runCommand(CutString(Command,"errexit",1)) == ERROR) {
				System.out.println("exit the build");
				System.exit(ERROR);
				return ERROR;
			}
			else {
				return OK;
			}
		}
		
		
		//print 打印字符
		else if(Code.length >= 2 && Code[0].equals("print")) {
			System.out.println(CutString(Command,"print",1));
			return OK;
		}
		
		
		//errcall 错误调用
		else if(Code.length >= 3 && Code[0].equals("errcall")) {
			if(runCommand(CutString(CutString(Command,"errcall",1),Code[1],1)) == ERROR) {
				return runUnit(globalFile,Code[1]);
			}
			else{
				return OK;
			}
		}
		
		
		//not 位反
		else if(Code.length >=2 && Code[0].equals("not")) {
			if(runCommand(CutString(Command,"not",1))==ERROR) {
				return OK;
			}
			else {
				return ERROR;
			}
		}
		
		//runin 执行用户指定unit
		else if(Code.length == 1 && Code[0].equals("runin")) {
			Scanner in = new Scanner(System.in);
			int re = runUnit(globalFile,in.next());
			in.close();
			return re;
		}
		
		
		
		
		//未找到指令
		else {
			System.out.println("error:Command not Found in:" + row + " is \"" + Command + "\"");
			return ERROR;
		}
		
	}
	
	
	
	//根据指令截断字符串
	public String CutString(String src,String Cut,int add) {
		int re = src.indexOf(Cut);
		String res = null;
		
		if(re == -1) {
			return res;
		}
		
		re += Cut.length()+add;
		
		
		return src.substring(re);
	}
	
	
	
}
