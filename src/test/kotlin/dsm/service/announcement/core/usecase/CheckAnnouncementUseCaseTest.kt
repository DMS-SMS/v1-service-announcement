package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.CheckAnnouncementUseCase
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import junit.framework.Assert.assertEquals

import org.junit.Assert.*
import org.junit.Test
import org.mockito.AdditionalAnswers
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime

class CheckAnnouncementUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var checkAnnouncementUseCase: CheckAnnouncementUseCase
    @Mock private lateinit var  announcementRepository: AnnouncementRepository
    @Mock private lateinit var getAccountUseCase: GetAccountUseCase

    @Test fun testCheckClubAnnouncement() {
        val input = CheckAnnouncementUseCase.InputValues("student-111122223333")

        given(announcementRepository.findByTypeOrderByDateDesc(anyString())).willReturn(
                mutableListOf(Announcement(
                        uuid = "announcement-111122223333",
                        number = 1,
                        writerUuid = "student-111122223334",
                        writerName= "동아리장",
                        date = LocalDateTime.now(),
                        title = "동아리파괴",
                        targetGrade = null,
                        targetClass = null,
                        type = "club",
                        club = "KODOMO",
                        content = "동아리 그만둡니다",
                        readAccounts = arrayListOf("student-111122223333", "student-111122223334"))))
        given(announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                anyString(), anyString(), anyString()
        )).willReturn(mutableListOf(Announcement(
                uuid = "announcement-111122223333",
                number = 2,
                writerUuid = "teacher-111122223334",
                writerName= "학교장",
                date = LocalDateTime.now(),
                title = "학교파괴",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                content = "학교 그만둡니다",
                readAccounts = arrayListOf("student-111122223334"))))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))
        val output = checkAnnouncementUseCase.execute(input)

        assertEquals(output.schoolIsCheck, false)
        assertEquals(output.clubIsCheck, true)
    }

    @Test fun testCheckSchoolAnnouncement() {
        val input = CheckAnnouncementUseCase.InputValues("student-111122223333")

        given(announcementRepository.findByTypeOrderByDateDesc(anyString())).willReturn(
                mutableListOf(Announcement(
                        uuid = "announcement-111122223333",
                        number = 1,
                        writerUuid = "student-111122223334",
                        writerName= "동아리장",
                        date = LocalDateTime.now(),
                        title = "동아리파괴",
                        targetGrade = null,
                        targetClass = null,
                        type = "club",
                        club = "KODOMO",
                        content = "동아리 그만둡니다",
                        readAccounts = arrayListOf("student-111122223335", "student-111122223334"))))
        given(announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                anyString(), anyString(), anyString()
        )).willReturn(mutableListOf(Announcement(
                uuid = "announcement-111122223333",
                number = 2,
                writerUuid = "teacher-111122223334",
                writerName= "학교장",
                date = LocalDateTime.now(),
                title = "학교파괴",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                content = "학교 그만둡니다",
                readAccounts = arrayListOf("student-111122223333"))))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))
        val output = checkAnnouncementUseCase.execute(input)

        assertEquals(output.schoolIsCheck, true)
        assertEquals(output.clubIsCheck, false)
    }

    @Test fun testCheckClubAndSchoolAnnouncement() {
        val input = CheckAnnouncementUseCase.InputValues("student-111122223333")

        given(announcementRepository.findByTypeOrderByDateDesc(anyString())).willReturn(
                mutableListOf(Announcement(
                        uuid = "announcement-111122223333",
                        number = 1,
                        writerUuid = "student-111122223334",
                        writerName= "동아리장",
                        date = LocalDateTime.now(),
                        title = "동아리파괴",
                        targetGrade = null,
                        targetClass = null,
                        type = "club",
                        club = "KODOMO",
                        content = "동아리 그만둡니다",
                        readAccounts = arrayListOf("student-111122223333", "student-111122223335", "student-111122223334"))))
        given(announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                anyString(), anyString(), anyString()
        )).willReturn(mutableListOf(Announcement(
                uuid = "announcement-111122223333",
                number = 2,
                writerUuid = "teacher-111122223334",
                writerName= "학교장",
                date = LocalDateTime.now(),
                title = "학교파괴",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                content = "학교 그만둡니다",
                readAccounts = arrayListOf("student-111122223333"))))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))
        val output = checkAnnouncementUseCase.execute(input)

        assertEquals(output.schoolIsCheck, true)
        assertEquals(output.clubIsCheck, true)
    }

    @Test fun testCheckAnnouncement() {
        val input = CheckAnnouncementUseCase.InputValues("student-111122223333")

        given(announcementRepository.findByTypeOrderByDateDesc(anyString())).willReturn(
                mutableListOf(Announcement(
                        uuid = "announcement-111122223333",
                        number = 1,
                        writerUuid = "student-111122223334",
                        writerName= "동아리장",
                        date = LocalDateTime.now(),
                        title = "동아리파괴",
                        targetGrade = null,
                        targetClass = null,
                        type = "club",
                        club = "KODOMO",
                        content = "동아리 그만둡니다",
                        readAccounts = arrayListOf("st udent-111122223335", "student-111122223334"))))
        given(announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                anyString(), anyString(), anyString()
        )).willReturn(mutableListOf(Announcement(
                uuid = "announcement-111122223333",
                number = 2,
                writerUuid = "teacher-111122223334",
                writerName= "학교장",
                date = LocalDateTime.now(),
                title = "학교파괴",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                content = "학교 그만둡니다",
                readAccounts = arrayListOf())))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))
        val output = checkAnnouncementUseCase.execute(input)

        assertEquals(output.schoolIsCheck, false)
        assertEquals(output.clubIsCheck, false)
    }

    @Test fun testCheckLotOfAnnouncement() {
        val input = CheckAnnouncementUseCase.InputValues("student-111122223333")

        given(announcementRepository.findByTypeOrderByDateDesc(anyString())).willReturn(
                mutableListOf(Announcement(
                        uuid = "announcement-111122223333",
                        number = 1,
                        writerUuid = "student-111122223334",
                        writerName= "동아리장",
                        date = LocalDateTime.now(),
                        title = "동아리파괴",
                        targetGrade = null,
                        targetClass = null,
                        type = "club",
                        club = "KODOMO",
                        content = "동아리 그만둡니다",
                        readAccounts = arrayListOf("student-111122223335", "student-111122223334"))),
                mutableListOf(Announcement(
                        uuid = "announcement-111122223333",
                        number = 1,
                        writerUuid = "student-111122223334",
                        writerName= "동아리장",
                        date = LocalDateTime.now(),
                        title = "동아리파괴",
                        targetGrade = null,
                        targetClass = null,
                        type = "club",
                        club = "KODOMO",
                        content = "동아리 그만둡니다",
                        readAccounts = arrayListOf("student-111122223335", "student-111122223333", "student-111122223334"))),
                mutableListOf(Announcement(
                        uuid = "announcement-111122223333",
                        number = 1,
                        writerUuid = "student-111122223334",
                        writerName= "동아리장",
                        date = LocalDateTime.now(),
                        title = "동아리파괴",
                        targetGrade = null,
                        targetClass = null,
                        type = "club",
                        club = "KODOMO",
                        content = "동아리 그만둡니다",
                        readAccounts = arrayListOf("student-111122223335", "student-111122223333", "student-111122223334"))))
        given(announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                anyString(), anyString(), anyString()
        )).willReturn(mutableListOf(Announcement(
                uuid = "announcement-111122223333",
                number = 2,
                writerUuid = "teacher-111122223334",
                writerName= "학교장",
                date = LocalDateTime.now(),
                title = "학교파괴",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                content = "학교 그만둡니다",
                readAccounts = arrayListOf())))
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))
        val output = checkAnnouncementUseCase.execute(input)

        assertEquals(output.schoolIsCheck, false)
        assertEquals(output.clubIsCheck, false)
    }

    @Test fun testCheckEmptyAnnouncement() {
        val input = CheckAnnouncementUseCase.InputValues("student-111122223333")

        given(announcementRepository.findByTypeOrderByDateDesc(anyString())).willReturn(
                mutableListOf())
        given(announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                anyString(), anyString(), anyString()
        )).willReturn(mutableListOf())
        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "학생",
                        phoneNumber = "01011112222",
                        type = AccountType.STUDENT
                )
        ))
        val output = checkAnnouncementUseCase.execute(input)

        assertEquals(output.schoolIsCheck, true)
        assertEquals(output.clubIsCheck, true)
    }

    @Test fun testCheckAnnouncementByTeacher() {
        val input = CheckAnnouncementUseCase.InputValues("teacher-111122223333")

        given(getAccountUseCase.execute(any())).willReturn(GetAccountUseCase.OutputValues(
                Account(
                        grade = 1,
                        group = 1,
                        name = "이선생",
                        phoneNumber = "01011112222",
                        type = AccountType.TEACHER
                )
        ))
        assertThrows(UnAuthorizedException::class.java) { checkAnnouncementUseCase.execute(input) }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

}