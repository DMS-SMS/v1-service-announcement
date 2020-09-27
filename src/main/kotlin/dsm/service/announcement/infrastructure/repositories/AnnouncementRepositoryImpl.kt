package dsm.service.announcement.infrastructure.repositories

import com.mongodb.BasicDBObject
import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.exceptions.AuthorityFailedException
import dsm.service.announcement.domain.exceptions.NotFoundException
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
    override fun findByUuid(announcementUuid: String): Announcement? {
        val query: TypedQuery<Announcement> = entityManager.createQuery(
            "SELECT a FROM Announcement a where a.uuid = :uuid",
            Announcement::class.java
        )

        var announcement: Announcement?

        try {
            announcement = query.setParameter("uuid", "$announcementUuid").singleResult
        } catch (e: Exception) {
            throw NotFoundException()
        }
        return announcement
    }

    override fun findContentByUuid(contentUuid: String): Document? {
        val obj = BasicDBObject()
        obj.put("uuid", contentUuid)

        return mongoManager.collection.find(obj).first()
    }

    override fun save(announcement: Announcement) {
        transaction.begin()
        entityManager.persist(announcement)
        transaction.commit()
    }

    override fun saveContent(contentUuid: String, content: String): String {
        val document: Document = Document.parse("{'uuid':'$contentUuid','content':$content}")
        mongoManager.collection.insertOne(document)
        return contentUuid
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

    override fun updateAnnouncement(
        announcementUuid: String,
        title: String,
        targetGrade: Int,
        targetClass: Int
    ): String? {
        val announcement = findByUuid(announcementUuid)
        if (announcement != null) {
            transaction.begin()

            announcement.title = title
            announcement.targetGrade = targetGrade
            announcement.targetClass = targetClass

            transaction.commit()
            return announcement.contentUuid
        }

        return null
    }

    override fun updateContent(contentUuid: String, content: String) {
        val filter = BasicDBObject()
        filter.put("uuid", contentUuid)

        val contentObject= BasicDBObject()
        contentObject.put("content", BasicDBObject.parse(content))

        val document = BasicDBObject()
        document.put("\$set", contentObject)

        mongoManager.collection.updateOne(filter, document)
    }

    override fun deleteAnnouncement(announcementUuid: String): String? {
        val announcement: Announcement? = findByUuid(announcementUuid)
        transaction.begin()
        entityManager.remove(announcement)
        transaction.commit()
        if (announcement != null) {
            return announcement.contentUuid
        }
        return null
    }

    override fun deleteContent(contentUuid: String?) {
        val filter = BasicDBObject()
        filter.put("uuid", contentUuid)

        mongoManager.collection.deleteOne(filter)
    }
}

