package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Account
import dsm.service.announcement.domain.repository.TeacherRepository
import org.springframework.stereotype.Component

@Component
class GetAccountUseCaseImpl(
        val teacherRepository: TeacherRepository
): GetAccountUseCase {
    override fun execute(uuid: String): Account? {
        return teacherRepository.findByUuid(uuid);
    }
}