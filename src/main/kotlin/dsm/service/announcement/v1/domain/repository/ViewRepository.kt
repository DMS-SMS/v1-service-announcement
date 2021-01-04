package dsm.service.announcement.v1.domain.repository

import dsm.service.announcement.v1.domain.entity.Content
import dsm.service.announcement.v1.domain.entity.View
import org.springframework.data.mongodb.repository.MongoRepository

interface ViewRepository: MongoRepository<View, String> {
    fun findByUuid(uuid: String): View?
}