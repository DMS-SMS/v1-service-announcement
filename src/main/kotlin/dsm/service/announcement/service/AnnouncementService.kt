package dsm.service.announcement.service

import dsm.service.announcement.proto.*

interface AnnouncementService {
    fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse

    fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse

    fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse

    fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse

    fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse
}