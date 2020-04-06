
package team.hammcoder.doing.MakeUnit;
import java.io.*;

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
	
	private String globalFile;
	
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
			return -1;
		}
		
		
		//ִ��Main
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
					return -1;
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
		
		return 0;
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
		
		
		
		//ѭ��ִ������
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
			
			//����end��null
			//ִ��ָ��
			
			if(runCommand(IN) != 0) {
				System.out.println("warning:command return warning in:" + row);
			}
			
			
			continue;
		}
		
		
		return 0;
	}
	
	
	
	
	//ִ��ָ��
	public int runCommand(String Command) throws IOException, InterruptedException {
		
		String Code[] = Command.split("\\s+");
		
		//shell����
		if(Code.length >= 2 && Code[0].equals("shell")) {
			String sCommand[] = new String[Code.length-1];
			boolean b=false;
			int a=0;
			
			//��ȡshellָ�ȥ����ͷ��shell
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
			
			//ִ��
			ps = rc.exec(sCommand);
			int re = ps.waitFor();
			
			System.out.println(Command + ":" + re);
			return re;
		}
		
		//call����ָ��
		else if(Code.length == 2 && Code[0].equals("call")) {
			return runUnit(globalFile,Code[1]);
		}
		
		// #ע�ͣ�����
		else if(Code.length >= 1 && Code[0].equals("#")) {
			return 0;
		}
		
		//access �ļ�����ָ��
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
		
		
		//accdir Ŀ¼����ָ��
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
		
		
		
		//δ�ҵ�ָ��
		else {
			System.out.println("error:Command not Found in:" + row);
			return -1;
		}
		
	}
	
	
}
