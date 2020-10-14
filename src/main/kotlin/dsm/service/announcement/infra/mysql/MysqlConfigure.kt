package dsm.service.announcement.infra.mysql

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
public class MysqlConfigure {
    @Bean
    fun dataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
        dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/SMS_Announcement_DB?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false")
        dataSourceBuilder.username("root")
        dataSourceBuilder.password("mingi0130")
        return dataSourceBuilder.build();
    }
}