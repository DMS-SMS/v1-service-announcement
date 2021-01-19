package dsm.service.announcement.data.db.jpa.repository

import dsm.service.announcement.data.db.jpa.model.AnnouncementDetailModel
import org.springframework.data.mongodb.repository.MongoRepository

interface JpaAnnouncementDetailRepository: MongoRepository<AnnouncementDetailModel, String> {
    fun findByUuid(uuid: String): AnnouncementDetailModel?

    fun deleteByUuid(uuid: String)
}