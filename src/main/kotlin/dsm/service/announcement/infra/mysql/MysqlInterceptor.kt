package dsm.service.announcement.infra.mysql

import dsm.service.announcement.infra.jaeger.JaegerHandler
import dsm.service.announcement.infra.springBoot.getBean
import org.hibernate.EmptyInterceptor
import org.hibernate.EntityMode
import org.hibernate.Transaction
import org.hibernate.type.Type
import org.springframework.beans.BeanUtils
import java.io.Serializable


class MysqlInterceptor: EmptyInterceptor() {
    init {
        val jaegerHandler: JaegerHandler
    }
    override fun beforeTransactionCompletion(tx: Transaction?) {
        val jaegerHandler = getBean("jaegerHandler") as JaegerHandler
        jaegerHandler.tracingStart("SQL (Transaction)")
        super.beforeTransactionCompletion(tx)
    }

    override fun afterTransactionCompletion(tx: Transaction?) {
        val jaegerHandler = getBean("jaegerHandler") as JaegerHandler
        jaegerHandler.tracingEnd()
        super.afterTransactionCompletion(tx)
    }

    override fun onLoad(entity: Any?, id: Serializable?, state: Array<out Any>?, propertyNames: Array<out String>?, types: Array<out Type>?): Boolean {
        val jaegerHandler = getBean("jaegerHandler") as JaegerHandler
        jaegerHandler.tracingStart("SQL (Select)")
        jaegerHandler.tracingEnd()
        return super.onLoad(entity, id, state, propertyNames, types)
    }
}