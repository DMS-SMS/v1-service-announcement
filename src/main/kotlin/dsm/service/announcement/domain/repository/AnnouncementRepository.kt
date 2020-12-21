package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Announcement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface AnnouncementRepository: PagingAndSortingRepository<Announcement, Long> {
    fun findByUuid(uuid: String): Announcement?

    fun findTopByOrderByNumberDesc(): Announcement?

    fun findTopByOrderByNumberAsc(): Announcement?

    fun findByWriterUuidAndType(writerUuid: String, type: String, pageable: Pageable): Page<Announcement>

    fun findByTitleContainsAndType(title: String, type: String, pageable: Pageable): Page<Announcement>

    fun findByType(type: String): MutableIterable<Announcement>

    fun findByType(type: String, pageable: Pageable): Page<Announcement>

    fun findByNumber(number: Long): Announcement?

    fun findByTitleContainsAndTypeAndTargetGradeContainingAndTargetGroupContaining(
            title: String, type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<Announcement>

    fun findByTypeAndTargetGradeContainsAndTargetGroupContains(
            type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<Announcement>
}