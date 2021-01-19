package dsm.service.announcement.data.adapter.repository.mapper

import com.mongodb.BasicDBObject
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.data.db.jpa.model.AnnouncementDetailModel
import dsm.service.announcement.data.db.jpa.model.AnnouncementModel
import org.springframework.stereotype.Component

@Component
class AnnouncementRepositoryMapper(
    val getAccountUseCase: GetAccountUseCase
) {
    fun map(announcement: Announcement): AnnouncementModels {
        return AnnouncementModels(
            announcementModel = AnnouncementModel(
                uuid = announcement.uuid,
                number = announcement.number,
                writerUuid = announcement.writerUuid,
                date = announcement.date,
                title = announcement.title,
                targetGrade = announcement.targetGrade,
                targetGroup = announcement.targetClass,
                type = announcement.type,
                club = announcement.club
            ),
            announcementDetailModel = AnnouncementDetailModel(
                uuid = announcement.uuid,
                content = BasicDBObject.parse(announcement.content),
                read_accounts = announcement.readAccounts
            )
        )
    }

    fun map(announcementModel: AnnouncementModel?, announcementDetailModel: AnnouncementDetailModel?): Announcement? {
        if (announcementModel == null || announcementDetailModel == null) return null

        println("MAPPER [=]")

        return Announcement(
             uuid = announcementModel.uuid,
             number = announcementModel.number,
             writerUuid = announcementModel.writerUuid,
             writerName =
             if (announcementModel.club == null)
                 getAccountUseCase.execute(GetAccountUseCase.InputValues(announcementModel.writerUuid)).account?.name
             else announcementModel.club,
             date = announcementModel.date,
             title = announcementModel.title,
             targetGrade = announcementModel.targetGrade,
             targetClass = announcementModel.targetGroup,
             type = announcementModel.type,
             club = announcementModel.club,
             content = announcementDetailModel.content.toJson(),
             readAccounts = announcementDetailModel.read_accounts
        )
    }

    class AnnouncementModels (
        val announcementModel: AnnouncementModel,
        val announcementDetailModel: AnnouncementDetailModel
    )
}