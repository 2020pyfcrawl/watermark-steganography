### README

本实验使用**Eclipse**完成，内附**MATLAB**文件用来数据分析。

- 工程目录**F3/F4**内内容解析

  - .settings：Eclipse自带配置文件；

  - bin：Eclipse生成和执行区域；

  - MATLAB文件

    - his.m：统计直方图数据。

  - txt文件

    - 含有实验报告中提到的3组不同大小的测试样例（3KB,33KB,48KB）以及相应的解码文件

  - more_data_48.jpg：F3F4系统隐写使用的jpg图片。

  - code.txt：F3隐写48KB信息到上述图片修改块的DCT系数新值（用于之后解密和比对）

  - decode.txt：从code文件中的值解密的原文信息。

  - test.txt：输入文本。

  - ori.txt：原图的所有DCT系数。

  - new.txt：按照F3隐写系统理论情况新图的DCT系数（**可能有微小偏差，看实验报告**）

  - test1：F3隐写之后生成的图片。

  - 其他同名文件_F4：同理使用F4系统。

    注：上述生成文本均由当前48KBtest.txt隐写后生成，读者可以自行更改使用观测效果。

    

具体问题可以参看实验报告，如果问题请联系我，谢谢！