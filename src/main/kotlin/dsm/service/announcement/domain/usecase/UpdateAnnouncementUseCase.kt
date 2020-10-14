package dsm.service.announcement.domain.usecase

import java.time.LocalDateTime

interface UpdateAnnouncementUseCase {
    fun run(
            writerUuid: String,
            announcementUuid: String,
            title: String,
            content: String,
            targetGrade: Int,
            targetGroup: Int)
}