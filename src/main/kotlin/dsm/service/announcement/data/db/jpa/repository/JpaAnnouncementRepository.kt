package dsm.service.announcement.data.db.jpa.repository

import dsm.service.announcement.data.db.jpa.model.AnnouncementModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaAnnouncementRepository: PagingAndSortingRepository<AnnouncementModel, Long> {
    fun findByUuid(uuid: String): AnnouncementModel?

    fun findByNumberAndType(number: Long, type: String): AnnouncementModel?

    fun findTopByOrderByNumberDesc(): AnnouncementModel?

    fun findTopByOrderByNumberAsc(): AnnouncementModel?

    fun findByWriterUuidAndTypeOrderByDateDesc(writerUuid: String, type: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByWriterUuidAndType(writerUuid: String, type: String): Long

    fun findByTitleContainsAndTypeOrderByDateDesc(title: String, type: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByTitleContainsAndType(title: String, type: String): Long

    fun findByTypeOrderByDateDesc(type: String): MutableIterable<AnnouncementModel>

    fun findByTypeOrderByDateDesc(type: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByType(type: String): Long

    fun findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        title: String, type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<AnnouncementModel>

    fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        type: String, targetGrade: String, targetGroup: String): MutableIterable<AnnouncementModel>

    fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<AnnouncementModel>

    fun countByTypeAndTargetGradeContainsAndTargetGroupContains(type: String, targetGrade: String, targetGroup: String): Long
}