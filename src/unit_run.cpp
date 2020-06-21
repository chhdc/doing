/* Path：/src/unit_run.cpp
 * :doing unit run fn
 * Coder: chhd
 * Date: 2020.6.12
 * Version: 0.1.0
 * Update: 2020.6.21
*/ 
#ifndef _UNIT_RUN_CPP_
#define _UNIT_RUN_CPP_


#include <string>
#include "include/head.hpp"
#include <vector>
#include <iostream>
#include <fstream>
#include <thread>
#include "Unit.hpp"


int Unit::run(const std::vector<std::string> s){
    //移动到unit
    int a = 0;
    while(true){
        if(a == s.size()){
            std::cout << "error not found unit " + Unit::name << std::endl;
            return RETURN_ERROR;
        }
        auto block = split(s[a],"\\s+");
        if(block.size() >= 3 && block[0] == "unit" && block[1] == Unit::name && block[block.size()-1] == "begin"){
                break;
        }
        else{
            a++;
        }
    }

    int re;
    //执行指令
    while(s[a] != "end"){
        a++;
        re = Unit::command(s[a]);
    }


    return re;
}

int Unit::command(const std::string command){





    return RETURN_SUCCESS;
}








#endif