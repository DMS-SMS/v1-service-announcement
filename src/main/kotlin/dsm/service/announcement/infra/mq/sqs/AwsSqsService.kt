package dsm.service.announcement.infra.mq.sqs

import org.springframework.stereotype.Component
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Header


@Component
class AwsSqsService {
    @SqsListener("\${cloud.aws.sqs.queue.url}")
    fun receive(message: String?, @Header("SenderId") senderId: String?) {
        println(String.format("%s %s", message, senderId))
    }
}