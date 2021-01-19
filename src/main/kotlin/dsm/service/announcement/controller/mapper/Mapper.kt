package dsm.service.announcement.controller.mapper

abstract class Mapper<I, O> {
    abstract fun map(input: I): O
}