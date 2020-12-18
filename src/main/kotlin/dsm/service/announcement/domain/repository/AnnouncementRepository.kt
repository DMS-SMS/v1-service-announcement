package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Announcement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface AnnouncementRepository: CrudRepository<Announcement, Long> {
    fun findByUuid(uuid: String): Announcement?

    fun findTopByOrderByNumberDesc(): Announcement?

    fun findTopByOrderByNumberAsc(): Announcement?

    fun findAllByTitleContainsAndType(title: String, type: String, pageable: Pageable): MutableIterable<Announcement>

    fun findAllByType(type: String): MutableIterable<Announcement>

    fun findAllByType(type: String, pageable: Pageable): Page<Announcement>

    fun findByNumber(number: Long): Announcement?

    fun findAllByTitleContainsAndTypeAndTargetGradeAndTargetGroup(title: String, type: String, grade: Int, group: Int, pageable: Pageable): Page<Announcement>

    fun findAllByTypeAndTargetGradeAndTargetGroup(type: String, grade: Int, group: Int, pageable: Pageable): Page<Announcement>
}