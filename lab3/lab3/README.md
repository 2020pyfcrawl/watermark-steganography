### README

本实验使用**Eclipse**完成，内附**MATLAB**文件用来数据分析。

- 工程目录**E_BLK8__D_BLK8**内内容解析

  - .settings：Eclipse自带配置文件；

  - bin：Eclipse生成和执行区域；

  - data：输入数据样本，包含40张图片；

  - m0：所有在**无ECC的E_BLK8__D_BLK8**加入信息0的图片；

  - m0_hamming：所有在**使用汉明码的E_BLK8__D_BLK8**加入信息0的图片；

  - m101：所有在**无ECC的E_BLK8__D_BLK8**加入信息101的图片；

  - m101_hamming：所有在**使用汉明码的E_BLK8__D_BLK8**加入信息101的图片；

  - m255：所有在**无ECC的E_BLK8__D_BLK8**加入信息255的图片；

  - m255_hamming：所有在**使用汉明码的E_BLK8__D_BLK8**加入信息255的图片；

  - src：java源文件；

  - Zcc_output：所有相关性的计算结果

    - 备份了a.hex b.hex的水印防止丢失
    - zcc_m0.txt：在**无ECC的E_BLK8__D_BLK8**加入信息0的图片检测出的Zcc；
    - zcc_m0_effective.txt：在**无ECC的E_BLK8__D_BLK8**加入信息0的图片的effectiveness检测；
    - zcc_m0_hamming.txt：在**使用汉明码的E_BLK8__D_BLK8**加入信息0的图片检测出的Zcc；
    - zcc_nom.txt：没有加水印的图片（即原图）检测出的zcc；
    - 其余同理

  - a.hex：实验part1中使用的8组8*8水印的存储文件；

  - a.hex：实验part2（ECC）中使用的12组8*8水印的存储文件；

  - record.xlsx：所有实验的线性相关结果的记录；以及是否含有水印的判断和检测出的信息的值的解码结果。

  - MATLAB文件：

    - 将record.xlsx的Zcc数据写入到文件中，直接运行即可看到运行结果。

    

具体问题可以参看实验报告。