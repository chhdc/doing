# doing编写指南
### doing基础语法简介
 doing使用单元（unit）来进行构建   
 每个任务都被划分成一个单元  
 定义看起来如下：  
 unit unitName begin  
 \# todo  
 end  
 每个文件内的unitName不能重合  
 每个doing文件在构建前都会被检测  
 unitName应该仅包含字母和数字下划线和连字符等，不应出现空格换行等  
### doing基础指令
 每个单元内包含一些指令，可以是shell指令，也可以是doing提供的跨平台指令    
 doing每次从Main开始执行  
 以下是指令列表  
 | 指令名称 | 指令作用 | 用法示例 |
 | :-------: | :--------: | :-------: |
 | shell   | 执行一条shell指令  | shell gcc Main.c -o Main |
 | end     | 在每个unit结束时调用，也用于返回父unit | end |
 | call    | 调用一个unit | call Main |
 | access  | 检测文件是否存在，不存在就退出 | access Main.c |
 | accdir  | 检测目录是否存在，不存在就退出 | accdir /include |