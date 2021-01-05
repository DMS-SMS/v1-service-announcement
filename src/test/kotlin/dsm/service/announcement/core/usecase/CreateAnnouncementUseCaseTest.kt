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
import org.mockito.stubbing.Answer

class CreateAnnouncementUseCaseTest(
        @InjectMocks private val createAnnouncementUseCase: CreateAnnouncementUseCase,
        @Mock private val announcementRepository: AnnouncementRepository,
        @Mock private val clubRepository: ClubRepository,
        @Mock private val announcement: Announcement
): UseCaseTest() {
    @Test
    fun createSchoolAnnouncementByTeacher() {
        val input = CreateAnnouncementUseCase.InputValues(
                writerUuid = "teacher-111122223333",
                title = "Mock Announcement",
                content = "{'content':'mock'}",
                targetGrade = "123",
                targetGroup = "1234",
                type = "school")

        given(announcementRepository.persist(any())).willAnswer(returnsFirstArg<Announcement>())
        given(announcementRepository.findById(anyString())).willReturn(null)
        given(clubRepository.findClubUuidByLeaderUuid(anyString())).willReturn(null)
        given(announcement.generateUuid()).willAnswer(Answer<Announcement>() {
            val announcement: Announcement = it.arguments[0] as Announcement
            announcement.uuid = "announcement-111122223333"
            return@Answer announcement
        })

        val output: CreateAnnouncementUseCase.OutputValues = createAnnouncementUseCase.execute(input)
        assertEquals(output,CreateAnnouncementUseCase.OutputValues(announcementUuid = "announcement-111122223333"))
    }
}