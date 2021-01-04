package dsm.service.announcement.v1.domain.usecase

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