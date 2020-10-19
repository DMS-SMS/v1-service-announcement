package dsm.service.announcement.infra.implementation.service

import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.service.UuidService
import org.springframework.stereotype.Component
import java.util.*
import kotlin.streams.asSequence


@Component
class UuidServiceImpl(
        private final val announcementRepository: AnnouncementRepository
): UuidService {
    override fun createAnnouncementUuid(): String {
        while(true) {
            val key = generateRandomKey(12)
            val uuid = "announcement-$key"
            announcementRepository.findByUuid(uuid)?: return uuid
        }
    }

    fun generateRandomKey(length: Long): String {
        val source = "1234567890"
        return Random().ints(length, 0, source.length)
                .asSequence()
                .map(source::get)
                .joinToString("")
    }
}