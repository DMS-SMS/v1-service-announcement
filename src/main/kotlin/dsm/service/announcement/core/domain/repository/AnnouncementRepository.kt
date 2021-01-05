package dsm.service.announcement.core.domain.repository

import dsm.service.announcement.core.domain.entity.Announcement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
interface AnnouncementRepository {
    fun persist(announcement: Announcement): Announcement

    fun delete(announcement: Announcement)

    fun findById(id: String): Announcement?

    fun findByNumberAndType(number: Long, type: String): Announcement?

    fun findTopByOrderByNumberDesc(): Announcement?

    fun findTopByOrderByNumberAsc(): Announcement?

    fun countByType(type: String): Long

    fun findByTypeOrderByDateDesc(type: String, pageable: Pageable): Page<Announcement>

    fun findByTitleContainsAndTypeOrderByDateDesc(title: String, type: String, pageable: Pageable): Page<Announcement>

    fun countByTitleContainsAndType(title: String, type: String): Long

    fun findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
            title: String, type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<Announcement>

    fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
            type: String, targetGrade: String, targetGroup: String, pageable: Pageable): Page<Announcement>

    fun countByTypeAndTargetGradeContainsAndTargetGroupContains(type: String, targetGrade: String, targetGroup: String): Long
}