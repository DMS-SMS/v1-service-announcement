package dsm.service.announcement.core.usecase

abstract class UseCase<I : UseCase.InputValues, O : UseCase.OutputValues> {
    abstract fun execute(input: I): O

    interface InputValues

    interface OutputValues
}