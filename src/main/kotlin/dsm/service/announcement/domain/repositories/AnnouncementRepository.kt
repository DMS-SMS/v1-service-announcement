package dsm.service.announcement.domain.repositories

import dsm.service.announcement.domain.entities.Announcement
import org.json.JSONObject


interface AnnouncementRepository {
    fun findByType(type: String): Announcement

    fun save(announcement: Announcement)

    fun saveContent(content: String): String
}