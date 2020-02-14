package main;

import make.make;

/*参数处理器*/

public class doing{

    public static void main(String []args){
        int path=0;//参数指针 从0开始
        int Max=args.length;//参数上标 从1开始
        


        while(path < Max && Max != 0){
            
            /*-h帮助*/
            if(args[path].equals("-h")){
                System.out.printf("doing version 0.1.0\n");
                System.out.printf("Command line parameter list: \n");
                System.out.printf("-h Get help and put Stdio and exit\n");
                System.out.printf("-make fileName inspection and run the file\n");
                path++;
                System.exit(0);
                continue;
            }
            


            /*-make 构建*/
            else if(args[path].equals("-make")){

                if(++path < Max){
                    System.out.printf("Start doing in %s\n",args[path]);
                    make interpreter=new make();
                    interpreter.doing(args[path]);
                }

                else{
                    System.out.printf("ERROR:No file Command line parameter\n");
                }
                path++;
                continue;
            }


            else{
                System.out.printf("ERROR:Command line parameter ERROR\n");
                System.exit(-1);
            }



        }

        System.exit(0);
    }




}