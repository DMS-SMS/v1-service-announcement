package dsm.service.announcement.domain.repositories

import dsm.service.announcement.domain.entities.Announcement
import org.bson.Document
import org.json.JSONObject


interface AnnouncementRepository {
    fun findSchoolAnnouncements(uuid: String): List<Announcement?>

    fun findClubAnnouncements(): List<Announcement?>

    fun findByUuid(uuid: String): Announcement?

    fun findContentByUuid(uuid: String): Document?

    fun save(announcement: Announcement)

    fun saveContent(content: String, key: String): String
}