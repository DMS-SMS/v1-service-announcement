package dsm.service.announcement.infra.grpc

import dsm.service.announcement.data.grpc.auth.AuthService
import dsm.service.announcement.data.grpc.club.ClubService
import org.springframework.stereotype.Component

@Component
class GrpcService(
    val authService: AuthService,
    val clubService: ClubService
) {
    fun updateAllGrpcServiceAddress() {
        authService.updateServiceAddress()
        clubService.updateServiceAddress()
    }
}