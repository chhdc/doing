/* Path：/src/directive.cpp
 * :doing directive source code
 * Coder: chhd
 * Date: 2020.6.12
 * Version: 0.1.0
 * Update: 2020.6.19
*/ 



#include <vector>
#include <string>
#include <iostream>
#include "include/head.hpp"
#include <regex>
#include <iterator>
#include <fstream>


std::string trim(std::string s);
int directive(std::vector<std::string>&);
std::vector<std::string> split(std::string s,std::string regex);
std::string replace(std::string s,std::string to,std::string out);


int directive(std::vector<std::string> &s){
    {
    //$include 开始处理
    for(auto a = 0;a != s.size();){
        auto &code = s.at(a++);
        auto block = split(code,"\\s+");

        if(block.size() == 2 && block[0] == "$include"){
            std::fstream Handle;
            Handle.open(block[1],std::ios::in);

            if(!Handle.is_open()){
        std::cout << PRINT_SET_RED << "file open error at " << block[1] << " code: "<< strerror(errno) << PRINT_SET_NULL << std::endl;
    }

    code = "# include " + block[1];
    std::vector<std::string> text;
    std::string buf;

    while(std::getline(Handle,buf)){
        text.push_back(buf);
    }

    Handle.close();

    //读取完成文件，写入
    std::vector<std::string> bufs;
    for(auto &co:text){
        bufs.push_back(co);
    }
    
    for(auto &co:s){
        bufs.push_back(co);
    }


    s.clear();
    for(auto &co:bufs){
        s.push_back(co);
    }
        }

    else continue;
    }
//$include处理完毕
    }


//丢弃空格
for(std::string &c:s){
    c = trim(c);
}


//defin开始处理
{
std::vector<std::string> defineSrc;
std::vector<std::string> defineAims;


for(auto &code:s){
    //定义define
    auto block = split(code,"\\s+");
    if(block.size() >= 2 && block[0] == "$define"){

        if(block.size() > 3){
            std::cout << PRINT_SET_RED << "directive error:" + code << PRINT_SET_NULL << std::endl;
            return RETURN_ERROR;
        }

        defineSrc.push_back(block[1]);
        defineAims.push_back(block.size()==2?"":replace(block[2],"§"," "));
        code = "# define (" + block[1] + ") " + "(" + (block.size()==2?"":replace(block[2],"§"," ")) + ")";
        continue;
    }

    //非define定义，替换
    else for(int ptr=0;ptr < defineSrc.size();){
        int FindStr = code.find(defineSrc[ptr]);
        //未找到defineSrc
        if(FindStr == code.npos){
            ptr++;
            continue;
        }
        //找到defineSrc转换成defineAims
        else{
            std::string buf(code.substr(0,FindStr) + defineAims.at(ptr) + code.substr(FindStr + defineSrc.at(ptr).size()));
            code = buf;
            ptr++;
            continue;
        }
    }

}
//define处理完毕
}

//去注释和空行
{
std::vector<int> ptr;
int bPtr = 0;
while(bPtr != s.size()){
    if(s[bPtr][0] == '#' || s[bPtr] == ""){
        ptr.push_back(bPtr);
    }
    else;
    bPtr++;
}
while(ptr.size() != 0){
    auto a = s.begin();
    std::advance(a,ptr[ptr.size()-1]);
    s.erase(a);
    ptr.pop_back();
}
}





return RETURN_SUCCESS;
}