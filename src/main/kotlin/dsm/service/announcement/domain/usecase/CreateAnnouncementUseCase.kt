package dsm.service.announcement.domain.usecase

import java.time.LocalDateTime

interface CreateAnnouncementUseCase {
    fun run(
            writerUuid: String,
            title: String,
            content: String,
            targetGrade: Int,
            targetGroup: Int,
            type: String,
            xRequestId: String)
}