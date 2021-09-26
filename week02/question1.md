1. java8默认并行GC，有一次full gc

   ![image-20210924083718299](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210924083718299.png)

2. java8 串行GC，没有进行full gc

   ![image-20210924094030863](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210924094030863.png)

3. java8 CMS GC, 初始标记->并发标记->并发预清理->最终标记->并发清理->并发重置

   ![image-20210924094421558](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210924094421558.png)

4. java8使用G1 GC

   ![image-20210924095325356](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210924095325356.png)

   ![](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210924095808517.png)





