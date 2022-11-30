x1 = [-0.20955
-0.06629
-0.26835
-0.06827
-0.25827
-0.28227
-0.1869
-0.18046
-0.36241
-0.29073
-0.21793
-0.31842
-0.27115
-0.24543
-0.19556
-0.09849
-0.26282
-0.22373
-0.41293
-0.14492
-0.10774
-0.26962
-0.24583
-0.22252
-0.21492
-0.18743
-0.26444
-0.24652
-0.30423
-0.2434
-0.17478
-0.22817
-0.36343
-0.23468
-0.15135
-0.09627
-0.23679
-0.19076
-0.21264
-0.35189
];
x1 = sort(x1);
[mu1,sigma1] = normfit(x1);
y1 = normpdf(x1,mu1,sigma1);
x2 = [-1.18099
-1.05135
-1.26481
-1.05433
-1.25496
-1.27868
-1.17434
-1.17759
-1.359
-1.28712
-1.1336
-1.30833
-1.26655
-1.24052
-1.18852
-1.09502
-1.25254
-1.22003
-1.25037
-0.91671
-1.06607
-1.25631
-1.24242
-1.21863
-1.20923
-1.18386
-1.11789
-1.24589
-1.29771
-1.2381
-0.4988
-0.5381
-0.72376
-0.57537
-0.48888
-0.40513
-1.23288
-1.18701
-1.2019
-1.34356
];
x2 = sort(x2);
[mu2,sigma2] = normfit(x2);
y2 = normpdf(x2,mu2,sigma2);
x3 = [0.76299
0.91912
0.72814
0.91722
0.73865
0.71378
0.79953
0.81664
0.63418
0.70569
0.69477
0.6718
0.7241
0.74923
0.79932
0.89805
0.72769
0.7724
0.5723
0.62798
0.85258
0.71732
0.75075
0.77263
0.779
0.80865
0.59327
0.75193
0.68729
0.75306
0.14432
0.08423
-0.00564
0.0949
0.16981
0.21986
0.75953
0.80554
0.77637
0.63888
0.78111
0.93836
0.74858
0.93772
0.75924
0.73439
0.82032
0.83641
0.65458
0.72567
0.71276
0.69217
0.74456
0.7708
0.81952
0.91846
0.74803
0.793
0.57559
0.64496
0.871
0.73694
0.77116
0.79379
0.79943
0.82915
0.61265
0.77201
0.70676
0.77221
0.15345
0.10011
0.01617
0.11755
0.18413
0.22945
0.77945
0.82621
0.79622
0.65905
];
x3 = sort(x3);
[mu3,sigma3] = normfit(x3);
y3 = normpdf(x3,mu3,sigma3);
plot(x1, y1, 'r', x2, y2, 'g', x3, y3, 'b');
% q=1-normcdf(0.29,mu1,sigma1)+normcdf(-0.41,mu1,sigma1)