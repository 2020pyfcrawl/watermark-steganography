### README

本实验使用**Eclipse**完成，内附**MATLAB**文件用来数据分析。

- 工程目录**E_SIMPLE8__D_SIMPLE8**内内容解析

  - .settings：Eclipse自带配置文件；

  - bin：Eclipse生成和执行区域；

  - data：输入数据样本，包含40张512*512图片；

  - matlab_file：使用的MATLAB脚本文件。

    - wr1-wr8：实验part1的线性相关检测值的曲线模拟脚本。
    - wrs：实验part2的40组水印的线性相关检测值的曲线模拟脚本。
    - wr_distribution：对水印值分布情况检测的脚本。

  - output_m0：实验part1对data中的样本嵌入信息0的水印生成的40张图片；

  - output_m101：实验part1对data中的样本嵌入信息101的水印生成的40张图片；

  - output_m255：实验part1对data中的样本嵌入信息255的水印生成的40张图片；

  - output_result_txt：实验中所有输出txt文本的文件夹

    - result0：实验part1中data文件夹中原图与水印的线性相关检测值计算结果；
    - result_m0/result_m101/result_m255：实验part1中加入0,101,255信息后生成的图片与水印的线性相关值计算结果；
    - result_wrs_ori：实验part2中lena512.bmp和40组水印的线性相关检测值计算结果；
    - result_wrs_m101：实验part2中用40组水印嵌入信息101后图片与对应水印的线性相关性检测值计算结果；
    - fileread sequence/wrread sequence：实验part2中遍历40组水印生成图片与遍历40组水印计算线性相关及检测值的顺序记录（在console输出的信息）。

  - output_wrs_m101：实验part2中使用40组水印对lena512.bmp加信息101后生成的图片；

  - src：java源文件；

  - wrs：生成的40组水印存储的文件（wr0-wr39）；

  - a.hex：实验part1中使用的一组水印的存储文件；

  - record.xlsx：所有实验的线性相关结果的记录；以及是否含有水印的判断和检测出的信息的值的解码结果。

  - readme：用于嵌入的信息的二进制表示。

  - lena512.bmp：用于实验part2的原图。

    

具体问题可以参看实验报告。