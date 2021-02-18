package dsm.service.announcement.infra.event

import dsm.service.announcement.data.grpc.auth.AuthService
import dsm.service.announcement.data.grpc.club.ClubService
import dsm.service.announcement.proto.AnnouncementEventGrpcKt
import dsm.service.announcement.proto.Empty
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class AddressUpdateEventService(
    val authService: AuthService,
    val clubService: ClubService
): AnnouncementEventGrpcKt.AnnouncementEventCoroutineImplBase() {
    override suspend fun changeAllServiceNodes(request: Empty): Empty {
        authService.updateServiceAddress()
        clubService.updateServiceAddress()
        return Empty.getDefaultInstance()
    }
}