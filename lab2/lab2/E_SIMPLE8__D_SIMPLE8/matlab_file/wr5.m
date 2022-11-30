x1 = [-0.08998
-0.01375
0.0738
-0.05823
-0.17948
-0.24971
-0.24489
-0.06558
-0.19477
-0.2554
-0.21599
-0.09739
-0.34967
-0.16268
-0.04302
-0.26582
-0.18458
-0.30006
-0.30535
-0.22516
-0.32059
-0.22714
-0.04663
-0.25706
-0.24579
-0.18193
-0.16214
-0.28504
-0.34978
-0.13874
-0.02395
-0.02385
-0.33347
-0.14748
-0.17426
-0.25925
-0.27911
-0.32091
-0.2288
-0.24816
];
x1 = sort(x1);
[mu1,sigma1] = normfit(x1);
y1 = normpdf(x1,mu1,sigma1);
x2 = [-1.05806
-0.99547
-0.91951
-1.04176
-1.17301
-1.24292
-1.22913
-1.05923
-1.18818
-1.2481
-1.12708
-1.08475
-1.34169
-1.15489
-1.03661
-1.25916
-1.17082
-1.29283
-1.26889
-0.9989
-1.27588
-1.21152
-1.04003
-1.24952
-1.23711
-1.17501
-1.01569
-1.28047
-1.33852
-1.13075
-0.34302
-0.33081
-0.70265
-0.47512
-0.50634
-0.57164
-1.27226
-1.31384
-1.21391
-1.23666
-1.06366
-0.99957
-0.92407
-1.04565
-1.17769
-1.24706
-1.23289
-1.06368
-1.19274
-1.25295
-1.13078
-1.08867
-1.34594
-1.15909
-1.03754
-1.26378
-1.17493
-1.29785
-1.26399
-1.00014
-1.2841
-1.21556
-1.0446
-1.25476
-1.24095
-1.17966
-1.01833
-1.28478
-1.34263
-1.136
-0.35257
-0.3385
-0.70669
-0.48331
-0.50712
-0.57299
-1.27671
-1.31859
-1.21776
-1.24178
];
x2 = sort(x2);
[mu2,sigma2] = normfit(x2);
y2 = normpdf(x2,mu2,sigma2);
x3 = [0.87816
0.96762
1.0671
0.92471
0.81398
0.74308
0.73952
0.92802
0.79863
0.73773
0.69336
0.88857
0.64249
0.82963
0.94787
0.72761
0.8016
0.69321
0.54128
0.54711
0.63489
0.75604
0.94677
0.73594
0.74478
0.81102
0.69501
0.71033
0.63819
0.8538
0.29726
0.29631
0.02844
0.19436
0.15845
0.05577
0.7139
0.67299
0.75665
0.73977
];
x3 = sort(x3);
[mu3,sigma3] = normfit(x3);
y3 = normpdf(x3,mu3,sigma3);
plot(x1, y1, 'r', x2, y2, 'g', x3, y3, 'b');
% q=1-normcdf(0.29,mu1,sigma1)+normcdf(-0.41,mu1,sigma1)