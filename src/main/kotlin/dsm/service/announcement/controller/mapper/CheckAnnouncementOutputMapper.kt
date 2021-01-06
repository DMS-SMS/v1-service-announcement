package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.CheckAnnouncementUseCase
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.proto.CheckAnnouncementResponse
import org.springframework.stereotype.Component

@Component
class CheckAnnouncementOutputMapper: Mapper<CheckAnnouncementUseCase.OutputValues, CheckAnnouncementResponse>() {
    override fun map(input: CheckAnnouncementUseCase.OutputValues): CheckAnnouncementResponse {
        return CheckAnnouncementResponse
                .newBuilder()
                .setSchool(if (input.schoolIsCheck) 1 else 0)
                .setClub(if (input.clubIsCheck) 1 else 0)
                .setStatus(200)
                .build()

    }
}