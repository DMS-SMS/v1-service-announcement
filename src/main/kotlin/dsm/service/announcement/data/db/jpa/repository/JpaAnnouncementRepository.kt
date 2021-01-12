package dsm.service.announcement.data.db.jpa.repository

import dsm.service.announcement.data.db.jpa.model.AnnouncementModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface JpaAnnouncementRepository: PagingAndSortingRepository<AnnouncementModel, Long> {
    fun findByUuid(uuid: String): AnnouncementModel?

    fun findTopByOrderByNumberDesc(): AnnouncementModel?

    fun findTopByOrderByNumberAsc(): AnnouncementModel?

    fun findByWriterUuidAndTypeOrderByDateDesc(writerUuid: String, type: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByWriterUuidAndType(writerUuid: String, type: String): Long

    fun findByTitleContainsAndTypeOrderByDateDesc(title: String, type: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByTitleContainsAndType(title: String, type: String): Long

    fun findByTypeOrderByDateDesc(type: String): MutableIterable<AnnouncementModel>

    fun findByTypeOrderByDateDesc(type: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByType(type: String): Long

    fun findByNumber(number: Long): AnnouncementModel?

    fun findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        title: String, type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContains(title: String, type: String, targetGrade: String, targetGroup: String): Long

    fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByTypeAndTargetGradeContainsAndTargetGroupContains(type: String, targetGrade: String, targetGroup: String): Long
}