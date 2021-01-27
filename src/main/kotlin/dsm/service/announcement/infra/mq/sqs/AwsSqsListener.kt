package dsm.service.announcement.infra.mq.sqs

import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.web.bind.annotation.RestController
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Header


@RestController
class AwsSqsListener(
    val awsSqsService: AwsSqsService
) {
    @SqsListener("\${cloud.aws.sqs.url}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun receive(message: String?, @Header("SenderId") senderId: String?) {
        awsSqsService.updateAllGrpcServiceAddress()
    }
}