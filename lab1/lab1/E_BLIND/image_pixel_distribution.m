i=imread('rec.bmp');%i是彩色图像
i1=im2gray(i);%i1就是灰度图像
x=i1(:);
tabulate(x);
[f,xi]=ksdensity(x);
plot(xi,f);
