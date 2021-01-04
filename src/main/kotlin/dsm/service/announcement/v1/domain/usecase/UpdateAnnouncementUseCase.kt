package dsm.service.announcement.v1.domain.usecase

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