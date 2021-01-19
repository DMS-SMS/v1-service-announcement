package dsm.service.announcement.core.usecase

import dsm.service.announcement.controller.mapper.Mapper
import dsm.service.announcement.core.usecase.UseCase.InputValues
import dsm.service.announcement.core.usecase.UseCase.OutputValues
import java.util.function.Function


interface UseCaseExecutor {
    fun <I : InputValues, O : OutputValues, Res> execute(
            useCase: UseCase<I, O>, input: I, outputMapper: Mapper<O, Res>): Res
}