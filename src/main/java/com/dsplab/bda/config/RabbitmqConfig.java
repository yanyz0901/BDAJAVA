package com.dsplab.bda.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitmqConfig {

    public static final String QUEUE_TASK_MOO_SEEKER = "queue_task_moo_seeker";
    public static final String QUEUE_TASK_YIELDS_CALCULATER = "queue_task_yields_calculater";
    public static final String QUEUE_TASK_TOXICITY_PREDICTOR = "queue_task_toxicity_predictor";
    public static final String EXCHANGE_TASK = "exchange_task";
    public static final String ROUTING_KEY_MOO_SEEKER = "routing_key_moo_seeker";
    public static final String ROUTING_KEY_YIELDS_CALCULATER = "routing_key_yields_calculater";
    public static final String ROUTING_KEY_TOXICITY_PREDICTOR = "routing_key_toxicity_predictor";

    @Bean(EXCHANGE_TASK)
    public Exchange EXCHANGE_TASK(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TASK).durable(true).build();
    }

    @Bean(QUEUE_TASK_MOO_SEEKER)
    public Queue QUEUE_TASK_MOO_SEEKER(){
        return new Queue(QUEUE_TASK_MOO_SEEKER,true);
    }

    @Bean(QUEUE_TASK_YIELDS_CALCULATER)
    public Queue QUEUE_TASK_YIELDS_CALCULATER(){
        return new Queue(QUEUE_TASK_YIELDS_CALCULATER,true);
    }

    @Bean(QUEUE_TASK_TOXICITY_PREDICTOR)
    public Queue QUEUE_TASK_TOXICITY_PREDICTOR(){
        return new Queue(QUEUE_TASK_TOXICITY_PREDICTOR,true);
    }

    @Bean
    public Binding BINDING_EXCHANGE_TASK_QUEUE_MOO_SEEKER(@Qualifier(QUEUE_TASK_MOO_SEEKER) Queue queue,
                                                          @Qualifier(EXCHANGE_TASK) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_MOO_SEEKER).noargs();
    }

    @Bean
    public Binding BINDING_EXCHANGE_TASK_QUEUE_YIELDS_CALCULATER(@Qualifier(QUEUE_TASK_YIELDS_CALCULATER) Queue queue,
                                                          @Qualifier(EXCHANGE_TASK) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_YIELDS_CALCULATER).noargs();
    }

    @Bean
    public Binding BINDING_EXCHANGE_TASK_QUEUE_TOXICITY_PREDICTOR(@Qualifier(QUEUE_TASK_TOXICITY_PREDICTOR) Queue queue,
                                                                 @Qualifier(EXCHANGE_TASK) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_TOXICITY_PREDICTOR).noargs();
    }

    /**
     * 自定义RabbitTemplate
     */
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置开启Mandatory，才能触发回调函数，无论消息推送结果怎么样都会强制调用回调函数
        rabbitTemplate.setMandatory(true);

        // 设置确认发送到交换机的回调函数
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            if(b){
                log.info("ConfirmCallback:     " + "相关数据：" + correlationData);
                log.info("ConfirmCallback:     " + "确认情况：" + b);
                log.info("ConfirmCallback:     " + "原因：" + s);
            }else {
                log.error("ConfirmCallback:     " + "相关数据：" + correlationData);
                log.error("ConfirmCallback:     " + "确认情况：" + b);
                log.error("ConfirmCallback:     " + "原因：" + s);
            }
        });

        // 设置确认消息已发送到队列的回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息：" + new String(message.getBody()));
            log.error("回应码：" + replyCode);
            log.error("回应信息：" + replyText);
            log.error("交换机：" + exchange);
            log.error("路由键：" + routingKey);
        });
        return rabbitTemplate;
    }
}
