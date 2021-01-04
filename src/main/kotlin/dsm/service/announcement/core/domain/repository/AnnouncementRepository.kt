package dsm.service.announcement.core.domain.repository

import dsm.service.announcement.core.domain.entity.Announcement
import org.springframework.stereotype.Repository

@Repository
interface AnnouncementRepository {
    fun persist(announcement: Announcement): Announcement

    fun delete(announcement: Announcement)

    fun findById(id: String): Announcement?

    fun findByNumberAndType(number: Long, type: String): Announcement?

    fun findTopByOrderByNumberDesc(): Announcement?
}