/* Path：/src/Unit.cpp
 * :doing unit class
 * Coder: chhd
 * Date: 2020.6.12
 * Version: 0.1.0
 * Update: 2020.6.21
*/ 
#ifndef _D_UNIT_HPP_D_
#define _D_UNIT_HPP_D_

#include <string>
#include "include/head.hpp"
#include <vector>
#include <iostream>
#include <fstream>
#include <thread>


class Unit{
    public:
    Unit(std::string n);
    ~Unit();
    int run(const std::vector<std::string> s);
    std::vector<std::string> parameter;

    private:
    std::string name;
    std::vector<std::thread> t;
    int command(const std::string command);


};
Unit::Unit(std::string n): name(n){
    return;
}

Unit::~Unit(){
    for(auto &t:Unit::t){
        t.join();
    }
}

#include "unit_run.cpp"

#endif