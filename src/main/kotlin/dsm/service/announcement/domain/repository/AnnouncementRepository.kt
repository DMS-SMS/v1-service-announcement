package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Announcement
import org.springframework.data.repository.CrudRepository

interface AnnouncementRepository: CrudRepository<Announcement, Long> {
    fun findByUuid(uuid: String): Announcement?

    fun findTopByOrderByNumberDesc(): Announcement?

    fun findTopByOrderByNumberAsc(): Announcement?

    fun findAllByType(type: String): MutableIterable<Announcement>

    fun findByNumber(number: Long): Announcement?

    fun findAllByTypeAndTargetGradeAndTargetGroup(type: String, grade: Int, group: Int): MutableIterable<Announcement>
}