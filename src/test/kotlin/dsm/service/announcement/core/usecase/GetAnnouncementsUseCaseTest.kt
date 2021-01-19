package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.core.usecase.announcement.GetAnnouncementsUseCase
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime

class GetAnnouncementsUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var getAnnouncementsUseCase: GetAnnouncementsUseCase
    @Mock private lateinit var announcementRepository: AnnouncementRepository
    @Mock private lateinit var getAccountUseCase: GetAccountUseCase

    @Test
    fun testGetSchoolAnnouncementsByStudent() {
        val input = GetAnnouncementsUseCase.InputValues(
                writerUuid = "student-111122223333",
                start = 0,
                count = 10,
                type = "school"
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
                                uuid = "announcement-111122223334",
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
        `when`(announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(anyString(), anyString(), anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByTypeAndTargetGradeContainsAndTargetGroupContains(anyString(), anyString(), anyString())).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        getAnnouncementsUseCase.execute(input).apply {
            assertEquals(announcements.first().title, "Announcement1")
            assertEquals(announcements.first().writerUuid, "teacher-111122223333")
            assertEquals(announcements.first().type, "school")
            assertEquals(count, pageResponse.totalElements)
        }
    }

    @Test
    fun testGetSchoolAnnouncementsByTeacher() {
        val input = GetAnnouncementsUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                start = 0,
                count = 10,
                type = "school"
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
                                uuid = "announcement-111122223334",
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
        `when`(announcementRepository.findByTypeOrderByDateDesc(anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByType(anyString())).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "선생님",
                        phoneNumber = "01011112222",
                        type = AccountType.TEACHER
                )
        ))

        getAnnouncementsUseCase.execute(input).apply {
            assertEquals(announcements.first().title, "Announcement1")
            assertEquals(announcements.first().writerUuid, "teacher-111122223333")
            assertEquals(announcements.first().type, "school")
            assertEquals(count, pageResponse.totalElements)
        }
    }

    @Test
    fun testGetSchoolAnnouncementsByAdmin() {
        val input = GetAnnouncementsUseCase.InputValues(
                writerUuid = "admin-111122223333",
                start = 0,
                count = 10,
                type = "school"
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
                                uuid = "announcement-111122223334",
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
        `when`(announcementRepository.findByTypeOrderByDateDesc(anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByType(anyString())).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))

        getAnnouncementsUseCase.execute(input).apply {
            assertEquals(announcements.first().title, "Announcement1")
            assertEquals(announcements.first().writerUuid, "teacher-111122223333")
            assertEquals(announcements.first().type, "school")
            assertEquals(count, pageResponse.totalElements)
        }
    }

    @Test
    fun testGetClubAnnouncements() {
        val input = GetAnnouncementsUseCase.InputValues(
                writerUuid = "student-111122223333",
                start = 0,
                count = 10,
                type = "club"
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
                                type = "club",
                                club = "DMS",
                                readAccounts = arrayListOf(),
                                content = "{'content': 'origin'}"
                        ),
                        Announcement(
                                uuid = "announcement-111122223334",
                                writerUuid = "teacher-111122223333",
                                number = 2,
                                writerName= "선생님",
                                date = LocalDateTime.now(),
                                title = "Announcement2",
                                targetGrade = "1",
                                targetClass = "1",
                                type = "club",
                                club = "Nonamed",
                                readAccounts = arrayListOf(),
                                content = "{'content': 'origin'}"
                        )
                )
        val pageResponse: Page<Announcement> = PageImpl(announcementList)
        `when`(announcementRepository.findByTypeOrderByDateDesc(anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByType(anyString())).willReturn(pageResponse.totalElements)

        getAnnouncementsUseCase.execute(input).apply {
            assertEquals(announcements.first().title, "Announcement1")
            assertEquals(announcements.first().writerUuid, "teacher-111122223333")
            assertEquals(announcements.first().type, "club")
            assertEquals(count, pageResponse.totalElements)
        }
    }

    @Test
    fun testGetNoneSchoolAnnouncements() {
        val input = GetAnnouncementsUseCase.InputValues(
                writerUuid = "admin-111122223333",
                start = 0,
                count = 10,
                type = "school"
        )

        val pageResponse: Page<Announcement> = PageImpl(arrayListOf())
        `when`(announcementRepository.findByTypeOrderByDateDesc("school", PageRequest.of(0, 10))).thenReturn(pageResponse)
        given(announcementRepository.countByType("school")).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))

        getAnnouncementsUseCase.execute(input).apply {
            assertEquals(count, 0)
        }
    }

    @Test
    fun testGetNoneClubAnnouncements() {
        val input = GetAnnouncementsUseCase.InputValues(
                writerUuid = "admin-111122223333",
                start = 0,
                count = 10,
                type = "club"
        )

        val pageResponse: Page<Announcement> = PageImpl(arrayListOf())
        `when`(announcementRepository.findByTypeOrderByDateDesc("club", PageRequest.of(0, 10))).thenReturn(pageResponse)
        given(announcementRepository.countByType("club")).willReturn(pageResponse.totalElements)

        getAnnouncementsUseCase.execute(input).apply {
            assertEquals(count, 0)
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}