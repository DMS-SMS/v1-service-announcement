package dsm.service.announcement.grpc

import dsm.service.announcement.proto.*
import dsm.service.announcement.service.AnnouncementService
import org.lognet.springboot.grpc.GRpcService

@GRpcService
public class AnnouncementServiceGrpc(
        val announcementService: AnnouncementService
): AnnouncementServiceGrpcKt.AnnouncementServiceCoroutineImplBase() {
    override suspend fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse {
        return announcementService.createAnnouncement(request)
    }

    override suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse {
        return announcementService.deleteAnnouncement(request)
    }

    override suspend fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        return announcementService.getAnnouncementDetail(request)
    }

    override suspend fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse {
        return announcementService.getAnnouncements(request)
    }

    override suspend fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse {
        return announcementService.updateAnnouncement(request)
    }
}