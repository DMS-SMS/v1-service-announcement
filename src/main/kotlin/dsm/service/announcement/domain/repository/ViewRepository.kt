package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Content
import dsm.service.announcement.domain.entity.View
import org.springframework.data.mongodb.repository.MongoRepository

interface ViewRepository: MongoRepository<View, String> {
    fun findByUuid(uuid: String): View?
}