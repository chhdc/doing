
package team.hammcoder.doing.MakeUnit;
import java.io.*;

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
	
	private String globalFile;
	
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
			return -1;
		}
		
		
		//执行Main
		return runUnit(file,"Main");

		}
		catch(FileNotFoundException e) {
			System.out.println("error:File Not Found");
			e.printStackTrace();
			return -1;
		}
		catch(IOException e) {
			System.out.println("error:IO error");
			e.printStackTrace();
			return -1;
		}
		catch(InterruptedException e) {
			System.out.println("error:Interrupted Exception");
			e.printStackTrace();
			return -1;
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
					return -1;
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
		
		return 0;
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
			String buf[] = IN.split("\\s+");
			row++;
			
			if(IN == null) {
				return -1;
			}
			
			else if(buf.length == 3 && buf[0].equals("unit") && buf[1].equals(unitName) && buf[2].equals("begin")) {
				break;
			}
			else {
				continue;
			}	

		}
		
		
		
		//循环执行命令
		while(true) {
			IN = BR.readLine();
			row++;
			
			if(IN == null) {
				break;
			}
			
			
			String buf[] = IN.split("//s+");
			
			if(buf.length == 1 && buf[0].equals("end")) {
				return 0;
			}
			
			//不是end和null
			//执行指令
			
			if(runCommand(IN) != 0) {
				System.out.println("warning:command return warning in:" + row);
			}
			
			
			continue;
		}
		
		
		return 0;
	}
	
	
	
	
	//执行指令
	public int runCommand(String Command) throws IOException, InterruptedException {
		
		String Code[] = Command.split("\\s+");
		
		//shell命令
		if(Code.length >= 2 && Code[0].equals("shell")) {
			String sCommand[] = new String[Code.length-1];
			boolean b=false;
			int a=0;
			
			//获取shell指令，去除开头的shell
			for(String s:Code) {
				if(b) {
					sCommand[a] = s;
					a++;
				}
				else {
					b=true;
					continue;
				}
			}
			
			//执行
			ps = rc.exec(sCommand);
			int re = ps.waitFor();
			
			System.out.println(Command + ":" + re);
			return re;
		}
		
		//call调用指令
		else if(Code.length == 2 && Code[0].equals("call")) {
			return runUnit(globalFile,Code[1]);
		}
		
		// #注释，跳过
		else if(Code.length >= 1 && Code[0].equals("#")) {
			return 0;
		}
		
		//access 文件访问指令
		else if(Code.length ==2 && Code[0].equals("access")) {
			File f = new File(Code[1]);
			if(!f.exists()) {
				System.out.println("error:File Not Found:" + Code[1]);
				System.exit(-1);
				return -1;
			}
			else{
				return 0;
			}
		}
		
		
		//accdir 目录访问指令
		else if(Code.length == 2 && Code[0].equals("accdir")) {
			File f = new File(Code[1]);
			if(!f.exists() || !f.isDirectory()) {
				System.out.println("error:Dir Not Found:" + Code[1]);
				System.exit(-1);
				return -1;
			}
			else {
				return 0;
			}
			
			
			
		}
		
		
		
		//未找到指令
		else {
			System.out.println("error:Command not Found in:" + row);
			return -1;
		}
		
	}
	
	
}
