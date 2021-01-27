package dsm.service.announcement.infra.mq.sqs

import dsm.service.announcement.data.grpc.auth.AuthService
import dsm.service.announcement.data.grpc.club.ClubService
import org.springframework.stereotype.Component

@Component
class AwsSqsService(
    val authService: AuthService,
    val clubService: ClubService
) {
    fun updateAllGrpcServiceAddress() {
        authService.updateServiceAddress()
        clubService.updateServiceAddress()
    }
}