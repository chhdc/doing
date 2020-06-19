/* Path：/src/directive.cpp
 * :doing directive source code
 * Coder: chhd
 * Date: 2020.6.12
 * Version: 0.1.0
 * Update: 2020.6.12
*/ 



#include <vector>
#include <string>
#include <iostream>
#include "include/head.hpp"
#include <regex>


std::string trim(std::string s);
int directive(std::vector<std::string>&);
std::vector<std::string> split(std::string s,std::string regex);
std::string replace(std::string s,std::string to,std::string out);


int directive(std::vector<std::string> &s){
    //init define: Src to Aims
std::vector<std::string> defineSrc;
std::vector<std::string> defineAims;


for(std::string &c:s){
    c = trim(c);
}
//lost space


//define command Begin
for(auto &code:s){
    //if init define
    auto block = split(code,"\\s+");
    if(block.size() == 3 && block[0] == "$define"){
        defineSrc.push_back(block[1]);
        defineAims.push_back(::replace(block[2],"§"," "));
        continue;
    }

    else for(int ptr=0;ptr < defineSrc.size();){
        int FindStr = code.find(defineSrc[ptr]);
        //Not Found define
        if(FindStr == code.npos){
            ptr++;
            continue;
        }
        //Find define and replace
        else{
            std::string buf(code.substr(0,FindStr) + defineAims.at(ptr) + code.substr(FindStr + defineSrc.at(ptr).size()));
            code = buf;
            ptr++;
            continue;
        }
    }

}
//define End

for(auto ss:s){
    std::cout << ss <<std::endl;
}

return RETURN_SUCCESS;
}

