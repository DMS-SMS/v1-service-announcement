package dsm.service.announcement.infrastructure.mysql

import dsm.service.announcement.domain.entities.Announcement
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

import org.hibernate.cfg.Configuration

object MySqlEntityManager {
    val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("dsm.service.announcement")
    val em: EntityManager = emf.createEntityManager()

}