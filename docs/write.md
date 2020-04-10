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
 | access  | 检测文件是否存在，不存在就返回error | access Main.c |
 | accdir  | 检测目录是否存在，不存在就返回error | accdir /include |
 | exit    | 退出构建程序 | exit |
 | errexit | 执行后一条指令，返回error就退出程序 | errexit access Main.c |
 | print   | 打印后面跟着的任何字符,自动添加换行符 | print Hallo World |
 | errcall | 跟上一个unit名称和一条指令，指令返回error则执行指定unit | errcall close gcc Main.c |
 | not     | 将跟着的指令的返回结果进行反运算，error->ok  ok->error | errexit not access not |
 | runin   | 根据用户的输入执行unit，返回unit执行结果 | runin |

 ### doing特性
 把 / 添加在命令末尾，即可断行命令  
 如：  
 uniy Main begin  
 print Hallo  /   
 World  
 end  
 以上指令输出Hallo World

 ### doing多线程
doing可以通过   
together unitName   
来多线程执行unit   
一个unit最多只能有一个线程执行   

test unitName   
来测试unit是否执行完成     
执行完成或不存在返回error   

break unitName  
来中断一个线程


