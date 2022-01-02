## JVM

JVM是指java虚拟机，可以理解成一种程序，可以在不同平台上启动，然后运行同一份字节码文件，从而实现一份文件，跨平台运行。

1. jvm类加载过程主要分为加载、验证、准备、解析、初始化、使用、卸载等七个阶段。
2. 类加载通过双亲委派机制，先递归的交由父类加载器进行加载，如果父类没有加载到，则由自己进行加载，能避免重复加载。
3. java内存结构主要分为：程序计数器、虚拟机栈、本地方法栈、堆内存、元空间等。
   - 堆分为新生代和老年代，默认1:2，新生代分为Eden区和survivor0和survivor1区，默认8:1:1 。

4. java启动参数分为标准参数和非标准参数，-开头为标准参数，-X开头的为非标准参数，-XX为非稳定参数，可以通过命令查看。
5. java常用的命令行工具有：jps、jinfo、jstat、jmap、jcmd、jstack等。
6. 图形化界面分析统计有：jConsole、JVisualVM.
7. GC的原理有引用计数法和可达性分析法。引用计数法可能造成循环依赖，一般用可达性分析法。
8. 常用GC算法有复制算法、标记清除算法、标记整理算法。
9. GC主要是收集堆内存的垃圾。
10. 常用的GC收集器有：SerialGC、SerialOld、ParNewGC、ParallelGC、ParallelOldGC、CMSGC、G1GC、ZGC
11. CMS在初始标记和最终标记会有STW。
12. 常用的GC组合有：Serial+SerialOld、ParNew+CMS、Parallel+ParallelOld、G1.

![JVM](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/JVM%E8%84%91%E5%9B%BE.png)

## NIO

1. 常见IO模型：阻塞IO、非阻塞IO、IO多路复用、信号驱动IO、异步IO。
   - 阻塞IO是用户线程一直等待数据准备好，然后再返回。
   - 非阻塞IO是用户线程可以去做其他事情，但是通过不断轮询数据是否准备好。
   - IO多路复用是通过单个线程监听多个套接字，当某个socket有数据后，通知用户线程。
   - 信号驱动IO是当内核中有数据准备好后，主动给用户进程发信号，用户进程不用等待。
   - 用户进程发出系统调用后立即返回，内核读取数据并拷贝到缓冲区，然后通知用户进程。

2. Netty使用的是Reactor IO多路复用模型，所以能承载大量的并发。

   ![NIO](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/NIO.png)



## 并发编程

在实际工作项目中，经常会遇到需要使用多线程的场景，能够帮助我们异步的处理任务。

1. 线程的创建方式有以下几种：继承Thread类、实现Runnable接口、实现Callable接口。
2. 线程的各状态包括初始状态、可运行状态、运行状态、阻塞状态、终止状态。
3. 线程还可以通过线程池进行创建，jdk提供了Executor接口，包含了execute方法和submit方法，execute方法没有返回值，submit方法可以提供返回值。
4. 线程池的主要参数包括coreSize，maxSize，workQueue，拒绝策略等。
5. 线程池的工作原理是当任务到来时，先使用核心线程，当任务继续增加时，放入任务队列，当达到任务队列满了时，增加线程数，当线程数达到最大线程数时，则执行拒绝策略。
6. jdk提供的几种创建线程池的方法，包括SingleThreadExecutor、FixedThreadPool、CachedThreadPool、ScheduledThreadPool。
7. 创建线程池线程数的经验，一般对于CPU密集型，设置为N或者N+1，对于IO密集型，线程池大小设置为2N或2N+1。
8. 使用多线程会带来线程安全问题，jdk提供了一些帮助开发者编程的手段，包括synchronized、volatile关键字等。synchronized能保证原子性，可见性，volatile能保证可见性，有序性，不保证原子性。
9. jdk还提供了并发包，提供了大量并发工具类，包括Lock、Condition、Atomic原子类、CountDownLatch、CyclicBarrier、Semphore、ConcurrentMap等。

​	![image-20211230104902157](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20211230104902157.png)





## Spring和ORM框架

Spring是一个java领域强大的框架，集成了各种开箱即用的组件和功能，核心模块包括Bean、Context、AOP、ORM等。

Spring的核心思想是控制反转和面向切面编程。控制反转主要思想是将对象交由Spring容器进行管理，不用我们程序去管理。面向切面编程则是通过增加一个中间代理层来增强对象的功能。

Spring的bean的加载过程包括以下步骤：

