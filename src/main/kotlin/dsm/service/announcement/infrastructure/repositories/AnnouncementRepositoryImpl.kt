package dsm.service.announcement.infrastructure.repositories

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.infrastructure.mysql.MySqlEntityManager
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

class AnnouncementRepositoryImpl(
    val entityManager: EntityManager = MySqlEntityManager.em
): AnnouncementRepository {
    override fun findByType(type: String): Announcement {
        val getAnnouncementQuery: TypedQuery<Announcement> = this.entityManager.createQuery(
            "SELECT a FROM Announcement a WHERE a.type=:type",Announcement::class.java)

        val announcement: Announcement = getAnnouncementQuery.
            setParameter("type", type).getSingleResult();

        return announcement
    }
}

