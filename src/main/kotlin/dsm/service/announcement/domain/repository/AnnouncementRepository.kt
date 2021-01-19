package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Announcement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface AnnouncementRepository: PagingAndSortingRepository<Announcement, Long> {
    fun findByUuid(uuid: String): Announcement?

    fun findTopByOrderByNumberDesc(): Announcement?

    fun findTopByOrderByNumberAsc(): Announcement?

    fun findByWriterUuidAndTypeOrderByDateDesc(writerUuid: String, type: String, pageable: Pageable): Page<Announcement>

    fun countByWriterUuidAndType(writerUuid: String, type: String): Long

    fun findByTitleContainsAndTypeOrderByDateDesc(title: String, type: String, pageable: Pageable): Page<Announcement>

    fun countByTitleContainsAndType(title: String, type: String): Long

    fun findByTypeOrderByDateDesc(type: String): MutableIterable<Announcement>

    fun findByTypeOrderByDateDesc(type: String, pageable: Pageable): Page<Announcement>

    fun countByType(type: String): Long

    fun findByNumber(number: Long): Announcement?

    fun findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
            title: String, type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<Announcement>

    fun countByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContains(title: String, type: String, targetGrade: String, targetGroup: String): Long

    fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
            type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<Announcement>

    fun countByTypeAndTargetGradeContainsAndTargetGroupContains(type: String, targetGrade: String, targetGroup: String): Long
}