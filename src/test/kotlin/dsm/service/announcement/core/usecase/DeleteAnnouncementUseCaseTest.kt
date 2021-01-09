package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.DeleteAnnouncementUseCase
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime

class DeleteAnnouncementUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var deleteAnnouncementUseCase: DeleteAnnouncementUseCase
    @Mock private lateinit var announcementRepository: AnnouncementRepository

    @Test fun testMyAnnouncement() {
        val input = DeleteAnnouncementUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                announcementUuid = "announcement-111122223333"
        )

        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                number = 1,
                writerUuid = "teacher-111122223333",
                writerName = "김선생",
                date = LocalDateTime.now(),
                title = "공지",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                content = "{'content':'contents}",
                readAccounts = arrayListOf("student-111122223334"),
                isCheck = false
        ))

        val output = deleteAnnouncementUseCase.execute(input)

        assertEquals(output.announcement.uuid, "announcement-111122223333")
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}