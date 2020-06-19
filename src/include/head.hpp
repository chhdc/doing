/* Path: /src/include/head.hpp
 * :doing base head source code
 * Coder: chhd
 * Date: 2020.6.12
 * Version: 0.1.0
 * Update: 2020.6.12
*/ 
#ifndef _HEAD_CPP_
#define _HEAD_CPP_

#include <string>
#include <vector>
#include <regex>

/*extern*/ char const * PRINT_SET_NULL = "\033[0m";
/*extern*/ char const * PRINT_SET_RED = "\033[31m";
/*extern*/ char const * PRINT_SET_YELLOW = "\033[33m";
/*extern*/ const char VERSION[] = "0.1.0";

const int RETURN_SUCCESS = 0;
const int RETURN_ERROR = -1;
const int RETURN_WRONG = -2;

std::string trim(std::string s);
std::vector<std::string> split(std::string s,std::string regex);
std::string replace(std::string s,std::string src,std::string to);


std::vector<std::string> split(std::string s,std::string regex){
    std::regex space_regex(regex);
    std::vector<std::string> c(std::sregex_token_iterator(s.begin(),s.end(),space_regex,-1),std::sregex_token_iterator());
    return c;
}

std::string trim(std::string s){
    //head
    for(int a = s.size();a != 0;){
        if(isspace(s[0])){
            s = s.erase(0,1);
            a = s.size();
            continue;
        }
        else{
            break;
        }
    }

    //last
    for(int a = s.size();a != 0;){
        if(isspace(s[s.size()-1])){
            s = s.erase(s.size()-1,1);
            a = s.size();
            continue;
        }
        else{
            break;
        }
    }
    return s;
}


//Just a char
std::string replace(std::string s,std::string src,std::string to){
    int ptr = s.find(src);
    while(ptr != s.npos){
        s = s.replace(ptr,1,to);
        ptr = s.find(src);
    }
    return s;
}







#endif