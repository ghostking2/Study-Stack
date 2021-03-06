### disruptor使用场景：
1. 使用到LinkedBlockingQueue且遇到性能瓶颈
2. 顺序处理 生产者 -> 消费者 的场景
3. 跑的快
4. 轻量级消息队列

====================

## disruptor整合spring
#### 样例: 模拟下单，先下单，然后发送邮件（短信）通知，
#### 订单信息保存到数据库，数据库使用的是mongodb

===> disruptor是真的快
#### 测试结果
### disruptor使用场景：
1. 使用到LinkedBlockingQueue且遇到性能瓶颈
2. 顺序处理 生产者 -> 消费者 的场景
3. 跑的快
4. 轻量级消息队列

====================

## disruptor整合spring
#### 样例: 模拟下单，先下单，然后发送邮件（短信）通知，
#### 订单信息保存到数据库，数据库使用的是mongodb

===> disruptor是真的快
#### 测试结果
2019-04-19 10:12:55.878 Disruptor ===>index=1, 耗时 1.0 秒处理了 205039 条消息， 平均每秒 205039.0 条消息.      
2019-04-19 10:12:57.844 Disruptor ===>index=2, 耗时 2.0 秒处理了 525033 条消息， 平均每秒 262516.5 条消息.      
2019-04-19 10:13:22.614 Disruptor ===>index=3, 耗时 24.8 秒处理了 270952 条消息， 平均每秒 10925.5 条消息.        
2019-04-19 10:13:23.737 Disruptor ===>index=4, 耗时 1.1 秒处理了 488701 条消息， 平均每秒 444273.6 条消息.        
2019-04-19 10:13:44.023 Disruptor ===>index=5, 耗时 20.3 秒处理了 511297 条消息， 平均每秒 25187.0 条消息.         
2019-04-19 10:13:45.808 Disruptor ===>index=6, 耗时 1.8 秒处理了 767067 条消息， 平均每秒 426148.3 条消息.        
2019-04-19 10:14:06.276 Disruptor ===>index=7, 耗时 20.5 秒处理了 232931 条消息， 平均每秒 11362.5 条消息.        
2019-04-19 10:14:08.106 Disruptor ===>index=8, 耗时 1.8 秒处理了 869829 条消息， 平均每秒 483238.3 条消息.        
2019-04-19 10:14:26.352 Disruptor ===>index=9, 耗时 18.2 秒处理了 130169 条消息， 平均每秒 7152.1 条消息.     
2019-04-19 10:14:27.357 Disruptor ===>index=10, 耗时 1.0 秒处理了 441917 条消息， 平均每秒 441917.0 条消息.     
2019-04-19 10:14:47.806 Disruptor ===>index=11, 耗时 20.4 秒处理了 557989 条消息， 平均每秒 27352.4 条消息.     
2019-04-19 10:14:49.830 Disruptor ===>index=12, 耗时 2.0 秒处理了 964124 条消息， 平均每秒 482062.0 条消息.     
2019-04-19 10:15:08.289 Disruptor ===>index=13, 耗时 18.5 秒处理了 35874 条消息， 平均每秒 1939.1 条消息.     
2019-04-19 10:15:09.683 Disruptor ===>index=14, 耗时 1.4 秒处理了 661489 条消息， 平均每秒 472492.2 条消息.     
2019-04-19 10:15:28.619 Disruptor ===>index=15, 耗时 18.9 秒处理了 338464 条消息， 平均每秒 17908.1 条消息.     
2019-04-19 10:15:29.619 Disruptor ===>index=16, 耗时 1.0 秒处理了 607953 条消息， 平均每秒 607953.0 条消息.     
2019-04-19 10:15:48.382 Disruptor ===>index=17, 耗时 18.8 秒处理了 392045 条消息， 平均每秒 20853.5 条消息.     
2019-04-19 10:15:49.422 Disruptor ===>index=18, 耗时 1.0 秒处理了 604838 条消息， 平均每秒 604838.0 条消息.     
2019-04-19 10:16:08.162 Disruptor ===>index=19, 耗时 18.7 秒处理了 395160 条消息， 平均每秒 21131.5 条消息.     
2019-04-19 10:16:09.282 Disruptor ===>index=20, 耗时 1.1 秒处理了 849228 条消息， 平均每秒 772025.4 条消息.     
2019-04-19 10:16:29.710 Disruptor ===>index=21, 耗时 20.4 秒处理了 150649 条消息， 平均每秒 7384.8 条消息.     
2019-04-19 10:16:31.507 Disruptor ===>index=22, 耗时 1.8 秒处理了 858835 条消息， 平均每秒 477130.6 条消息.     
2019-04-19 10:16:49.885 Disruptor ===>index=23, 耗时 18.4 秒处理了 141163 条消息， 平均每秒 7671.9 条消息.     
2019-04-19 10:17:11.052 Disruptor ===>index=24, 耗时 21.2 秒处理了 999999 条消息， 平均每秒 47169.8 条消息.     
2019-04-19 10:17:13.057 Disruptor ===>index=25, 耗时 2.0 秒处理了 930194 条消息， 平均每秒 465097.0 条消息.     
2019-04-19 10:17:31.256 Disruptor ===>index=26, 耗时 18.2 秒处理了 69804 条消息， 平均每秒 3835.4 条消息.     
2019-04-19 10:17:33.029 Disruptor ===>index=27, 耗时 1.8 秒处理了 863260 条消息， 平均每秒 479588.9 条消息.     
2019-04-19 10:17:51.171 Disruptor ===>index=28, 耗时 18.1 秒处理了 136738 条消息， 平均每秒 7554.6 条消息.     
2019-04-19 10:17:52.777 Disruptor ===>index=29, 耗时 1.6 秒处理了 767905 条消息， 平均每秒 479940.6 条消息.     
2019-04-19 10:18:11.461 Disruptor ===>index=30, 耗时 18.7 秒处理了 232093 条消息， 平均每秒 12411.4 条消息.     
2019-04-19 10:18:13.509 Disruptor ===>index=31, 耗时 2.0 秒处理了 968615 条消息， 平均每秒 484307.5 条消息.     
2019-04-19 10:18:31.720 Disruptor ===>index=32, 耗时 18.2 秒处理了 31383 条消息， 平均每秒 1724.3 条消息.     
2019-04-19 10:18:33.527 Disruptor ===>index=33, 耗时 1.8 秒处理了 874317 条消息， 平均每秒 485731.7 条消息.     
2019-04-19 10:18:51.675 Disruptor ===>index=34, 耗时 18.1 秒处理了 125576 条消息， 平均每秒 6937.9 条消息.     
2019-04-19 10:18:53.695 Disruptor ===>index=35, 耗时 2.0 秒处理了 956870 条消息， 平均每秒 478435.0 条消息.     
2019-04-19 10:19:11.447 Disruptor ===>index=36, 耗时 17.8 秒处理了 43128 条消息， 平均每秒 2422.9 条消息.     
2019-04-19 10:19:12.447 Disruptor ===>index=37, 耗时 1.0 秒处理了 974230 条消息， 平均每秒 974230.0 条消息.     
2019-04-19 10:19:31.539 Disruptor ===>index=38, 耗时 19.1 秒处理了 25767 条消息， 平均每秒 1349.1 条消息.     
2019-04-19 10:19:33.246 Disruptor ===>index=39, 耗时 1.7 秒处理了 810371 条消息， 平均每秒 476688.8 条消息.     
2019-04-19 10:19:51.879 Disruptor ===>index=40, 耗时 18.6 秒处理了 189627 条消息， 平均每秒 10195.0 条消息.     
2019-04-19 10:19:53.680 Disruptor ===>index=41, 耗时 1.8 秒处理了 823600 条消息， 平均每秒 457555.6 条消息.     
2019-04-19 10:20:12.708 Disruptor ===>index=42, 耗时 19.0 秒处理了 176348 条消息， 平均每秒 9281.5 条消息.     
2019-04-19 10:20:14.397 Disruptor ===>index=43, 耗时 1.7 秒处理了 815408 条消息， 平均每秒 479651.8 条消息.     
2019-04-19 10:20:32.742 Disruptor ===>index=44, 耗时 18.3 秒处理了 184591 条消息， 平均每秒 10086.9 条消息.     
2019-04-19 10:20:34.505 Disruptor ===>index=45, 耗时 1.8 秒处理了 829599 条消息， 平均每秒 460888.3 条消息.     
2019-04-19 10:20:52.975 Disruptor ===>index=46, 耗时 18.5 秒处理了 170399 条消息， 平均每秒 9210.8 条消息.     
2019-04-19 10:20:54.743 Disruptor ===>index=47, 耗时 1.8 秒处理了 837694 条消息， 平均每秒 465385.6 条消息.     
2019-04-19 10:21:13.364 Disruptor ===>index=48, 耗时 18.6 秒处理了 162304 条消息， 平均每秒 8726.0 条消息.     
2019-04-19 10:21:15.166 Disruptor ===>index=49, 耗时 1.8 秒处理了 849796 条消息， 平均每秒 472108.9 条消息.     
2019-04-19 10:21:33.547 Disruptor ===>index=50, 耗时 18.4 秒处理了 150202 条消息， 平均每秒 8163.2 条消息.     
2019-04-19 10:21:38.547 Disruptor  总共处理了 25001024 条消息.     
