package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.usecase.UseCase.InputValues
import dsm.service.announcement.core.usecase.UseCase.OutputValues
import java.util.concurrent.CompletableFuture
import java.util.function.Function


interface UseCaseExecutor {
    fun <RX, I : InputValues, O : OutputValues> execute(
            useCase: UseCase<I, O>, input: I, outputMapper: Function<O, RX>): CompletableFuture<RX>
}