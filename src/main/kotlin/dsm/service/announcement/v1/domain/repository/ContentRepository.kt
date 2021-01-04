package dsm.service.announcement.v1.domain.repository

import dsm.service.announcement.v1.domain.entity.Content
import org.springframework.data.mongodb.repository.MongoRepository

interface ContentRepository: MongoRepository<Content, String> {
    fun findByUuid(uuid: String): Content?

    fun deleteByUuid(uuid: String)
}