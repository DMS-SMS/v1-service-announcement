package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import org.json.JSONObject
import java.util.*
import kotlin.streams.asSequence

class CreateAnnouncementUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): CreateAnnouncementUseCase {
    override fun createAnnouncementUseCase(announcement: Announcement) {
        announcement.uuid = createAnnouncementUuidUseCase()
        announcementRepository.save(announcement)
    }

    override fun createContent(content: String): String {
        return announcementRepository
            .saveContent(content, createContentUuidUseCase())
    }

    override fun createAnnouncementUuidUseCase(): String {
        while (true) {
            val key = generateRandomKey(12)
            if (announcementRepository.findByUuid(key) == null )
                return "Announcement-$key"
        }
    }

    override fun createContentUuidUseCase(): String {
        while (true) {
            val key = generateRandomKey(12)
            if (announcementRepository.findContentByUuid(key) == null )
                return key
        }
    }

    fun generateRandomKey(length: Long): String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWZYZabcdefghijklmnopqrstuvwxtz123457890"
        return Random().ints(length, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
    }
}