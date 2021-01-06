package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.BadRequestException
import dsm.service.announcement.core.domain.exception.ServerException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val getAccountUseCase: GetAccountUseCase
): UseCase<GetAnnouncementsUseCase.InputValues, GetAnnouncementsUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues = generateAnnouncements(input)

    private fun generateAnnouncements(input: InputValues): OutputValues {
        return when(input.type) {
            "club" -> generateDefaultOutputValue(input)
            "school" -> generateSchoolOutputValue(input)
            else -> throw BadRequestException(message = "Type isn't matched") }
    }

    private fun generateSchoolOutputValue(input: InputValues): OutputValues {
        return getAccountUseCase.execute(GetAccountUseCase.InputValues(input.writerUuid)).account
                ?.let { account ->
                    if (account.grade == 0) generateDefaultOutputValue(input)
                    else generateSchoolOutputValueByStudent(input, account) }
                ?: throw ServerException(message = "Announcement number isn't exists.")
    }

    private fun generateDefaultOutputValue(input: InputValues): OutputValues {
        return OutputValues(
                announcementRepository
                        .findByTypeOrderByDateDesc(
                                type = input.type,
                                pageable = PageRequest.of(input.start, input.count)),
                announcementRepository.countByType(input.type))
    }

    private fun generateSchoolOutputValueByStudent(input: InputValues, account: Account): OutputValues {
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
                                targetGroup = account.group.toString()))
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