1. 实例化bean
2. 依赖注入，设置对象属性
3. 检查是否实现了BeanNameAware接口，给bean设置Id。
4. 检查是否实现了BeanFactoryAware接口，将beanFactory传入。
5. 检查是否实现了ApplicationContextAware接口，将ApplicationContext传入。
6. 如果实现了BeanPostProcessor接口，执行前置方法。
7. 如果实现了initializingBean接口，执行afterPropertiesSet方法。
8. 如果自定义了init-method方法，执行该方法。
9. 如果实现了BeanPostProcessor接口，执行后置方法。
10. 使用Bean
11. 如果实现了DisposableBean接口，执行destroy方法。
12. 如果自定义了destroy方法，执行该方法。

![Spring.drawio](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/Spring.drawio.png)

SpringBoot自动配置原理：

- 通过各种自动配置注解，以及很多条件注解来完成的。
- 加载所有的META-INF/spring.factories中存在的配置类。

Spring常用的ORM框架主要涵盖Hibernate和Mybatis。

Hibernate和Mybatis的区别：

1. Mybatis支持原生SQL，更直观，写法更灵活，对DBA友好。
2. Mybatis繁琐，写各种SQL语句比较麻烦，对于灵活变动的需求，迭代起来更方便。
3. Hibernate对于简单业务不用写SQL，方便快捷。
4. Hibernate对DBA不友好，复杂业务场景也需要写SQL。



## MySQL数据库和SQL

1. 常用的MySQL存储引擎有Innodb，MyISAM，Memory，Archive几种。常用的是Innodb和MyISAM。Innodb支持事务，是行级锁，mysiam不支持事务，是表级锁。
2. MySQL语句执行顺序：from-->on-->join-->where-->group by -->having-->select-->order by-->limit。
3. MySQL索引使用B+树实现，目的是能够减少树高，从而减少IO操作，提高效率。
4. MySQL聚簇索引包含所有数据，普通索引包含主键和该项值，取其他数据时需要回表。
5. 事务的四大特性是ACID，原子性、一致性、隔离性、持久性。
6. MySQL锁分为共享锁（S锁）和排它锁（X锁），在细分可分为共享意向锁（IS锁）、排它意向锁（IX锁）。
7. MySQL常见的锁有记录锁、间隙锁、临键锁等。
8. 事务的隔离级别分为：读未提交、读已提交、可重复读、串行化，MySQL默认的隔离级别是可重复读。
9. MySQL实现事务依靠两大日志，undo log 和redo log。undo log是撤销日志，保证事务回滚所需要。redo log是重做日志，保证事务的持久性，利用wal技术，先写日志，在写磁盘。
10. MySQL利用MVCC多版本并发控制实现事务的隔离性。

![MySQL.drawio](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/MySQL.drawio.png)



## 分库分表

为什么要分库分表，因为单机已经无法满足需求，当大量的连接进来，会有巨大的读写压力，所以做成集群机制，实现读写分离。同样由于容量问题，单独一个表或者一个数据库数据量太大，需要将表或者库进行拆分。

数据库拆分可以分为水平拆分和垂直拆分。

垂直拆分可以将业务进行细粒度的划分，将一个库，拆分成多个不同业务的数据库。还可以拆表，将多个宽表（表列字段太多的表）拆分成多个列字段少的表，然后进行关联。比如订单表、订单详情表等。

垂直拆分使得单表变小，便于维护和管理，业务分界更清晰。同时对性能和容量有所提升。但也使得系统变的复杂，库和表变多。

水平拆分意思是对数据进行分片，比如按主键进行分库分表，将单个数据量超大的表，进行拆分成多个数据量小的表，根据id进行路由。还可以按时间进行分表，根据不同的时间维度来拆分，可以根据时间直接定位到子表。

水平拆分能够解决单表数据量过大问题，解决容量问题，提升系统的性能。同时会造成SQL语句比较复杂，会造成性能问题，以及一致性问题等。



常用的分库分表框架有ShardingSphere-jdbc、TDDL等。

常用的分库分表中间件有MyCat、ShardingSphere-Proxy等。



分库分表带来了大量的有点，同时会带来分布式事务的问题，分布式事务需要考虑一致性问题。

分布式事务常用解决方案有XA，BASE柔性事务。

XA强一致性分布式事务，通过两阶段提交来实现。XA解决方案好处是实现简单，属于强一致性事务，缺点是可能会遇到单点故障、同步阻塞问题，或者因为网络抖动等造成数据不一致问题。

