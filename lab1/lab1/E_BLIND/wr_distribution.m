dat1=load('a0.hex');
x=dat1(:);
tabulate(x);
x1 = sort(x);
[mu12,sigma12] = normfit(x1);
y1 = normpdf(x1,mu12,sigma12);
plot(x1, y1);
%[f,xi]=ksdensity(x);
%plot(xi,f);