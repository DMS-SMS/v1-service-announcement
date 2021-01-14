package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.core.usecase.announcement.GetPreviousAnnouncementUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime

class GetPreviousAnnouncementUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var getPreviousAnnouncementUseCase: GetPreviousAnnouncementUseCase
    @Mock private lateinit var announcementRepository: AnnouncementRepository
    @Mock private lateinit var getAccountUseCase: GetAccountUseCase

    @Test
    fun testGetPreviousSchoolAnnouncementUseCase() {
        val mainAnnouncement = Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 2,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "Announcement2",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        )
        val previousAnnouncement = Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 1,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "Announcement1",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        )

        val input = GetPreviousAnnouncementUseCase.InputValues(
                accountUuid = "student-111122223333",
                announcement = mainAnnouncement
        )

        given(announcementRepository.findTopByOrderByNumberAsc()).willReturn(previousAnnouncement)
        given(announcementRepository.findByNumberAndType(anyLong(), anyString())).willReturn(previousAnnouncement)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        getPreviousAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement?.title, "Announcement1")
        }
    }

    @Test
    fun testGetPreviousClubAnnouncementUseCase() {
        val input = GetPreviousAnnouncementUseCase.InputValues(
                accountUuid = "student-111122223333",
                announcement = Announcement(
                        uuid = "announcement-111122223333",
                        writerUuid = "teacher-111122223333",
                        number = 2,
                        writerName= "선생님",
                        date = LocalDateTime.now(),
                        title = "club2",
                        targetGrade = "1",
                        targetClass = "1",
                        type = "club",
                        club = null,
                        readAccounts = arrayListOf(),
                        content = "{'content': 'origin'}"
                )
        )
        val previousAnnouncement = Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 1,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "club1",
                targetGrade = "1",
                targetClass = "1",
                type = "club",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        )

        given(announcementRepository.findTopByOrderByNumberAsc()).willReturn(previousAnnouncement)
        given(announcementRepository.findByNumberAndType(anyLong(), anyString())).willReturn(previousAnnouncement)

        getPreviousAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement?.title, "club1")
        }
    }

    @Test
    fun testGetNonePreviousAnnouncementUseCase() {
        val input = GetPreviousAnnouncementUseCase.InputValues(
                accountUuid = "student-111122223333",
                announcement = Announcement(
                        uuid = "announcement-111122223333",
                        writerUuid = "teacher-111122223333",
                        number = 1,
                        writerName= "선생님",
                        date = LocalDateTime.now(),
                        title = "Announcement1",
                        targetGrade = "1",
                        targetClass = "1",
                        type = "school",
                        club = null,
                        readAccounts = arrayListOf(),
                        content = "{'content': 'origin'}"
                )
        )

        getPreviousAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement?.title, null)
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}