/* Path：/src/doingMain.cpp
 * :doing interpreter main source code
 * Coder: chhd
 * Date: 2020.6.12
 * Version: 0.1.0
 * Update: 2020.6.19
*/ 

#include <string>
#include <vector>
#include <fstream>
#include <iostream>
#include <error.h>
#include <errno.h>
#include <err.h>
#include <string.h>
#include "directive.cpp"
#include "include/head.hpp"
#include "Unit.hpp"

int doing(std::string);


int doing(std::string f){
    int willReturn = RETURN_SUCCESS;
    std::fstream Handle;
    Handle.open(f,std::ios::in);
    
    if(!Handle.is_open()){
        std::cout << PRINT_SET_RED << "file open error at " << f << " code: "<< strerror(errno) << PRINT_SET_NULL << std::endl;
        return (willReturn=RETURN_ERROR);
    }

    std::vector<std::string> text;
    std::string buf;

    while(std::getline(Handle,buf)){
        text.push_back(buf);
    }

    Handle.close();
    //读取文件


    //预处理
    if(directive(text) != RETURN_SUCCESS){
        std::cout << PRINT_SET_RED << "directive error" << PRINT_SET_NULL << std::endl;
        return (willReturn=RETURN_ERROR);
    }
    
    for(auto ss:text){
    std::cout << ss <<std::endl;
}

    //执行
    Unit main("main");
return main.run(text);
}
