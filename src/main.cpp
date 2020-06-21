/* Path：/src/main.cpp
 * :doing Main source code
 * Coder: chhd
 * Date: 2020.6.12
 * Version: 0.1.0
 * Update: 2020.6.19
*/ 

#include "include/head.hpp"
#include <iostream>
#include <string>
#include <vector>
#include <cstdlib>
#include "doingMain.cpp"
#include <ctime>

std::vector<std::string> CharArrayToStringVector(int argc,char * argv[]);
void doing_err(int code);

time_t startTime;
time_t endTime;

int main(int argc,char * argv[]){

    std::vector<std::string> args = ::CharArrayToStringVector((argc - 1),&argv[1]);//Not Transform Program Name

    if(args.size() == 1 && args[0] == "--help"){
        std::cout << "doing version " << VERSION << std::endl
        << "Web: https://github.com/chhdc/doing" << std::endl;
        std::cout << "Compile Time: " << __TIME__ << " " << __DATE__ << std::endl;
        std::cout << PRINT_SET_YELLOW 
        << "     _       _             " <<std::endl
        << "  __| | ___ (_)_ __   __ _ " << std::endl
        << " / _` |/ _ \\| | '_ \\ / _` |" << std::endl
        << "| (_| | (_) | | | | | (_| |" << std::endl
        << " \\__,_|\\___/|_|_| |_|\\__, |" <<std::endl
        << "                     |___/ " << std::endl
        << PRINT_SET_NULL;
    }

    //开始构建
    else if(args.size() == 1){
        startTime = time(&startTime);
        doing_err(doing(args[0]));
    }

    else if(args.size() == 0){
        startTime = time(&startTime);
        doing_err(doing("make.doing"));
    }


    else{
        std::cout << PRINT_SET_RED << "Not Found Command" << PRINT_SET_NULL << std::endl;
        exit(EXIT_FAILURE);
    }

return EXIT_SUCCESS;
}

//main一次性函数
std::vector<std::string> CharArrayToStringVector(int argc,char * argv[]){
    std::vector<std::string> OUT;
    int a = 0;
    while(argc != 0){
        OUT.push_back(argv[a]);
        a++;
        argc--;
    }
    return OUT;
}

//打印error
void doing_err(int code){
    endTime = time(&endTime);
    if(code == RETURN_SUCCESS){
        std::cout << "Build End:success use " << (double)((double)endTime - (double)startTime) << "s" << std::endl;
        return;
    }
    else if(code == RETURN_WRONG){
        std::cout << PRINT_SET_YELLOW << "Build End:wrong use " << (double)((double)endTime - (double)startTime) << "s" << PRINT_SET_NULL << std::endl;
        return;
    }
    else if(code == RETURN_ERROR){
        std::cout << PRINT_SET_RED << "Build End:FAILURE use " << (double)((double)endTime - (double)startTime) << "s" << PRINT_SET_NULL << std::endl;
        return;
    }

}




