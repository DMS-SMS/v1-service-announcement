package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.Club
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.domain.repository.ClubRepository
import dsm.service.announcement.core.usecase.UseCase
import java.time.LocalDateTime
import java.util.*
import kotlin.streams.asSequence

class CreateAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val clubRepository: ClubRepository
): UseCase<CreateAnnouncementUseCase.InputValues, CreateAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues {
        val announcement = createAnnouncement(input)

        return OutputValues(announcementRepository.persist(announcement).uuid)
    }

    private fun createAnnouncement(input: InputValues): Announcement {
        return Announcement(
                uuid = createAnnouncementUuid(),
                number = null,
                writerUuid = input.writerUuid,
                date = LocalDateTime.now(),
                title = input.title,
                targetGrade = input.targetGrade,
                targetClass = input.targetGroup,
                type = input.type,
                club = getClubName(input),
                content = input.content,
                readAccounts = emptyList()
        )
    }

    private fun createAnnouncementUuid(): String {
        while(true) {
            val key = generateRandomKey()
            val announcementUuid = "announcement-$key"
            announcementRepository.findById(announcementUuid)?: return announcementUuid
        }
    }

    private fun generateRandomKey(): String {
        val source = "1234567890"
        return Random().ints(12, 0, source.length)
                .asSequence()
                .map(source::get)
                .joinToString("")
    }

    private fun getClubName(input: InputValues): String? {
        if (input.type == "school") return null
        clubRepository.findClubUuidByLeaderUuid(input.writerUuid)?.let {
            return clubRepository.findByUuid(it, input.writerUuid)?.name
        }
        return null
    }

    class InputValues(
            val writerUuid: String,
            val title: String,
            val content: String,
            val targetGrade: String,
            val targetGroup: String,
            val type: String
    ): UseCase.InputValues

    class OutputValues(
            val announcementUuid: String
    ): UseCase.OutputValues

}