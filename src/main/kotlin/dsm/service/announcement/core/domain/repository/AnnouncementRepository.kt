package dsm.service.announcement.core.domain.repository

import dsm.service.announcement.core.domain.entity.Announcement

interface AnnouncementRepository {
    fun persist(announcement: Announcement): Announcement;

    fun findById(id: String): Announcement?;
}