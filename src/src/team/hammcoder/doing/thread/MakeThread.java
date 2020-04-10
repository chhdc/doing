package team.hammcoder.doing.thread;

import java.io.IOException;

import team.hammcoder.doing.MakeUnit.*;


public class MakeThread implements Runnable{
	
	
	public Thread t;
	private String threadName;
	
	private String task[] = new String[2];
	
	private MakeUnit make = new MakeUnit();
	
	
	public MakeThread(String fileName,String UnitName){
		task[0] = fileName;
		task[1] = UnitName;
		threadName = UnitName;
	}
	
	
	
public void run() {
		
	try {
		if(make.checking(task[0]) == make.ERROR) {
			System.out.println("Thread error,get repeat unit");
			return;
		}
		if(make.runUnit(task[0], task[1]) == make.ERROR) {
			System.out.println("Thread build error");
		}
		
		System.out.println("Thread build OK");
		
	} catch (IOException e) {
		System.out.println("Thread error:IO error");
		e.printStackTrace();
	} catch (InterruptedException e) {
		System.out.println("Thread error:Interrupted Exception");
		e.printStackTrace();
	}
	
	
	
	
	}
	
	public void start() {
		if(t==null) {
			t = new Thread(this,threadName);
			t.start();
		}
			
	}
	
	
	
	
	
}
