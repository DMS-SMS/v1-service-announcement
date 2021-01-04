package dsm.service.announcement.v1.infra.mongodb

import dsm.service.announcement.v1.domain.entity.Content
import dsm.service.announcement.v1.infra.jaeger.JaegerHandler
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.mapping.event.*


@Configuration
class MongoInterceptor(
        val jaegerHandler: JaegerHandler
): AbstractMongoEventListener<Content>() {
    override fun onBeforeSave(event: BeforeSaveEvent<Content>) {
        jaegerHandler.tracingStart("MongoDB (Save)")
        super.onBeforeSave(event)
    }

    override fun onAfterSave(event: AfterSaveEvent<Content>) {
        jaegerHandler.tracingEnd()
        super.onAfterSave(event)
    }

    override fun onAfterLoad(event: AfterLoadEvent<Content>) {
        jaegerHandler.tracingStart("MongoDB (Load)")
        jaegerHandler.tracingEnd()
        super.onAfterLoad(event)
    }

    override fun onBeforeDelete(event: BeforeDeleteEvent<Content>) {
        jaegerHandler.tracingStart("MongoDB (Delete)")
        super.onBeforeDelete(event)
    }

    override fun onAfterDelete(event: AfterDeleteEvent<Content>) {
        jaegerHandler.tracingEnd()
        super.onAfterDelete(event)
    }

    override fun onBeforeConvert(event: BeforeConvertEvent<Content>) {
        jaegerHandler.tracingStart("MongoDB (Convert)")
        jaegerHandler.tracingEnd()
        super.onBeforeConvert(event)
    }
}