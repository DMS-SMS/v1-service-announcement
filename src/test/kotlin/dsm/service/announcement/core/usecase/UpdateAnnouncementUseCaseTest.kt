package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.core.usecase.announcement.UpdateAnnouncementUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.AdditionalAnswers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime

class UpdateAnnouncementUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var updateAnnouncementUseCase: UpdateAnnouncementUseCase
    @Mock private lateinit var announcementRepository: AnnouncementRepository
    @Mock private lateinit var getAccountUseCase: GetAccountUseCase

    @Test
    fun testUpdateSchoolAnnouncementByTeacher() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )

        given(announcementRepository.persist(any())).willAnswer(AdditionalAnswers.returnsFirstArg<Any>())
        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 1,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "Announcement",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
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

        updateAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement.title, "Changed Announcement")
            assertEquals(announcement.content, "{'content': 'mock'}")
            assertEquals(announcement.writerUuid, "teacher-111122223333")
            assertEquals(announcement.type, "school")
            assertEquals(announcement.club, null)
        }
    }

    @Test
    fun testUpdateSchoolAnnouncementByStudent() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "student-111122223333",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )

        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 1,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "Announcement",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        ))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        assertThrows(UnAuthorizedException::class.java) { updateAnnouncementUseCase.execute(input) }
    }

    @Test
    fun testUpdateSchoolAnnouncementByAdmin() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "admin-111122223333",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )

        given(announcementRepository.persist(any())).willAnswer(AdditionalAnswers.returnsFirstArg<Any>())
        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 1,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "Announcement",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        ))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))

        updateAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement.title, "Changed Announcement")
            assertEquals(announcement.content, "{'content': 'mock'}")
            assertEquals(announcement.writerUuid, "teacher-111122223333")
            assertEquals(announcement.type, "school")
            assertEquals(announcement.club, null)
        }
    }

    @Test
    fun testUpdateClubAnnouncementByClubLeader() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "student-111122223333",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )

        given(announcementRepository.persist(any())).willAnswer(AdditionalAnswers.returnsFirstArg<Any>())
        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "student-111122223333",
                number = 1,
                writerName= "학생",
                date = LocalDateTime.now(),
                title = "Announcement",
                targetGrade = "1",
                targetClass = "1",
                type = "club",
                club = "DMS",
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        ))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        updateAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement.title, "Changed Announcement")
            assertEquals(announcement.content, "{'content': 'mock'}")
            assertEquals(announcement.writerUuid, "student-111122223333")
            assertEquals(announcement.type, "club")
            assertEquals(announcement.club, "DMS")
        }
    }

    @Test
    fun testUpdateClubAnnouncementByTeacher() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )

        given(announcementRepository.persist(any())).willAnswer(AdditionalAnswers.returnsFirstArg<Any>())
        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 1,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "Announcement",
                targetGrade = "1",
                targetClass = "1",
                type = "club",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
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

        updateAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement.title, "Changed Announcement")
            assertEquals(announcement.content, "{'content': 'mock'}")
            assertEquals(announcement.writerUuid, "teacher-111122223333")
            assertEquals(announcement.type, "club")
            assertEquals(announcement.club, null)
        }
    }

    @Test
    fun testUpdateClubAnnouncementByAdmin() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "admin-111122223333",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )

        given(announcementRepository.persist(any())).willAnswer(AdditionalAnswers.returnsFirstArg<Any>())
        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 1,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "Announcement",
                targetGrade = "1",
                targetClass = "1",
                type = "club",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        ))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))

        updateAnnouncementUseCase.execute(input).apply {
            assertEquals(announcement.title, "Changed Announcement")
            assertEquals(announcement.content, "{'content': 'mock'}")
            assertEquals(announcement.writerUuid, "teacher-111122223333")
            assertEquals(announcement.type, "club")
            assertEquals(announcement.club, null)
        }
    }

    @Test
    fun testUpdateClubAnnouncementByStudentNotClubLeader() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "student-111122223334",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )

        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "student-111122223333",
                number = 1,
                writerName= "학생",
                date = LocalDateTime.now(),
                title = "Announcement",
                targetGrade = "1",
                targetClass = "1",
                type = "club",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        ))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        assertThrows(UnAuthorizedException::class.java) { updateAnnouncementUseCase.execute(input) }
    }

    @Test
    fun testUpdateClubAnnouncementByStudent() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "student-111122223333",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )

        given(announcementRepository.findById(anyString())).willReturn(Announcement(
                uuid = "announcement-111122223333",
                writerUuid = "teacher-111122223333",
                number = 1,
                writerName= "선생님",
                date = LocalDateTime.now(),
                title = "Announcement",
                targetGrade = "1",
                targetClass = "1",
                type = "club",
                club = null,
                readAccounts = arrayListOf(),
                content = "{'content': 'origin'}"
        ))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        assertThrows(UnAuthorizedException::class.java) { updateAnnouncementUseCase.execute(input) }
    }

    @Test
    fun testUpdateNotExistAnnouncement() {
        val input = UpdateAnnouncementUseCase.InputValues(
                writerUuid = "student-111122223333",
                title = "Changed Announcement",
                content = "{'content': 'mock'}",
                targetGrade = "1",
                targetGroup = "1",
                announcementId = "announcement-111122223333"
        )
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))

        assertThrows(NotFoundException::class.java) { updateAnnouncementUseCase.execute(input) }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}