package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.BadRequestException
import dsm.service.announcement.core.domain.exception.ServerException
import dsm.service.announcement.core.domain.repository.AccountRepository
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class SearchAnnouncementsUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val accountRepository: AccountRepository
) : UseCase<SearchAnnouncementsUseCase.InputValues, SearchAnnouncementsUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues = generateSearchAnnouncements(input)

    private fun generateSearchAnnouncements(input: InputValues): OutputValues {
        return when (input.type) {
            "club" -> { generateClubOutputValue(input) }
            "school" -> { generateSchoolOutputValue(input) }
            else -> throw BadRequestException(message = "Type isn't matched") }
    }

    private fun generateClubOutputValue(input: InputValues): OutputValues {
        return OutputValues(
                announcements = announcementRepository
                        .findByTitleContainsAndTypeOrderByDateDesc(
                                title = input.query,
                                type = input.type,
                                pageable = PageRequest.of(input.start, input.count)),
                count = announcementRepository
                        .countByTitleContainsAndType(
                                title = input.query,
                                type = input.type))
    }

    private fun generateSchoolOutputValue(input: InputValues): OutputValues {
        return accountRepository.findByUuid(input.writerUuid, input.writerUuid)
                ?.let { OutputValues(
                announcements = announcementRepository
                        .findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                                title = input.query,
                                type = "school",
                                targetGrade = it.grade.toString(),
                                targetGroup = it.group.toString(),
                                pageable = PageRequest.of(input.start, input.count)),
                count = announcementRepository
                        .countByTitleContainsAndType(
                                title = input.query,
                                type = input.type))
                }
                ?: throw ServerException(message = "Announcement number isn't exists.")
    }

    class InputValues(
            val writerUuid: String,
            val query: String,
            val start: Int,
            val count: Int,
            val type: String
    ) : UseCase.InputValues

    class OutputValues(
            val announcements: MutableIterable<Announcement>,
            val count: Long
    ) : UseCase.OutputValues
}