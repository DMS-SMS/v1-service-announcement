package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.ServerException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.domain.repository.ClubRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import kotlin.streams.asSequence

@Component
class CreateAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val clubRepository: ClubRepository
): UseCase<CreateAnnouncementUseCase.InputValues, CreateAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues {
        val announcementUuid = announcementRepository.persist(createAnnouncement(input)).uuid?:
                throw ServerException(message = "uuid isn't generated")
        return OutputValues(announcementUuid)
    }

    private fun createAnnouncement(input: InputValues): Announcement {
        return generateAnnouncementUuid(
                Announcement(
                    uuid = null,
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
        )
    }

    private fun generateAnnouncementUuid(announcement: Announcement): Announcement {
        while(true) {
            val generatedAnnouncement = announcement.generateUuid()
            val announcementUuid = generatedAnnouncement.uuid ?:
            throw ServerException(message = "uuid isn't generated")
            announcementRepository.findById(announcementUuid)?: return generatedAnnouncement
        }
    }

    private fun getClubName(input: InputValues): String? {
        return if (input.type != "school") {
            clubRepository.findClubUuidByLeaderUuid(input.writerUuid)
                    ?.let { clubRepository.findByUuid(it, input.writerUuid)?.name }
        }
        else { null }
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