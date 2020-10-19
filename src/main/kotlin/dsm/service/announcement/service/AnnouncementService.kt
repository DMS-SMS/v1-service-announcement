package dsm.service.announcement.service

import dsm.service.announcement.proto.*

interface AnnouncementService {
    fun getAnnouncements(request: GetAnnouncementsRequest, xRequestId: String, spanContext: String): GetAnnouncementsResponse

    fun getAnnouncementDetail(request: GetAnnouncementDetailRequest, xRequestId: String, spanContext: String): GetAnnouncementDetailResponse

    fun createAnnouncement(request: CreateAnnouncementRequest, xRequestId: String, spanContext: String): DefaultAnnouncementResponse

    fun updateAnnouncement(request: UpdateAnnouncementRequest, xRequestId: String, spanContext: String): DefaultAnnouncementResponse

    fun deleteAnnouncement(request: DeleteAnnouncementRequest, xRequestId: String, spanContext: String): DefaultAnnouncementResponse
}