package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement
import org.bson.Document

interface GetAnnouncementUseCase {
    fun getAnnouncements(uuid: String, type: String): List<Announcement?>

    fun getAnnouncement(uuid: String): Announcement?

    fun getAnnouncementContent(uuid: String): Document?
}