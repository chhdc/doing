package main;

import java.io.*;


public class doing{

    public static void main(String []args){
        int path=0;
        int Max=args.length;
        

        while(path < Max && Max != 0){
            

            if(args[path].equals("-h")){
                System.out.printf("doing version 0.1.0\n");
                System.out.printf("Command line parameter list: \n");
                System.out.printf("-h Get help and put Stdio and exit\n");
                System.out.printf("-r inspection and run the file\n");
                path++;
                System.exit(0);
                continue;
            }

            if(args[path].equals("-r")){
                if(++path < Max){
                    System.out.printf("Start doing in %s\n",args[path]);
                }
                else{
                    System.out.printf("ERROR:No file input\n");
                }
                path++;
                continue;
            }





        }

        System.exit(0);
    }




}