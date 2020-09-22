package dsm.service.announcement.presentation.service

import dsm.service.announcement.proto.*

class AnnouncementService: AnnouncementServiceGrpcKt.AnnouncementServiceCoroutineImplBase() {
    override suspend fun getAnnouncement(request: GetAnnouncementRequest): GetAnnouncementResponse {
        return GetAnnouncementResponse.newBuilder()
            .build()
    }

    override suspend fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        return super.getAnnouncementDetail(request)
    }

    override suspend fun createAnnouncement(request: CreateAnnouncementRequest): CreateAnnouncementResponse {
        return super.createAnnouncement(request)
    }

    override suspend fun updateAnnouncement(request: UpdateAnnouncementRequest): UpdateAnnouncementResponse {
        return super.updateAnnouncement(request)
    }

    override suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DeleteAnnouncementResponse {
        return super.deleteAnnouncement(request)
    }
}