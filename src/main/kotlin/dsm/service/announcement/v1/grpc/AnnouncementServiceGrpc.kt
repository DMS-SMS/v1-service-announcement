package dsm.service.announcement.v1.grpc

import dsm.service.announcement.proto.*
import dsm.service.announcement.v1.service.AnnouncementService
import io.grpc.Context
import io.grpc.Contexts
import org.lognet.springboot.grpc.GRpcService
import org.springframework.data.mongodb.repository.Meta

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