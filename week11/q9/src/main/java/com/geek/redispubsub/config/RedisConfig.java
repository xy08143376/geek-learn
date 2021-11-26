package com.geek.redispubsub.config;

import com.geek.redispubsub.consumer.ConsumerRedisListener;
import com.geek.redispubsub.consumer.MapMessageListener;
import com.geek.redispubsub.consumer.ObjectMessageListener;
import com.geek.redispubsub.entity.Order;
import io.lettuce.core.XGroupCreateArgs;
import io.lettuce.core.XReadArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConverters;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;

/**
 * @author itguoy
 * @date 2021-11-25 15:43
 */
@Configuration
@Slf4j
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(serializer);
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("orderTopic");
    }

    @Autowired
    ConsumerRedisListener consumerRedisListener;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(LettuceConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(consumerRedisListener, topic());
        return container;
    }


    @Autowired
    ObjectMessageListener objectMessageListener;


    @Autowired
    ThreadPoolTaskExecutor executor;

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, Order>> listenerContainer(RedisConnectionFactory connectionFactory) {
        // 初始化topic
        try {
            LettuceConnection connection = (LettuceConnection) connectionFactory.getConnection();
            XReadArgs.StreamOffset<byte[]> streamOffset = XReadArgs.StreamOffset.from(LettuceConverters.toBytes("objectGroup"), "0-0");
            connection.getNativeConnection().xgroupCreate(streamOffset,
                    LettuceConverters.toBytes("objectGroup"), XGroupCreateArgs.Builder.mkstream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Order>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ZERO)
                        .batchSize(1)
                        .targetType(Order.class)
                        .executor(executor)
                        .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, Order>> container =
                StreamMessageListenerContainer.create(connectionFactory, options);

        // 指定消费者对象
        container.register(
                StreamMessageListenerContainer.StreamReadRequest
                        .builder(StreamOffset.create("objectGroup", ReadOffset.lastConsumed()))
                        .errorHandler(error -> {
                            if (!(error instanceof QueryTimeoutException)) {
                                log.error(error.getMessage(), error);
                            }
                        })
                        .cancelOnError(e -> false)
                        .consumer(Consumer.from("objectGroup", "obj1"))
                        // 关闭自动ack确认
                        .autoAcknowledge(false)
                        .build(), objectMessageListener
        );

        container.start();
        return container;
    }


    @Autowired
    MapMessageListener mapMessageListener;

    @Bean
    public Subscription subscription(RedisConnectionFactory connectionFactory) {

        // 初始化topic
        try {
            LettuceConnection connection = (LettuceConnection) connectionFactory.getConnection();
            XReadArgs.StreamOffset<byte[]> streamOffset = XReadArgs.StreamOffset.from(LettuceConverters.toBytes("mapGroup"), "0-0");
            connection.getNativeConnection().xgroupCreate(streamOffset,
                    LettuceConverters.toBytes("mapGroup"), XGroupCreateArgs.Builder.mkstream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofSeconds(1))
                        .build();
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container =
                StreamMessageListenerContainer.create(connectionFactory, options);

        Subscription subscription = container.receiveAutoAck(Consumer.from("mapGroup", "map1"),
                StreamOffset.create("strStream", ReadOffset.lastConsumed()), mapMessageListener);

        container.start();
        return subscription;
    }

}
