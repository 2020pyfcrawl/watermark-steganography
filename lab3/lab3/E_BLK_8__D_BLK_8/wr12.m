x1 = [
0.96251
0.99095
0.99276
0.98977
0.98227
0.97029
0.9865
0.92304
0.9902
0.99537
0.99222
0.99279
0.96661
0.96757
0.98413
0.98164
0.97584
0.99252
0.98083
0.99082
0.9878
0.99162
0.99585
0.98876
0.95173
0.99242
0.99286
0.98174
0.98699
0.99096
0.98109
0.9927
0.97848
0.99084
0.98116
0.98714
0.98107
0.99519
0.94528
0.9961



0.96784
0.99103
0.99147
0.99321
0.98551
0.96987
0.98693
0.93509
0.9922
0.99558
0.99192
0.99071
0.96847
0.9727
0.98714
0.98244
0.98021
0.99392
0.98827
0.9917
0.98961
0.99291
0.99573
0.98818
0.94768
0.99432
0.99415
0.98415
0.98686
0.99195
0.98743
0.99422
0.97525
0.99238
0.98035
0.9859
0.98379
0.99419
0.948
0.99595



0.97743
0.99074
0.99047
0.99016
0.98528
0.97274
0.98571
0.92451
0.99329
0.99589
0.99377
0.98986
0.97041
0.97633
0.99106
0.98631
0.98149
0.99271
0.9873
0.99443
0.99168
0.99301
0.99521
0.98802
0.9422
0.99412
0.995
0.98871
0.98799
0.99019
0.9824
0.99331
0.98066
0.9933
0.97634
0.98516
0.98489
0.99603
0.94663
0.99561
];
x1 = sort(x1);
[mu1,sigma1] = normfit(x1);
y1 = normpdf(x1,mu1,sigma1);
x2 = [0.48283
0.24799
0.35278
0.42179
0.22267
0.31084
0.3182
0.45333
0.31244
0.35181
0.2956
0.46486
0.39435
0.36844
0.29539
0.48223
0.13491
0.39111
0.37183
0.35312
0.24093
0.44119
0.31141
0.22751
0.33945
0.43721
0.42246
0.38735
0.2959
0.32868
0.26685
0.30401
0.3576
0.35134
0.49224
0.25145
0.32411
0.20048
0.29855
0.3624
];
x2 = sort(x2);
[mu2,sigma2] = normfit(x2);
y2 = normpdf(x2,mu2,sigma2);

plot(x1, y1, 'r', x2, y2, 'g');
% q=1-normcdf(0.29,mu1,sigma1)+normcdf(-0.41,mu1,sigma1)