package dsm.service.announcement.data.adapter.repository

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.data.adapter.repository.mapper.AnnouncementRepositoryMapper
import dsm.service.announcement.data.db.jpa.repository.JpaAnnouncementDetailRepository
import dsm.service.announcement.data.db.jpa.repository.JpaAnnouncementRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class AnnouncementRepositoryImpl(
    val announcementRepositoryMapper: AnnouncementRepositoryMapper,
    val jpaAnnouncementRepository: JpaAnnouncementRepository,
    val jpaAnnouncementDetailRepository: JpaAnnouncementDetailRepository
): AnnouncementRepository {
    override fun persist(announcement: Announcement): Announcement {
        val announcementModels = announcementRepositoryMapper.map(announcement)
        jpaAnnouncementRepository.save(announcementModels.announcementModel)
        jpaAnnouncementDetailRepository.save(announcementModels.announcementDetailModel)
        return announcement
    }

    override fun delete(announcement: Announcement) {
        val announcementModels = announcementRepositoryMapper.map(announcement)
        jpaAnnouncementRepository.delete(announcementModels.announcementModel)
        jpaAnnouncementDetailRepository.deleteByUuid(announcementModels.announcementDetailModel.uuid)
    }

    override fun findById(id: String): Announcement? {
        return announcementRepositoryMapper.map(jpaAnnouncementRepository.findByUuid(id),
            jpaAnnouncementDetailRepository.findByUuid(id))
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