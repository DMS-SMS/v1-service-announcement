package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.core.usecase.announcement.SearchAnnouncementsUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDateTime

class SearchAnnouncementsUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var searchAnnouncementsUseCase: SearchAnnouncementsUseCase
    @Mock private lateinit var announcementRepository: AnnouncementRepository
    @Mock private lateinit var getAccountUseCase: GetAccountUseCase

    @Test
    fun testGetSearchSchoolAnnouncements() {
        val input = SearchAnnouncementsUseCase.InputValues(
                accountUuid = "student-111122223333",
                query = "Announcement",
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
        `when`(announcementRepository.findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(anyString(), anyString(), anyString(), anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByTitleContainsAndType(anyString(), anyString())).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        searchAnnouncementsUseCase.execute(input).apply {
            Assert.assertEquals(announcements.first().title, "Announcement1")
            Assert.assertEquals(announcements.first().writerUuid, "teacher-111122223333")
            Assert.assertEquals(announcements.first().type, "school")
            Assert.assertEquals(count, pageResponse.totalElements)
        }
    }

    @Test
    fun testGetSearchClubAnnouncements() {
        val input = SearchAnnouncementsUseCase.InputValues(
                accountUuid = "student-111122223333",
                query = "Announcement",
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
        `when`(announcementRepository.findByTitleContainsAndTypeOrderByDateDesc(anyString(), anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByTitleContainsAndType(anyString(), anyString())).willReturn(pageResponse.totalElements)


        searchAnnouncementsUseCase.execute(input).apply {
            Assert.assertEquals(announcements.first().title, "Announcement1")
            Assert.assertEquals(announcements.first().writerUuid, "teacher-111122223333")
            Assert.assertEquals(announcements.first().type, "club")
            Assert.assertEquals(count, pageResponse.totalElements)
        }
    }

    @Test
    fun testGetNoneSearchSchoolAnnouncements() {
        val input = SearchAnnouncementsUseCase.InputValues(
                accountUuid = "student-111122223333",
                query = "Announcement",
                start = 0,
                count = 10,
                type = "school"
        )

        val pageResponse: Page<Announcement> = PageImpl(arrayListOf())
        `when`(announcementRepository.findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(anyString(), anyString(), anyString(), anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByTitleContainsAndType(anyString(), anyString())).willReturn(pageResponse.totalElements)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))

        searchAnnouncementsUseCase.execute(input).apply {
            Assert.assertEquals(count, 0)
        }
    }

    @Test
    fun testGetNoneSearchClubAnnouncements() {
        val input = SearchAnnouncementsUseCase.InputValues(
                accountUuid = "student-111122223333",
                query = "Announcement",
                start = 0,
                count = 10,
                type = "club"
        )

        val pageResponse: Page<Announcement> = PageImpl(arrayListOf())
        `when`(announcementRepository.findByTitleContainsAndTypeOrderByDateDesc(anyString(), anyString(), any())).thenReturn(pageResponse)
        given(announcementRepository.countByTitleContainsAndType(anyString(), anyString())).willReturn(pageResponse.totalElements)

        searchAnnouncementsUseCase.execute(input).apply {
            Assert.assertEquals(count, 0)
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}