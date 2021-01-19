package dsm.service.announcement.controller.grpc

import dsm.service.announcement.proto.*
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class AnnouncementServiceGrpc(
        private val announcementService: AnnouncementService

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

    override suspend fun checkAnnouncement(request: CheckAnnouncementRequest): CheckAnnouncementResponse {
        return announcementService.checkAnnouncement(request)
    }

    override suspend fun getMyAnnouncements(request: GetMyAnnouncementsRequest): GetAnnouncementsResponse {
        return announcementService.getMyAnnouncements(request)
    }

    override suspend fun searchAnnouncements(request: SearchAnnouncementsRequest): GetAnnouncementsResponse {
        return announcementService.searchAnnouncements(request)
    }
}