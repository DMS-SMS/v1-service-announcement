package dsm.service.announcement.application.services.announcement

import dsm.service.announcement.proto.*

interface AnnouncementService {
    fun getAnnouncement(getAnnouncementRequest: GetAnnouncementRequest): GetAnnouncementResponse

    fun getAnnouncementDetail(getAnnouncementDetailRequest: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse

    fun createAnnouncement(createAnnouncementRequest: CreateAnnouncementRequest): CreateAnnouncementResponse
}