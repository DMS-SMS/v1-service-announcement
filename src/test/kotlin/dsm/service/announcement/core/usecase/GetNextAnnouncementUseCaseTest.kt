package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.core.usecase.announcement.GetNextAnnouncementUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime

class GetNextAnnouncementUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var getNextAnnouncementUseCase: GetNextAnnouncementUseCase
    @Mock private lateinit var announcementRepository: AnnouncementRepository
    @Mock private lateinit var getAccountUseCase: GetAccountUseCase

    @Test
    fun testGetNextSchoolAnnouncementUseCase() {
        val mainAnnouncement = Announcement(
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
        val nextAnnouncement = Announcement(
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

        val input = GetNextAnnouncementUseCase.InputValues(
                accountUuid = "student-111122223333",
                announcement = mainAnnouncement
        )

        given(announcementRepository.findTopByOrderByNumberDesc()).willReturn(nextAnnouncement)
        given(announcementRepository.findByNumberAndType(anyLong(), anyString())).willReturn(nextAnnouncement)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        getNextAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement?.title, "Announcement2")
        }
    }

    @Test
    fun testGetNextClubAnnouncementUseCase() {
        val mainAnnouncement = Announcement(
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
                content = "{'content': 'contents'}"
        )
        val nextAnnouncement = Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 3,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "club2",
                targetGrade = "1",
                targetClass = "1",
                type = "club",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'contents'}"
        )

        val input = GetNextAnnouncementUseCase.InputValues(
                accountUuid = "student-111122223333",
                announcement = mainAnnouncement
        )

        given(announcementRepository.findTopByOrderByNumberDesc()).willReturn(nextAnnouncement)
        given(announcementRepository.findByNumberAndType(anyLong(), anyString())).willReturn(nextAnnouncement)

        getNextAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement?.title, "club2")
        }
    }

    @Test
    fun testGetNoneNextAnnouncementUseCase() {
        val mainAnnouncement = Announcement(
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

        val input = GetNextAnnouncementUseCase.InputValues(
                accountUuid = "student-111122223333",
                announcement = mainAnnouncement
        )

        given(announcementRepository.findTopByOrderByNumberDesc()).willReturn(null)

        getNextAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement?.title, null)
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}