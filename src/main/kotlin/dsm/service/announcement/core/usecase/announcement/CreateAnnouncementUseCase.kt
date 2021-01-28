package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.exception.ServerException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
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
        private val clubRepository: ClubRepository,

        private val getAccountUseCase: GetAccountUseCase
): UseCase<CreateAnnouncementUseCase.InputValues, CreateAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(announcementRepository.persist(createAnnouncement(input)))

    private fun createAnnouncement(input: InputValues): Announcement {
        checkAccount(input)
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
                content = input.content
        )
    }

    private fun checkAccount(input: InputValues) {
        val account = getAccountUseCase.execute(GetAccountUseCase.InputValues(input.writerUuid)).account?:
                throw UnAuthorizedException()
        if (account.type == AccountType.STUDENT && input.type != "club") throw UnAuthorizedException()
        if (account.type == AccountType.STUDENT &&
                clubRepository.findClubUuidByLeaderUuid(input.writerUuid) == null) throw UnAuthorizedException()
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
        if (input.type == "club") {
            clubRepository.findClubUuidByLeaderUuid(input.writerUuid)?.let {
                return clubRepository.findByUuid(it, input.writerUuid)?.name
            }
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
            val announcement: Announcement
    ): UseCase.OutputValues

}