package dsm.service.announcement.controller.grpc

import dsm.service.announcement.proto.*
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class AnnouncementServiceGrpc(
) {
    suspend fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse {
        TODO()
    }

    suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse {
        TODO()
    }

    suspend fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        TODO()
    }

    suspend fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }

    suspend fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse {
        TODO()
    }

    suspend fun checkAnnouncement(request: CheckAnnouncementRequest): CheckAnnouncementResponse {
        TODO()
    }

    suspend fun getMyAnnouncements(request: GetMyAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }

    suspend fun searchAnnouncements(request: SearchAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }
}