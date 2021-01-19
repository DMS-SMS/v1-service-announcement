package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.announcement.GetAnnouncementDetailUseCase
import dsm.service.announcement.core.usecase.announcement.GetNextAnnouncementUseCase
import dsm.service.announcement.core.usecase.announcement.GetPreviousAnnouncementUseCase
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import org.mockito.AdditionalAnswers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime

class GetAnnouncementDetailUseCaseTest: UseCaseTest() {
    @InjectMocks lateinit var getAnnouncementDetailUseCase: GetAnnouncementDetailUseCase
    @Mock lateinit var announcementRepository: AnnouncementRepository
    @Mock lateinit var getNextAnnouncementUseCase: GetNextAnnouncementUseCase
    @Mock lateinit var getPreviousAnnouncementUseCase: GetPreviousAnnouncementUseCase

    @Test fun getAnnouncement() {
        val input = GetAnnouncementDetailUseCase.InputValues(
            announcementUuid = "announcement-111122223333",
            accountUuid = "student-111122223333"
        )

        val announcement = Announcement(
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
            readAccounts = arrayListOf()
        )

        given(announcementRepository.findById(anyString())).willReturn(announcement)
        given(announcementRepository.persist(any())).willAnswer(AdditionalAnswers.returnsFirstArg<Any>())
        given(getNextAnnouncementUseCase.execute(any())).willReturn(GetNextAnnouncementUseCase.OutputValues(null))
        given(getPreviousAnnouncementUseCase.execute(any())).willReturn(GetPreviousAnnouncementUseCase.OutputValues(
            Announcement(
                uuid = "announcement-111122223332",
                number = 1,
                writerUuid = "teacher-111122223334",
                writerName= "학교장",
                date = LocalDateTime.now(),
                title = "학교파괴",
                targetGrade = "1",
                targetClass = "1",
                type = "school",
                club = null,
                content = "학교 그만둡니다",
                readAccounts = arrayListOf()
            )
        ))

        val output = getAnnouncementDetailUseCase.execute(input)

        assertEquals(output.announcement, announcement.read("student-111122223333"))
        assertEquals(output.nextAnnouncement, null)
        assertEquals(output.previousAnnouncement?.number?.toInt(), 1)
    }

    @Test fun getNotExistAnnouncement() {
        val input = GetAnnouncementDetailUseCase.InputValues(
            announcementUuid = "announcement-111122223333",
            accountUuid = "student-111122223333"
        )

        given(announcementRepository.findById(anyString())).willReturn(null)

        Assert.assertThrows(NotFoundException::class.java) { getAnnouncementDetailUseCase.execute(input) }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}