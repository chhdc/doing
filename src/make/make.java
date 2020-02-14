package make;

import java.io.*;

/*doing 解释器*/



public class make{

protected FileReader Fin;

public void doing(String filePath){

//测试性输出
System.out.printf("Test:filepath= %s\n",filePath);

    /*打开文件*/
    try{
        Fin=new FileReader(filePath);


    long row=0;//记录读取的行数
    int buf[]= new int[1024];//缓冲区
    int path=0;//缓冲区指针


    while(true){

        buf[path]=Fin.read();

        //排除文件末尾
        if(buf[path]!=-1){

            //排除换行 分号
            if(buf[path] == '\n' || buf[path] == ';'){
                row++;
                continue;
            }

            //获取非空字符
            while(buf[path]!=' '){
                path++;
                buf[path] = Fin.read();
            }
            
            //测试性输出
            System.out.printf("Test:Reader Fin=%s row=%ld\n",buf,row);




        }



        else {
            //测试性输出
            System.out.printf("Test:Fin close\n");
            Fin.close();
            break;
        }


        //测试性输出
        System.out.printf("Test:The end of the continue\n");

    }
}

catch(FileNotFoundException Fin){
    System.out.printf("ERROR:file open ERROR\n");
}
catch(IOException Fin){
    System.out.printf("ERROR:IO ERROR\n");
}
finally{
    System.exit(-1);
}

}







}