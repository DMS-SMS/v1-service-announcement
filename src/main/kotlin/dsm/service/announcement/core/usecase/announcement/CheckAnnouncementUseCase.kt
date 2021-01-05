package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component

@Component
class CheckAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository
): UseCase<CheckAnnouncementUseCase.InputValues, CheckAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues {
        return if (isAnnouncementCheck(input)) OutputValues(1)
        else OutputValues(0)
    }

    private fun isAnnouncementCheck(input: InputValues): Boolean {
        val announcements = announcementRepository.findByTypeOrderByDateDesc(input.type)
        for (announcement in announcements) {
            return announcement.isCheck.toInt() == 0
        }
        return announcements.count() != 0
    }

    class InputValues(
            val writerUuid: String,
            val type: String
    ): UseCase.InputValues

    class OutputValues(
            val isCheck: Long
    ): UseCase.OutputValues
}