package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.BadRequestException
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
    override fun execute(input: InputValues): OutputValues = OutputValues(getSearchAnnouncements(input))

    private fun getSearchAnnouncements(input: InputValues): Pair<MutableIterable<Announcement>, Long>? {
        return when (input.type) {
            "club" -> {
                Pair(announcementRepository
                        .findByTitleContainsAndTypeOrderByDateDesc(
                                title = input.query,
                                type = input.type,
                                pageable = PageRequest.of(input.start, input.count)),
                        announcementRepository.countByTitleContainsAndType(
                                title = input.query,
                                type = input.type
                        ))
            }
            "school" -> {
                accountRepository.findByUuid(input.writerUuid, input.writerUuid)
                        ?.let {
                            Pair(announcementRepository
                                    .findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                                            title = input.query,
                                            type = "school",
                                            targetGrade = it.grade.toString(),
                                            targetGroup = it.group.toString(),
                                            pageable = PageRequest.of(input.start, input.count)),
                                    announcementRepository.countByTitleContainsAndType(
                                            title = input.query,
                                            type = input.type))
                        }
            }
            else -> throw BadRequestException()
        }
    }

    class InputValues(
            val writerUuid: String,
            val query: String,
            val start: Int,
            val count: Int,
            val type: String
    ) : UseCase.InputValues

    class OutputValues(
            val pair: Pair<MutableIterable<Announcement>, Long>?
    ) : UseCase.OutputValues
}