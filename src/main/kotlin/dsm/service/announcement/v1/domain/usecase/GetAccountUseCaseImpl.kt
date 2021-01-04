package dsm.service.announcement.v1.domain.usecase

import dsm.service.announcement.v1.domain.entity.Account
import dsm.service.announcement.v1.domain.repository.TeacherRepository
import org.springframework.stereotype.Component

@Component
class GetAccountUseCaseImpl(
        val teacherRepository: TeacherRepository
): GetAccountUseCase {
    override fun execute(uuid: String): Account? {
        return teacherRepository.findByUuid(uuid);
    }
}