package dsm.service.announcement.infra.mq.sqs

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.web.bind.annotation.RestController


@RestController
class AwsSqsService {
    @SqsListener("\${cloud.aws.sqs.queue.url}")
    fun receive(message: String?, @Header("SenderId") senderId: String?) {
        println(String.format("%s %s", message, senderId))
    }
}