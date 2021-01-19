package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.core.usecase.announcement.GetMyAnnouncementsUseCase
import org.junit.Test
import org.mockito.*
import org.mockito.ArgumentMatchers.anyString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.`when`
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDateTime

class GetMyAnnouncementsUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var getMyAnnouncementsUseCase: GetMyAnnouncementsUseCase
    @Mock private lateinit var announcementRepository: AnnouncementRepository
    @Mock private lateinit var getAccountUseCase: GetAccountUseCase

    @Test
    fun testGetMyAnnouncementsByTeacher() {
        val input = GetMyAnnouncementsUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                start = 0,
                count = 10
        )

        val announcementList: List<Announcement> =
                arrayListOf(
                        Announcement(
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
                        ),
                        Announcement(
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
                )
        val pageResponse: Page<Announcement> = PageImpl(announcementList)
        `when`(announcementRepository.findByWriterUuidAndTypeOrderByDateDesc(anyString(), anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByWriterUuidAndType(anyString(), anyString())).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "선생님",
                        phoneNumber = "01011112222",
                        type = AccountType.TEACHER
                )
        ))
        getMyAnnouncementsUseCase.execute(input).apply {
            assertEquals(announcements.first().title, "Announcement1")
            assertEquals(announcements.first().writerUuid, "teacher-111122223333")
            assertEquals(count, pageResponse.totalElements)
        }
    }

    @Test
    fun testGetMyAnnouncementsByStudent() {
        val input = GetMyAnnouncementsUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                start = 0,
                count = 10
        )
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))
        assertThrows(UnAuthorizedException::class.java) { getMyAnnouncementsUseCase.execute(input) }
    }

    @Test
    fun testGetMyAnnouncementsByAdmin() {
        val input = GetMyAnnouncementsUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                start = 0,
                count = 10
        )

        val announcementList: List<Announcement> =
                arrayListOf(
                        Announcement(
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
                        ),
                        Announcement(
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
                )
        val pageResponse: Page<Announcement> = PageImpl(announcementList)
        `when`(announcementRepository.findByWriterUuidAndTypeOrderByDateDesc(anyString(), anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByWriterUuidAndType(anyString(), anyString())).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))
        getMyAnnouncementsUseCase.execute(input).apply {
            assertEquals(announcements.first().title, "Announcement1")
            assertEquals(announcements.first().writerUuid, "teacher-111122223333")
            assertEquals(count, pageResponse.totalElements)
        }
    }

    @Test
    fun testGetNoneMyAnnouncements() {
        val input = GetMyAnnouncementsUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                start = 0,
                count = 10
        )
        val pageResponse: Page<Announcement> = PageImpl(arrayListOf())
        `when`(announcementRepository.findByWriterUuidAndTypeOrderByDateDesc(anyString(), anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByWriterUuidAndType(anyString(), anyString())).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "선생님",
                        phoneNumber = "01011112222",
                        type = AccountType.TEACHER
                )
        ))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "선생님",
                        phoneNumber = "01011112222",
                        type = AccountType.TEACHER
                )
        ))
        getMyAnnouncementsUseCase.execute(input).apply {
            assertEquals(count, pageResponse.totalElements)
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}