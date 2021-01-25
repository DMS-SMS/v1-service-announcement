package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GetMyAnnouncementsUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val getAccountUseCase: GetAccountUseCase
): UseCase<GetMyAnnouncementsUseCase.InputValues, GetMyAnnouncementsUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues = generateOutputValue(input)

    private fun generateOutputValue(input: InputValues): OutputValues {
        checkTeacher(input.accountUuid)
        return OutputValues(
                input.accountUuid,
                announcementRepository
                        .findByWriterUuidAndTypeOrderByDateDesc(
                                writerUuid = input.accountUuid,
                                type = "school",
                                pageable = PageRequest.of(input.start, input.count)),
                announcementRepository.countByWriterUuidAndType(input.accountUuid, "school"))
    }

    private fun checkTeacher(writerUuid: String) {
        getAccountUseCase.execute(GetAccountUseCase.InputValues(writerUuid)).account
                ?.let { account ->
                    if (account.type != AccountType.ADMIN)
                        if (account.type != AccountType.TEACHER) throw UnAuthorizedException()
                }
                ?: throw UnAuthorizedException()
    }

    class InputValues(
            val accountUuid: String,
            val start: Int,
            val count: Int
    ): UseCase.InputValues

    class OutputValues(
            val accountUuid: String,
            val announcements: MutableIterable<Announcement>,
            val count: Long
    ): UseCase.OutputValues
}