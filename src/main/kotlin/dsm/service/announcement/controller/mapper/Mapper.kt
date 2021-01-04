package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.CreateAnnouncementUseCase
import dsm.service.announcement.proto.CreateAnnouncementRequest

abstract class Mapper<I, O> {
    abstract fun map(input: I): O
}