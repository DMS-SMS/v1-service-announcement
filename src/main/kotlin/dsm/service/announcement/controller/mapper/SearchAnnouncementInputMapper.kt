package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.SearchAnnouncementsUseCase
import dsm.service.announcement.proto.SearchAnnouncementsRequest
import org.springframework.stereotype.Component

@Component
class SearchAnnouncementInputMapper {
    fun map(searchAnnouncementsRequest: SearchAnnouncementsRequest): SearchAnnouncementsUseCase.InputValues {
        return SearchAnnouncementsUseCase.InputValues(
                accountUuid = searchAnnouncementsRequest.uuid,
                query = searchAnnouncementsRequest.query,
                type = searchAnnouncementsRequest.type,
                start = searchAnnouncementsRequest.start,
                count = searchAnnouncementsRequest.count
        )
    }
}