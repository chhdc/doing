
package team.hammcoder.doing.MakeUnit;
import java.io.*;
import java.util.Scanner;

/*
 * �����ļ�
 */


public class MakeUnit {
	//����ʱ
	private Runtime rc=Runtime.getRuntime();
	private Process ps;
	
	//�ļ�����
	public long row=0;
	
	//unit����
	public String unit[];
	
	//�ļ�����
	private String globalFile;
	
	//����ֵ����
	public final int OK = 0;
	public final int ERROR = -1;
	
	
	//�����ļ�
	public int Make(String file) {
		try {
			globalFile = file;
			//��ȡunit
		if(checking(file)==-1) {
			System.out.println("error:unit Name overlapping in " + row);
			return -1;
		}
		
		row = 0;
		
		
		//�ж��Ƿ���Main����unit
		boolean IFMain=false;
		
		for(String s:unit) {
			if(s.equals("Main")) {
				IFMain = true;
			}
			else {
				continue;
			}
		}
		
		//û��Main���ӡ�����˳�
		if(!IFMain) {
			System.out.println("error:not find Main unit");
			return ERROR;
		}
		
		
		//ִ��Main
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
	
	
	
	
	//��ȡ�ļ�unit���ж��Ƿ��ظ�
	public int checking(String file) throws IOException {
		//���ļ�
		FileInputStream FIS = new FileInputStream(file);
		BufferedReader BR = new BufferedReader(new InputStreamReader(FIS));
		
		String bufs;
		int unit=0;
		
		
		
		//��ȡunit����
		while(true) {
			bufs = BR.readLine();
			row++;
			
			if(bufs == null) {
				break;
			}
			
			String Linebuf[] = bufs.split("\\s+");
			
			//�ж��Ƿ�Ϊunit����
			if(Linebuf.length == 3 && Linebuf[0].equals("unit") && Linebuf[2].equals("begin")) {
				unit++;
				continue;
			}
			
		}
		
		//���´��ļ�
		//��ȡunit����
		row = 0;
		
		FIS.close();
		BR.close();
		
		this.unit = new String[unit];
		
		//re open file
		FIS = new FileInputStream(file);
		BR = new BufferedReader(new InputStreamReader(FIS));
		
		
		
		//get unit Name
		int ibuf=0;
		
		//ѭ����ȡunit����
		while(true) {
			//��ʼ������
			bufs = BR.readLine();
			row++;
			
			if(bufs == null) {
				break;
			}
			
			String Linebuf[] = bufs.split("\\s+");
			
			//�ж��Ƿ�Ϊunit����
		if(Linebuf.length == 3 && Linebuf[0].equals("unit") && Linebuf[2].equals("begin")) {
			//�ж��Ƿ����ظ�unit
			for(String s:this.unit) {
				if(s == null) {
					continue;
				}
				if(s.equals(Linebuf[1])) {
					BR.close();
					return ERROR;
				}
			}
			
			//Ϊunit��������
			this.unit[ibuf] = Linebuf[1];
			ibuf++;
			continue;
		}
		
		//ѭ������
		}
		BR.close();
		FIS.close();
		
		return OK;
	}
	
	
	
	
	//ִ��unit
	public int runUnit(String file,String unitName) throws IOException, InterruptedException {
		FileInputStream FIS = new FileInputStream(file);
		BufferedReader BR = new BufferedReader(new InputStreamReader(FIS));
		
		String IN;
		
		row=0;
		
		//Ư����Main
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
		
		
		
		//ѭ��ִ������
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
			
			//�ж�ĩβ�Ƿ��� /
			//�����ٶ�ȡһ��
			if(IN.substring(IN.length()-1).equals("/")) {
				row++;
				String bufs = BR.readLine();
				bufs = bufs.trim();
				IN = IN.substring(0, IN.length()-2) + bufs;
			}
					
			
			
			
			//����end��null���߿հ��ַ�
			//ִ��ָ��
			
			if(runCommand(IN) != OK) {
				System.out.println("warning:command return warning in:" + row + " is \"" + IN + "\"");
			}
			
			
			continue;
		}
		
		BR.close();
		return OK;
	}
	
	
	
	
	//ִ��ָ��
	public int runCommand(String Command) throws IOException, InterruptedException {
		Command = Command.trim();
		String Code[] = Command.split("\\s+");
		
		//shell����
		if(Code.length >= 2 && Code[0].equals("shell")) {
			int StringFind = Command.indexOf("shell");
			
			//����1 ������
			if(StringFind == -1) {
			
			/*String sCommand[] = new String[Code.length-1];
			//boolean b=false;
			//int a=0;
			
			//��ȡshellָ�ȥ����ͷ��shell
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
			
			//ִ��
			//ps = rc.exec(sCommand);
			//int re = ps.waitFor();
			
			//System.out.println(Command + ":" + re);*/
				
				System.out.println("error:shell command useage error in:" + row);
			return ERROR;
			}
			
			//����2 �Ƽ�����
			else {
				StringFind += 6;//"shell "ռ��6��λ��
				
				ps = rc.exec(Command.substring(StringFind));
				int re = ps.waitFor();
				
				System.out.println(Command.substring(StringFind) + ":" + re);
				
				return re;
			}
		}
		
		
		//call����ָ��
		else if(Code.length == 2 && Code[0].equals("call")) {
			return runUnit(globalFile,Code[1]);
		}
		
		
		// #ע�ͣ�����
		else if(Code.length >= 1 && Code[0].equals("#")) {
			return OK;
		}
		
		
		//access �ļ�����ָ��
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
		
		
		//accdir Ŀ¼����ָ��
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
		
		
		//exit �˳�����
		else if(Code.length == 1 && Code[0].equals("exit")) {
			System.out.println("exit the build");
			System.exit(OK);
			return OK;
		}
		
		
		//errexit ִ���˳�
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
		
		
		//print ��ӡ�ַ�
		else if(Code.length >= 2 && Code[0].equals("print")) {
			System.out.println(CutString(Command,"print",1));
			return OK;
		}
		
		
		//errcall �������
		else if(Code.length >= 3 && Code[0].equals("errcall")) {
			if(runCommand(CutString(CutString(Command,"errcall",1),Code[1],1)) == ERROR) {
				return runUnit(globalFile,Code[1]);
			}
			else{
				return OK;
			}
		}
		
		
		//not λ��
		else if(Code.length >=2 && Code[0].equals("not")) {
			if(runCommand(CutString(Command,"not",1))==ERROR) {
				return OK;
			}
			else {
				return ERROR;
			}
		}
		
		//runin ִ���û�ָ��unit
		else if(Code.length == 1 && Code[0].equals("runin")) {
			Scanner in = new Scanner(System.in);
			int re = runUnit(globalFile,in.next());
			in.close();
			return re;
		}
		
		
		
		
		//δ�ҵ�ָ��
		else {
			System.out.println("error:Command not Found in:" + row + " is \"" + Command + "\"");
			return ERROR;
		}
		
	}
	
	
	
	//����ָ��ض��ַ���
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
