package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.ServerException
import dsm.service.announcement.core.domain.repository.AccountRepository
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val accountRepository: AccountRepository
): UseCase<GetAnnouncementsUseCase.InputValues, GetAnnouncementsUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues = getAnnouncements(input)

    private fun getAnnouncements(input: InputValues): OutputValues {
        return if (input.type == "club") {
            defaultOutputValue(input)
        }
        else {
            accountRepository.findByUuid(input.writerUuid, input.writerUuid)
                    ?.let { account ->
                        if (account.grade == 0) defaultOutputValue(input)
                        else outputValue(input, account) }
                    ?: throw ServerException(message = "Announcement number isn't exists.")
        }
    }

    fun defaultOutputValue(input: InputValues): OutputValues {
        return OutputValues(announcementRepository
                .findByTypeOrderByDateDesc(
                        type = input.type,
                        pageable = PageRequest.of(input.start, input.count)),
                announcementRepository.countByType(input.type))
    }

    fun outputValue(input: InputValues, account: Account): OutputValues {
        return OutputValues(
                announcementRepository
                        .findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                                type = "school",
                                targetGrade = account.grade.toString(),
                                targetGroup = account.group.toString(),
                                pageable = PageRequest.of(input.start, input.count)),
                announcementRepository
                        .countByTypeAndTargetGradeContainsAndTargetGroupContains(
                                type = "school",
                                targetGrade = account.grade.toString(),
                                targetGroup = account.group.toString()
                        ))
    }

    class InputValues(
            val writerUuid: String,
            val start: Int,
            val count: Int,
            val type: String
    ) : UseCase.InputValues

    class OutputValues(
            val announcements: MutableIterable<Announcement>,
            val count: Long
    ) : UseCase.OutputValues
}