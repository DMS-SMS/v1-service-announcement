package dsm.service.announcement.infra.config.db.mysql

import dsm.service.announcement.infra.config.spring.getBean
import dsm.service.announcement.infra.jaeger.JaegerService
import org.hibernate.EmptyInterceptor
import org.hibernate.Transaction
import org.hibernate.type.Type
import java.io.Serializable

class MysqlInterceptor: EmptyInterceptor() {
    override fun beforeTransactionCompletion(tx: Transaction?) {
        val jaegerHandler = getBean("jaegerHandler") as JaegerService
        jaegerHandler.tracingStart("SQL (Transaction)")
        super.beforeTransactionCompletion(tx)
    }

    override fun afterTransactionCompletion(tx: Transaction?) {
        val jaegerHandler = getBean("jaegerHandler") as JaegerService
        jaegerHandler.tracingEnd()
        super.afterTransactionCompletion(tx)
    }

    override fun onLoad(entity: Any?, id: Serializable?, state: Array<out Any>?, propertyNames: Array<out String>?, types: Array<out Type>?): Boolean {
        val jaegerHandler = getBean("jaegerHandler") as JaegerService
        jaegerHandler.tracingStart("SQL (Select)")
        jaegerHandler.tracingEnd()
        return super.onLoad(entity, id, state, propertyNames, types)
    }
}