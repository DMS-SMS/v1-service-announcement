package dsm.service.announcement.data.adapter.repository

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.ServerException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.data.adapter.repository.mapper.AnnouncementRepositoryMapper
import dsm.service.announcement.data.db.jpa.model.AnnouncementModel
import dsm.service.announcement.data.db.jpa.repository.JpaAnnouncementDetailRepository
import dsm.service.announcement.data.db.jpa.repository.JpaAnnouncementRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
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
        println("ASDF")
        val announcement = announcementRepositoryMapper.map(
            jpaAnnouncementRepository.findByUuid(id),
            jpaAnnouncementDetailRepository.findByUuid(id))
        println("HEE")
        return announcement
    }

    override fun findByNumberAndType(number: Long, type: String): Announcement? {
        val announcementModel = jpaAnnouncementRepository.findByNumberAndType(number, type)?: return null
        return announcementRepositoryMapper.map(
            announcementModel,
            jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
        )
    }

    override fun findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        title: String,
        type: String,
        targetGrade: String,
        targetGroup: String,
        pageable: Pageable
    ): MutableList<Announcement> {
        val announcementModels =
            jpaAnnouncementRepository.findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                title, type, targetGrade, targetGroup, pageable
            )

        val announcements = ArrayList<Announcement>()

        for (announcementModel: AnnouncementModel in announcementModels) {
            announcementRepositoryMapper.map(
                announcementModel,
                jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
            )?.let { announcements.add(it) }
        }

        return announcements
    }

    override fun findByTitleContainsAndTypeOrderByDateDesc(
        title: String,
        type: String,
        pageable: Pageable
    ): MutableList<Announcement> {
        val announcementModels = jpaAnnouncementRepository.findByTitleContainsAndTypeOrderByDateDesc(title, type, pageable)

        val announcements = ArrayList<Announcement>()

        for (announcementModel: AnnouncementModel in announcementModels) {
            announcementRepositoryMapper.map(
                announcementModel,
                jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
            )?.let { announcements.add(it) }
        }

        return announcements
    }

    override fun countByTitleContainsAndType(title: String, type: String): Long {
        return jpaAnnouncementRepository.countByTitleContainsAndType(title, type)
    }

    override fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        type: String,
        targetGrade: String,
        targetGroup: String
    ): MutableIterable<Announcement> {
        val announcementModels =
            jpaAnnouncementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                type, targetGrade, targetGroup
            )

        val announcements = ArrayList<Announcement>()

        for (announcementModel: AnnouncementModel in announcementModels) {
            announcementRepositoryMapper.map(
                announcementModel,
                jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
            )?.let { announcements.add(it) }
        }

        return announcements
    }

    override fun findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
        type: String,
        targetGrade: String,
        targetGroup: String,
        pageable: Pageable
    ): MutableList<Announcement> {
        val announcementModels =
            jpaAnnouncementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                type, targetGrade, targetGroup, pageable
            )

        val announcements = ArrayList<Announcement>()

        for (announcementModel: AnnouncementModel in announcementModels) {
            announcementRepositoryMapper.map(
                announcementModel,
                jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
            )?.let { announcements.add(it) }
        }

        return announcements
    }

    override fun findByTypeOrderByDateDesc(type: String): MutableIterable<Announcement> {
        val announcementModels =
            jpaAnnouncementRepository.findByTypeOrderByDateDesc(type)

        val announcements = ArrayList<Announcement>()

        for (announcementModel: AnnouncementModel in announcementModels) {
            announcementRepositoryMapper.map(
                announcementModel,
                jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
            )?.let { announcements.add(it) }
        }

        return announcements
    }

    override fun findByTypeOrderByDateDesc(type: String, pageable: Pageable): MutableIterable<Announcement> {
        val announcementModels =
            jpaAnnouncementRepository.findByTypeOrderByDateDesc(type, pageable)

        val announcements = ArrayList<Announcement>()

        for (announcementModel: AnnouncementModel in announcementModels) {
            announcementRepositoryMapper.map(
                announcementModel,
                jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
            )?.let { announcements.add(it) }
        }

        return announcements
    }

    override fun findByWriterUuidAndTypeOrderByDateDesc(
        writerUuid: String,
        type: String,
        pageable: Pageable
    ): MutableList<Announcement> {
        val announcementModels =
            jpaAnnouncementRepository.findByWriterUuidAndTypeOrderByDateDesc(writerUuid, type, pageable)

        val announcements = ArrayList<Announcement>()

        for (announcementModel: AnnouncementModel in announcementModels) {
            announcementRepositoryMapper.map(
                announcementModel,
                jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
            )?.let { announcements.add(it) }
        }

        return announcements
    }

    override fun findTopByOrderByNumberAsc(): Announcement? {
        val announcementModel = jpaAnnouncementRepository.findTopByOrderByNumberAsc()?: return null
        return announcementRepositoryMapper.map(
            announcementModel,
            jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
        )
    }

    override fun findTopByOrderByNumberDesc(): Announcement? {
        val announcementModel = jpaAnnouncementRepository.findTopByOrderByNumberDesc()?: return null
        return announcementRepositoryMapper.map(
            announcementModel,
            jpaAnnouncementDetailRepository.findByUuid(announcementModel.uuid)
        )
    }

    override fun countByType(type: String): Long {
        return jpaAnnouncementRepository.countByType(type)
    }

    override fun countByTypeAndTargetGradeContainsAndTargetGroupContains(
        type: String,
        targetGrade: String,
        targetGroup: String
    ): Long {
        return jpaAnnouncementRepository.countByTypeAndTargetGradeContainsAndTargetGroupContains(
            type, targetGrade, targetGroup
        )
    }

    override fun countByWriterUuidAndType(writerUuid: String, type: String): Long {
        return jpaAnnouncementRepository.countByWriterUuidAndType(writerUuid, type)
    }
}