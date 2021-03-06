package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.Club
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.domain.repository.ClubRepository
import dsm.service.announcement.core.usecase.announcement.CreateAnnouncementUseCase
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import org.junit.Test
import org.junit.Assert.*
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.stubbing.Answer
import java.time.LocalDateTime

class CreateAnnouncementUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var createAnnouncementUseCase: CreateAnnouncementUseCase
    @Mock private lateinit var  announcementRepository: AnnouncementRepository
    @Mock private lateinit var clubRepository: ClubRepository
    @Mock private lateinit var getAccountUseCase: GetAccountUseCase

    @Test fun testCreateSchoolAnnouncementByTeacher() {
        val input = CreateAnnouncementUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                title = "Mock Announcement",
                content = "{'content':'mock'}",
                targetGrade = "123",
                targetGroup = "1234",
                type = "school")

        given(announcementRepository.persist(any())).willAnswer(returnsFirstArg<Any>())
        given(announcementRepository.findById(anyString())).willReturn(null)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "선생님",
                        phoneNumber = "01011112222",
                        type = AccountType.TEACHER
                )
        ))

        val output: CreateAnnouncementUseCase.OutputValues = createAnnouncementUseCase.execute(input)
        assertEquals(output.announcement.targetGrade, "123")
        assertEquals(output.announcement.targetClass, "1234")
        assertEquals(output.announcement.club, null)
        assertEquals(output.announcement.content, "{'content':'mock'}")
        assertEquals(output.announcement.title, "Mock Announcement")
        assertEquals(output.announcement.type, "school")
        assertEquals(output.announcement.writerUuid, "teacher-111122223333")
    }

    @Test fun testCreateSchoolAnnouncementByStudent() {
        val input = CreateAnnouncementUseCase.InputValues(
                writerUuid = "student-111122223333",
                title = "Mock Announcement",
                content = "{'content':'mock'}",
                targetGrade = "123",
                targetGroup = "1234",
                type = "school")

        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        assertThrows(UnAuthorizedException::class.java) { createAnnouncementUseCase.execute(input) }
    }

    @Test fun testCreateSchoolAnnouncementByAdmin() {
        val input = CreateAnnouncementUseCase.InputValues(
                writerUuid = "admin-111122223333",
                title = "Mock Announcement",
                content = "{'content':'mock'}",
                targetGrade = "",
                targetGroup = "",
                type = "school")

        given(announcementRepository.persist(any())).willAnswer(returnsFirstArg<Any>())
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 0,
                        group = 0,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))
        val output: CreateAnnouncementUseCase.OutputValues = createAnnouncementUseCase.execute(input)
        assertEquals(output.announcement.club, null)
        assertEquals(output.announcement.content, "{'content':'mock'}")
        assertEquals(output.announcement.title, "Mock Announcement")
        assertEquals(output.announcement.type, "school")
        assertEquals(output.announcement.writerUuid, "admin-111122223333")
    }

    @Test fun testCreateClubAnnouncementByClubLeader() {
        val input = CreateAnnouncementUseCase.InputValues(
                writerUuid = "student-111122223333",
                title = "Mock Announcement",
                content = "{'content':'mock'}",
                targetGrade = "",
                targetGroup = "",
                type = "club")

        given(announcementRepository.persist(any())).willAnswer(returnsFirstArg<Any>())
        given(announcementRepository.findById(anyString())).willReturn(null)
        given(clubRepository.findClubUuidByLeaderUuid(anyString())).willReturn("club-111122223333")
        given(clubRepository.findByUuid(anyString(), anyString())).willReturn(Club(name = "KODOMO", clubConcept = "FREE", introduction = "자유로운동아리"))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "동아리장",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        val output: CreateAnnouncementUseCase.OutputValues = createAnnouncementUseCase.execute(input)
        assertEquals(output.announcement.club, "KODOMO")
        assertEquals(output.announcement.content, "{'content':'mock'}")
        assertEquals(output.announcement.title, "Mock Announcement")
        assertEquals(output.announcement.type, "club")
        assertEquals(output.announcement.writerUuid, "student-111122223333")
    }

    @Test fun testCreateClubAnnouncementByTeacher() {
        val input = CreateAnnouncementUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                title = "Mock Announcement",
                content = "{'content':'mock'}",
                targetGrade = "",
                targetGroup = "",
                type = "club")

        given(announcementRepository.persist(any())).willAnswer(returnsFirstArg<Any>())
        given(announcementRepository.findById(anyString())).willReturn(null)
        given(clubRepository.findClubUuidByLeaderUuid(anyString())).willReturn(null)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "선생님",
                        phoneNumber = "01011112222",
                        type = AccountType.TEACHER
                )
        ))

        val output: CreateAnnouncementUseCase.OutputValues = createAnnouncementUseCase.execute(input)
        assertEquals(output.announcement.club, null)
        assertEquals(output.announcement.content, "{'content':'mock'}")
        assertEquals(output.announcement.title, "Mock Announcement")
        assertEquals(output.announcement.type, "club")
        assertEquals(output.announcement.writerUuid, "teacher-111122223333")
    }

    @Test fun testCreateClubAnnouncementByStudent() {
        val input = CreateAnnouncementUseCase.InputValues(
                writerUuid = "student-111122223333",
                title = "Mock Announcement",
                content = "{'content':'mock'}",
                targetGrade = "",
                targetGroup = "",
                type = "club")

        given(clubRepository.findClubUuidByLeaderUuid(anyString())).willReturn(null)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))

        assertThrows(UnAuthorizedException::class.java) { createAnnouncementUseCase.execute(input) }
    }

    @Test fun testCreateClubAnnouncementByAdmin() {
        val input = CreateAnnouncementUseCase.InputValues(
                writerUuid = "admin-111122223333",
                title = "Mock Announcement",
                content = "{'content':'mock'}",
                targetGrade = "",
                targetGroup = "",
                type = "club")

        given(announcementRepository.persist(any())).willAnswer(returnsFirstArg<Any>())
        given(clubRepository.findClubUuidByLeaderUuid(anyString())).willReturn(null)
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 0,
                        group = 0,
                        name = "어드민",
                        phoneNumber = "01011112222",
                        type = AccountType.ADMIN
                )
        ))
        val output: CreateAnnouncementUseCase.OutputValues = createAnnouncementUseCase.execute(input)
        assertEquals(output.announcement.club, null)
        assertEquals(output.announcement.content, "{'content':'mock'}")
        assertEquals(output.announcement.title, "Mock Announcement")
        assertEquals(output.announcement.type, "club")
        assertEquals(output.announcement.writerUuid, "admin-111122223333")
    }


    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}