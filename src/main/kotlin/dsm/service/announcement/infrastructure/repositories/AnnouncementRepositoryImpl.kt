package dsm.service.announcement.infrastructure.repositories

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.infrastructure.mongo.MongoManager
import dsm.service.announcement.infrastructure.mysql.MySqlEntityManager
import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.TypedQuery
import org.bson.Document
import org.json.JSONObject

class AnnouncementRepositoryImpl(
    val entityManager: EntityManager = MySqlEntityManager.em,
    val transaction: EntityTransaction = MySqlEntityManager.tx,
    val mongoManager: MongoManager = MongoManager
): AnnouncementRepository {
    override fun findByType(type: String): Announcement? {
        val getAnnouncementQuery: TypedQuery<Announcement> = this.entityManager.createQuery(
            "SELECT a FROM Announcement a WHERE a.type=:type",Announcement::class.java)

        val announcement: Announcement = getAnnouncementQuery.
            setParameter("type", type).getSingleResult();

        return announcement
    }

    override fun findByUuid(uuid: String): Announcement? {
        return entityManager.find(Announcement::class.java, uuid)
    }

    override fun save(announcement: Announcement) {
        transaction.begin()
        entityManager.persist(announcement)
        transaction.commit()
    }

    override fun saveContent(content: String, key: String): String {
        val content: Document = Document.parse("{'uuid':'$key','content':$content}")
        mongoManager.collection.insertOne(content)
        return key
    }
}

