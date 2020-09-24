package dsm.service.announcement.domain.repositories

import dsm.service.announcement.domain.entities.Announcement
import org.bson.Document
import org.json.JSONObject


interface AnnouncementRepository {
    fun findSchoolAnnouncement(uuid: String): Announcement?
    // #TODO 리스트로 바꾸세요
    fun findClubAnnouncement():Announcement?
    // #TODO 리스트로 바꾸세요
    fun findByUuid(uuid: String): Announcement?

    fun findContentByUuid(uuid: String): Document?

    fun save(announcement: Announcement)

    fun saveContent(content: String, key: String): String
}