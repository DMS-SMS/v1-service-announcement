package dsm.service.announcement.data.adapter.repository

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class AnnouncementRepositoryImpl: AnnouncementRepository {
    override fun persist(announcement: Announcement): Announcement {
        TODO("Not yet implemented")
    }

    override fun delete(announcement: Announcement) {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): Announcement? {
        TODO("Not yet implemented")
    }

    override fun findByNumberAndType(number: Long, type: String): Announcement? {
        TODO("Not yet implemented")
    }

    override fun findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        title: String,
        type: String,
        targetGrade: String,
        targetGroup: String,
        pageable: Pageable
    ): Page<Announcement> {
        TODO("Not yet implemented")
    }

    override fun findByTitleContainsAndTypeOrderByDateDesc(
        title: String,
        type: String,
        pageable: Pageable
    ): Page<Announcement> {
        TODO("Not yet implemented")
    }

    override fun countByTitleContainsAndType(title: String, type: String): Long {
        TODO("Not yet implemented")
    }

    override fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        type: String,
        targetGrade: String,
        targetGroup: String
    ): MutableIterable<Announcement> {
        TODO("Not yet implemented")
    }

    override fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        type: String,
        targetGrade: String,
        targetGroup: String,
        pageable: Pageable
    ): Page<Announcement> {
        TODO("Not yet implemented")
    }

    override fun findByTypeOrderByDateDesc(type: String): MutableIterable<Announcement> {
        TODO("Not yet implemented")
    }

    override fun findByTypeOrderByDateDesc(type: String, pageable: Pageable): Page<Announcement> {
        TODO("Not yet implemented")
    }

    override fun findByWriterUuidAndTypeOrderByDateDesc(
        writerUuid: String,
        type: String,
        pageable: Pageable
    ): Page<Announcement> {
        TODO("Not yet implemented")
    }

    override fun findTopByOrderByNumberAsc(): Announcement? {
        TODO("Not yet implemented")
    }

    override fun findTopByOrderByNumberDesc(): Announcement? {
        TODO("Not yet implemented")
    }

    override fun countByType(type: String): Long {
        TODO("Not yet implemented")
    }

    override fun countByTypeAndTargetGradeContainsAndTargetGroupContains(
        type: String,
        targetGrade: String,
        targetGroup: String
    ): Long {
        TODO("Not yet implemented")
    }

    override fun countByWriterUuidAndType(writerUuid: String, type: String): Long {
        TODO("Not yet implemented")
    }
}