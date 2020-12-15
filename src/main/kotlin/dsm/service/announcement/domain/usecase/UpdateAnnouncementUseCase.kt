package dsm.service.announcement.domain.usecase

import java.time.LocalDateTime

interface UpdateAnnouncementUseCase {
    fun execute(
            writerUuid: String,
            announcementUuid: String,
            title: String,
            content: String,
            targetGrade: Int,
            targetGroup: Int)
}