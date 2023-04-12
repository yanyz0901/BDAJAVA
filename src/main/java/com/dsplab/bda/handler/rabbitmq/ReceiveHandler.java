package com.dsplab.bda.handler.rabbitmq;

import com.dsplab.bda.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 测试消费消息
 */
//@Component
@Slf4j
public class ReceiveHandler {
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_TASK_MOO_SEEKER})
    public void consumeMessage(Object msg, Message message, Channel channel){

        try {
            log.info(new String(message.getBody()));
            /**
             * 确认一条消息：
             * channel.basicAck(deliveryTag, false);
             * deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("get msg success msg = "+msg);

        } catch (Exception e) {
            //消费者处理出了问题，需要告诉队列消费失败
            try {
                /**
                 * 拒绝确认消息:
                 * channel.basicNack(long deliveryTag, boolean multiple, boolean requeue) ;
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                 * requeue：被拒绝的是否重新入队列
                 */
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false, true);
                /**
                 * 拒绝一条消息：
                 * channel.basicReject(long deliveryTag, boolean requeue);
                 * deliveryTag:该消息的index
                 * requeue：被拒绝的是否重新入队列
                 */
                //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            log.error("get msg failed msg = "+msg);
        }
    }
}
