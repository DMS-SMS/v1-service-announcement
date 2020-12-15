package dsm.service.announcement.domain.usecase

import java.time.LocalDateTime

interface CreateAnnouncementUseCase {
    fun execute(
            writerUuid: String,
            title: String,
            content: String,
            targetGrade: Int,
            targetGroup: Int,
            type: String): String
}