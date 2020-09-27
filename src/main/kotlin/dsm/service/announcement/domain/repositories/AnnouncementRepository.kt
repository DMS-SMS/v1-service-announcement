package dsm.service.announcement.domain.repositories

import dsm.service.announcement.domain.entities.Announcement
import org.bson.Document
import org.json.JSONObject


interface AnnouncementRepository {
    fun findSchoolAnnouncements(uuid: String): List<Announcement?>

    fun findClubAnnouncements(): List<Announcement?>

    fun findByUuid(announcementUuid: String): Announcement?

    fun findContentByUuid(contentUuid: String): Document?

    fun validateAnnouncement(announcementUuid: String, uuid: String)

    fun save(announcement: Announcement)

    fun saveContent(contentUuid: String, content: String): String

    fun updateAnnouncement(announcementUuid: String, title: String, targetGrade: Int, targetClass: Int): String?

    fun updateContent(contentUuid: String, content: String)

    fun deleteAnnouncement(announcementUuid: String): String?

    fun deleteContent(contentUuid: String?)
}