BASE柔性事务是保证数据的最终一致性。

BASE柔性事务常见的解决方案有TCC、AT等方案。

TCC是Try、Confirm、Cancel缩写，Confirm和Cancel只能有一个执行。TCC需要注意的问题：

- 允许空回滚
- 防止悬挂
- 幂等设计

AT模式是属于两阶段提交，自动生成反向SQL。

柔性事务保证最终一致性。

常用的分布式解决框架有Seata、Hmily、ShardingSphere等。



![分库分表.drawio](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8.drawio.png)



## RPC和微服务

RPC是远程调用服务，就像调用本地方法一样调用远程方法。

RPC的设计原理是通过代理实现、序列化、网络传输等共同实现的。

常用的RPC框架有Hessian、dubbo、gRpc等。

在微服务中，我们一般要考虑服务如何管理、服务注册与发现、负载均衡、熔断与限流、性能监控等因素。

微服务SpringCloud技术体系：Eureka，zookeeper、consul、nacos，Ribbon、Feign、Gateway、Hystrix等等.

![RPC.drawio](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/RPC.drawio.png)



## 分布式缓存

1. 哪些数据比较适合用缓存：
   - 热数据
   - 读写比较大（读/写）

2. 缓存加载时机：

   - 启动时全量加载。
   - 懒加载
     - 同步使用加载，需要使用时加载。
     - 延迟异步加载，直接返回空，启动异步任务加载，或者定期异步任务加载。

3. 缓存使用不当造成的问题：

   - 系统预热导致系统启动缓慢
   - 系统内存资源耗尽

4. 常用缓存中间件：Redis、Memcached、Hazelcast、lgnite

5. 缓存过期策略：FIFO、LRU、固定时间过期、按业务时间加权

6. 缓存带来的问题：

   - 缓存穿透：大量并发查询一个不存在的key，导致数据库压力剧增。

     解决办法：

     - 缓存不存在的的空key，后续加载会加载缓存。
     - Bloom过滤器或者RoaringBigmap判断key是否存在
     - 完全以缓存为准，使用延迟异步加载，不触发去数据库更新。

   - 缓存击穿：某个key失效的时候，正好有大量并发请求该key

     解决办法：

     - key的更新操作添加全局互斥锁
     - 完全以缓存为准，使用延迟异步加载，不触发去数据库更新

   - 缓存雪崩：某个时刻大量的key失效

     解决办法：

     - key的过期时间做的比较均匀
     - 使用的热数据尽量分散在不同机器上
     - 多台机器做主从复制或者多副本，实现高可用
     - 使用熔断限流机制，对系统负载能力进行控制。

7. Redis常用数据结构：String、list、hash、set、zset。

8. Redis高级数据结构：Bitmaps、Hyperloglogs、GEO。

9. Redis持久化方式：RDB和AOF

10. Redis常用场景：

    - 缓存业务数据：实时数据、热数据、会话缓存、token缓存等。
    - 业务数据处理；评论、点击、订单处理去重、排名、排行榜等。
    - 全局计数一致：全局流控计数、秒杀库存计数、抢红包、全局ID生成等
    - 高效统计计数：id去重、记录访问IP等全局bitmap操作，UV，PV等访问量
    - 发布订阅，Stream等操作
    - 分布式锁

Redis集群

![分布式缓存.drawio (1)](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/%E5%88%86%E5%B8%83%E5%BC%8F%E7%BC%93%E5%AD%98.drawio%20(1).png)

## 分布式消息队列

1. MQ优点：
   - 异步通信，减小线程等待时间
   - 系统解耦，降低系统间耦合，不在线时也能通信
   - 削峰填谷，减小系统峰值压力，起到缓冲作用
   - 可靠通信，提供多种消息模式，保证顺序。
2. 消息模式：点对点模式和发布订阅模式
3. 消息处理的三种语义；
   - at most once，至多一次，消息可能丢失，但不会重复发送
   - at least once，至少一次，消息不会丢失，但可能重复发送
   - exactly once，精确一次，每条消息肯定会被传输一次且仅一次
4. 消息处理通过确认机制实现事务性
5. 同一个topic或者同一个queue，保证消息的有序性。
6. 常用消息协议：jms、amqp，mqtt等。
7. 常用消息中间件：activemq、Rabbitmq、Kafka、Rocketmq、Pulsar

![消息服务.drawio](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/%E6%B6%88%E6%81%AF%E6%9C%8D%E5%8A%A1.drawio.png)







































