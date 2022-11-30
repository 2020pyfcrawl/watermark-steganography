x1 = [-0.15313
0.02117
-0.38822
-0.28583
-0.3328
-0.36217
-0.3516
0.07277
-0.24632
-0.19872
-0.38245
-0.27989
-0.24584
-0.31889
-0.21476
-0.11734
-0.16894
-0.27565
-0.03683
-0.40633
-0.23485
-0.19339
-0.12299
-0.33923
-0.16729
-0.15393
-0.03271
-0.30198
-0.14834
-0.37658
-0.05116
-0.11941
-0.20176
-0.28245
-0.12503
-0.04162
-0.13897
-0.23926
-0.29363
-0.23059
];
x1 = sort(x1);
[mu1,sigma1] = normfit(x1);
y1 = normpdf(x1,mu1,sigma1);
x2 = [-1.12731
-0.96616
-1.387
-1.27428
-1.33201
-1.36046
-1.34042
-0.9256
-1.2452
-1.19733
-1.29753
-1.272
-1.24347
-1.3168
-1.21023
-1.11614
-1.1611
-1.2746
-1.01846
-1.18024
-1.20181
-1.18151
-1.12186
-1.3381
-1.16356
-1.15222
-0.89438
-1.30285
-1.14302
-1.37437
-0.37474
-0.43748
-0.57305
-0.62485
-0.46078
-0.35773
-1.13771
-1.23773
-1.28512
-1.22405
];
x2 = sort(x2);
[mu2,sigma2] = normfit(x2);
y2 = normpdf(x2,mu2,sigma2);
x3 = [0.82034
1.00812
0.61054
0.70306
0.66649
0.63648
0.63852
1.07057
0.75255
0.80018
0.53376
0.7114
0.75189
0.67891
0.78082
0.88153
0.82413
0.72296
0.79815
0.36731
0.73169
0.79476
0.87589
0.65929
0.82887
0.84454
0.82368
0.69883
0.84578
0.6212
0.27674
0.19386
0.16222
0.05272
0.2043
0.27005
0.85894
0.75982
0.69766
0.76318
0.82987
1.01624
0.619
0.71182
0.67499
0.64422
0.64637
1.07936
0.76097
0.80876
0.54095
0.72044
0.7604
0.68753
0.78955
0.88993
0.8314
0.73108
0.79777
0.3762
0.73494
0.80227
0.8843
0.66837
0.83742
0.85271
0.83341
0.70761
0.85489
0.63026
0.27621
0.19017
0.16372
0.05726
0.20796
0.27081
0.86782
0.76806
0.70589
0.77133
];
x3 = sort(x3);
[mu3,sigma3] = normfit(x3);
y3 = normpdf(x3,mu3,sigma3);
plot(x1, y1, 'r', x2, y2, 'g', x3, y3, 'b');
% q=1-normcdf(0.29,mu1,sigma1)+normcdf(-0.41,mu1,sigma1)