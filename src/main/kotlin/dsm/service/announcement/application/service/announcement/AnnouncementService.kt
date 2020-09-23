package dsm.service.announcement.application.service.announcement

import dsm.service.announcement.proto.GetAnnouncementRequest
import dsm.service.announcement.proto.GetAnnouncementResponse

interface AnnouncementService {
    fun getAnnouncement(getAnnouncementRequest: GetAnnouncementRequest): GetAnnouncementResponse
}