package dsm.service.announcement.presentation.services.announcement

import dsm.service.announcement.application.services.announcement.AnnouncementService
import dsm.service.announcement.proto.*

class AnnouncementServicer(
    private val announcementService: AnnouncementService
): AnnouncementServiceGrpcKt.AnnouncementServiceCoroutineImplBase() {

    override suspend fun getAnnouncement(request: GetAnnouncementRequest): GetAnnouncementResponse {
        return announcementService.getAnnouncement(request)
    }

    override suspend fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        return super.getAnnouncementDetail(request)
    }

    override suspend fun createAnnouncement(request: CreateAnnouncementRequest): CreateAnnouncementResponse {
        return announcementService.createAnnouncement(request)
    }

    override suspend fun updateAnnouncement(request: UpdateAnnouncementRequest): UpdateAnnouncementResponse {
        return super.updateAnnouncement(request)
    }

    override suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DeleteAnnouncementResponse {
        return super.deleteAnnouncement(request)
    }
}