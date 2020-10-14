package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Announcement
import org.springframework.data.repository.CrudRepository

interface AnnouncementRepository: CrudRepository<Announcement, Long> {
    fun findByUuid(uuid: String): Announcement?
}