package dsm.service.announcement.controller

import dsm.service.announcement.controller.mapper.Mapper
import dsm.service.announcement.core.usecase.UseCase
import dsm.service.announcement.core.usecase.UseCaseExecutor
import org.springframework.stereotype.Service

@Service
class UseCaseExecutorImpl : UseCaseExecutor {
    override fun <I : UseCase.InputValues, O : UseCase.OutputValues, Res>
            execute(useCase: UseCase<I, O>, input: I, outputMapper: Mapper<O, Res>): Res {
        return outputMapper.map(useCase.execute(input))
    }
}