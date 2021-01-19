package dsm.service.announcement.infra.config.db.mongo

import dsm.service.announcement.data.db.jpa.model.AnnouncementDetailModel
import dsm.service.announcement.infra.jaeger.JaegerService
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.mapping.event.*

@Configuration
class MongoInterceptor(
    val jaegerService: JaegerService
): AbstractMongoEventListener<AnnouncementDetailModel>() {
    override fun onBeforeSave(event: BeforeSaveEvent<AnnouncementDetailModel>) {
        jaegerService.tracingStart("MongoDB (Save)")
        super.onBeforeSave(event)
    }

    override fun onAfterSave(event: AfterSaveEvent<AnnouncementDetailModel>) {
        jaegerService.tracingEnd()
        super.onAfterSave(event)
    }

    override fun onAfterLoad(event: AfterLoadEvent<AnnouncementDetailModel>) {
        jaegerService.tracingStart("MongoDB (Load)")
        jaegerService.tracingEnd()
        super.onAfterLoad(event)
    }

    override fun onBeforeDelete(event: BeforeDeleteEvent<AnnouncementDetailModel>) {
        jaegerService.tracingStart("MongoDB (Delete)")
        super.onBeforeDelete(event)
    }

    override fun onAfterDelete(event: AfterDeleteEvent<AnnouncementDetailModel>) {
        jaegerService.tracingEnd()
        super.onAfterDelete(event)
    }

    override fun onBeforeConvert(event: BeforeConvertEvent<AnnouncementDetailModel>) {
        jaegerService.tracingStart("MongoDB (Convert)")
        jaegerService.tracingEnd()
        super.onBeforeConvert(event)
    }
}