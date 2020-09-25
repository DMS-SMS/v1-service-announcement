package dsm.service.announcement.infrastructure.repositories

import com.mongodb.BasicDBObject
import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.infrastructure.mongo.MongoManager
import dsm.service.announcement.infrastructure.mysql.MySqlEntityManager
import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.TypedQuery
import org.bson.Document
import org.bson.conversions.Bson
import org.json.JSONObject

class AnnouncementRepositoryImpl(
    val entityManager: EntityManager = MySqlEntityManager.em,
    val transaction: EntityTransaction = MySqlEntityManager.tx,
    val mongoManager: MongoManager = MongoManager
): AnnouncementRepository {
    override fun findByUuid(uuid: String): Announcement? {
        val query: TypedQuery<Announcement> = entityManager.createQuery(
            "SELECT a FROM Announcement a where a.uuid = :uuid",
            Announcement::class.java
        )

        var announcement: Announcement?

        try {
            announcement = query.setParameter("uuid", "$uuid").singleResult
        } catch (e: Exception) {
            announcement = null
        }
        return announcement
    }

    override fun findContentByUuid(uuid: String): Document? {
        val obj = BasicDBObject()
        obj.put("uuid", uuid)

        return mongoManager.collection.find(obj).first()
    }

    override fun save(announcement: Announcement) {
        transaction.begin()
        entityManager.persist(announcement)
        transaction.commit()
    }

    override fun saveContent(content: String, key: String): String {
        val document: Document = Document.parse("{'uuid':'$key','content':$content}")
        mongoManager.collection.insertOne(document)
        return key
    }

    override fun findClubAnnouncements(): List<Announcement?> {
        val query: TypedQuery<Announcement> = entityManager.createQuery(
            "SELECT a FROM Announcement a where a.type = :type",
            Announcement::class.java
        )

        val announcement: List<Announcement?> = query.
            setParameter("type", "club").resultList;

        return announcement
    }

    override fun findSchoolAnnouncements(uuid: String): List<Announcement?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

