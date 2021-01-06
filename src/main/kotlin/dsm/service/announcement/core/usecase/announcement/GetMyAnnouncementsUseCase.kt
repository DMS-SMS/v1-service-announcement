package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GetMyAnnouncementsUseCase(
        private val announcementRepository: AnnouncementRepository
): UseCase<GetMyAnnouncementsUseCase.InputValues, GetMyAnnouncementsUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues = generateOutputValue(input)

    private fun generateOutputValue(input: InputValues): OutputValues {
        return OutputValues(
                announcementRepository
                        .findByWriterUuidAndTypeOrderByDateDesc(
                                writerUuid = input.writerUuid,
                                type = "school",
                                pageable = PageRequest.of(input.start, input.count)),
                announcementRepository.countByWriterUuidAndType(input.writerUuid, "school"))
    }

    class InputValues(
            val writerUuid: String,
            val start: Int,
            val count: Int
    ): UseCase.InputValues

    class OutputValues(
            val announcements: MutableIterable<Announcement>,
            val count: Long
    ): UseCase.OutputValues
}