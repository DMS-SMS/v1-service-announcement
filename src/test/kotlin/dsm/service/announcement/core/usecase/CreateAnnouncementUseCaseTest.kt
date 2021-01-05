package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.domain.repository.ClubRepository
import dsm.service.announcement.core.usecase.announcement.CreateAnnouncementUseCase
import org.junit.Test
import org.junit.Assert.*
import org.mockito.ArgumentMatchers.any
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.stubbing.Answer

class CreateAnnouncementUseCaseTest(

): UseCaseTest() {
    @InjectMocks private lateinit var createAnnouncementUseCase: CreateAnnouncementUseCase
    @Mock private lateinit var  announcementRepository: AnnouncementRepository
    @Mock private lateinit var clubRepository: ClubRepository

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
        given(clubRepository.findClubUuidByLeaderUuid(anyString())).willReturn(null)

        val output: CreateAnnouncementUseCase.OutputValues = createAnnouncementUseCase.execute(input)
        assertNotNull(output)
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}