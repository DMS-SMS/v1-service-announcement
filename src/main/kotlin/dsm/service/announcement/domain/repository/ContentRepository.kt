package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Content
import org.springframework.data.mongodb.repository.MongoRepository

interface ContentRepository: MongoRepository<Content, String> {
    fun findByUuid(uuid: String): Content?

    fun deleteByUuid(uuid: String)
}