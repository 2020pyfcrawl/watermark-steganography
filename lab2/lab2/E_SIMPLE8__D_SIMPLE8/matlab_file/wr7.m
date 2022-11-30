x1 = [-0.03508
-0.09977
0.12193
-0.06691
-0.2
-0.12062
-0.14515
-0.05948
-0.15161
-0.16983
0.01107
-0.10328
-0.26382
-0.17606
-0.29973
-0.21965
-0.02675
-0.09761
-0.05477
0.04946
-0.13293
0.00665
-0.10871
-0.25852
-0.09662
-0.26145
-0.10532
-0.18688
-0.21994
0.01944
-0.20308
-0.07908
0.00785
-0.03081
-0.0085
-0.03212
-0.17706
-0.06896
-0.16531
-0.25964
];
x1 = sort(x1);
[mu1,sigma1] = normfit(x1);
y1 = normpdf(x1,mu1,sigma1);
x2 = [-1.0075
-1.08609
-0.87638
-1.05491
-1.19882
-1.11862
-1.13355
-1.0579
-1.15006
-1.16878
-0.90424
-1.09505
-1.26077
-1.17385
-1.29503
-1.21807
-1.01734
-1.09558
-0.98894
-0.72349
-1.09441
-0.9818
-1.10716
-1.25629
-1.09252
-1.25921
-0.95929
-1.18814
-1.21312
-0.97786
-0.52541
-0.38824
-0.35509
-0.37597
-0.33452
-0.34728
-1.17553
-1.067
-1.15497
-1.25291
-1.00594
-1.08373
-0.87433
-1.05237
-1.19654
-1.11678
-1.13133
-1.05673
-1.14794
-1.16598
-0.90264
-1.09364
-1.25871
-1.17127
-1.29461
-1.216
-1.01626
-1.0935
-0.9832
-0.72356
-1.09463
-0.97926
-1.10505
-1.25462
-1.09048
-1.2572
-0.95995
-1.18477
-1.21157
-0.97518
-0.53315
-0.38939
-0.35618
-0.37545
-0.33411
-0.34228
-1.17265
-1.06484
-1.15209
-1.25116
];
x2 = sort(x2);
[mu2,sigma2] = normfit(x2);
y2 = normpdf(x2,mu2,sigma2);
x3 = [0.93826
0.88658
1.12023
0.92054
0.79851
0.87718
0.84345
0.9385
0.84685
0.82869
0.92278
0.8888
0.73284
0.82108
0.69379
0.77873
0.96598
0.90063
0.82667
0.82537
0.82756
0.99343
0.88974
0.73962
0.89936
0.73635
0.75048
0.8137
0.77262
1.01725
0.11973
0.23434
0.38012
0.29953
0.33108
0.28186
0.8203
0.92895
0.82426
0.73341
];
x3 = sort(x3);
[mu3,sigma3] = normfit(x3);
y3 = normpdf(x3,mu3,sigma3);
plot(x1, y1, 'r', x2, y2, 'g', x3, y3, 'b');
% q=1-normcdf(0.29,mu1,sigma1)+normcdf(-0.41,mu1,sigma1)