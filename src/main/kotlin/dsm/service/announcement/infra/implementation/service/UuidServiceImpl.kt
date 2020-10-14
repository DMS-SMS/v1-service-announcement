package dsm.service.announcement.infra.implementation.service

import dsm.service.announcement.domain.service.UuidService
import org.springframework.stereotype.Component


@Component
class UuidServiceImpl: UuidService {
    override fun createAnnouncementUuid(): String {
        // TODO UUID 로직 추가
        return "announcement-111122223333"
    }
}