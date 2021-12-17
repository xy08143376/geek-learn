> 实现了第二代和第三代的mq
>
> 第二代在g-mq-core里面，com.geek.gmq.core.v2包里面
>
> 第三代在第二代的基础上，通过g-mq-broker、g-mq-common、g-mq-producer、g-mq-consumer实现

**Tips:** <font style="color: red;">启动consumer和producer之前先启动broker</font>.

实现功能：

1. 消息保存到server端
2. 通过okHttp访问queue
3. 消息确认
4. consumer从offset增量拉取



第四代思路：

1. 消息过期可以通过给消息增加一个状态，设定一个保存时间，并且记录topic里面增加一个最久的消息的offset。然后定时去扫描topic里面，从最久的offset到最新的消息之间，对比当前时间和保存时间，得到一个新的最久的时间，两个最久的时间之间的消息，就是需要更新状态的过期的消息，然后更新过期的消息的状态。
2. 消息重试可以循环来实现。
3. 消息定时投递，可以用定时任务来实现。或者Timer
4. 批量操作分为批量投递和批量消费，批量投递可以broker新增接口，接受list参数
   - 批量消费可以给broker增加接口，接收一个消费batchSize参数，返回list消息对象给消费者。