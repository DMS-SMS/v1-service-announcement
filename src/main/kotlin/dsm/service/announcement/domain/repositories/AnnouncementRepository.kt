package dsm.service.announcement.domain.repositories

import dsm.service.announcement.domain.entities.Announcement


interface AnnouncementRepository {
    fun findByType(type: String): Announcement

}