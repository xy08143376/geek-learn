### 根据1和2的演示，写一段对于不同GC的堆内存的总结

1. SerialGC

```shell
java -Xmx1g -Xms1g -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gclog.GCLogAnalysis
```

![image-20210926100947391](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210926100947391.png)

- 采用单线程进行垃圾收集，收集效率略低，会造成STW。
- 新生代采用复制算法，老年代采用标记整理算法。
- 从运行图看，本机由于多核机器，自适应采用的是serial的升级版本parNew收集器。
- 一般在单CPU环境的client模式下使用。

2. 并行GC

   ```shell
   java -Xmx1g -Xms1g -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gclog.GCLogAnalysis
   ```

   ![image-20210926145025333](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210926145025333.png)

   - java8默认并行GC
   - 年轻代使用parallelGC，老年代使用parallelOld GC
   - 年轻代使用复制算法，老年代使用标记整理算法
   - 多线程并行收集垃圾，STW时间更短。
   - 适用于要求吞吐量优先的系统。

3. CMS GC

   ```shell
   java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gclog.GCLogAnalysis
   ```

   ![image-20210926170844885](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210926170844885.png)

   - 默认parNew和CMS配合使用

   - 分为初始标记--并发标记--并发预清理--最终标记--并发清除--并发重置。初始标记和最终标记会有STW，其他过程不用STW。
   - 新生代采用复制算法，老年代采用标记清除算法，会产生部分内存碎片。
   - 适用于要求低延迟的系统。

4. G1 GC

   ```shell
   java -Xmx1g -Xms1g -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gclog.GCLogAnalysis
   ```

   ![image-20210926173021395](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210926173021395.png)

   - 将堆分成很多个区块，每个区块既可能是年轻代也可能是老年代。
   - 步骤分为初始标记--root区扫描--并发标记--再次标记--清理
   - 采用复制算法和标记整理算法。
   - 适用于要求低延迟的系统。

### java堆内存

- 分为新生代和老年代，新生代默认占用1/3，老年代2/3
- 设置最大堆内存-Xmx，初始化堆内存大小参数-Xms
- 默认大小为系统内存大小的1/4.
- 新生代分为Eden区和两个survior区，默认大小8:1:1



















