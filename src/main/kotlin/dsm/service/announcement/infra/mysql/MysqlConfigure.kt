package dsm.service.announcement.infra.mysql

import dsm.service.announcement.infra.consul.ConsulHandler
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
public class MysqlConfigure(
        private val consulHandler: ConsulHandler
) {
    @Bean
    fun dataSource(): DataSource {
        val databaseConfig = consulHandler.getValue("db/announcement/local")
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
        dataSourceBuilder.url(databaseConfig.get("url") as String?)
        dataSourceBuilder.username(databaseConfig.get("username") as String?)
        dataSourceBuilder.password(System.getenv("DB_PASSWORD"))
        return dataSourceBuilder.build();
    }
}