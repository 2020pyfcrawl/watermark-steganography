data=load("new.txt");
data= data(:);
data(data==0) = [];
%data=data(:)+0.1;
x = histogram(data);


