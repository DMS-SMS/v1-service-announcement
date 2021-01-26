package dsm.service.announcement.infra.mq.sqs

import com.amazonaws.regions.Regions

import com.amazonaws.auth.AWSStaticCredentialsProvider

import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder

import com.amazonaws.services.sqs.AmazonSQSAsync

import org.springframework.cloud.aws.core.region.RegionProvider

import com.amazonaws.auth.AWSCredentialsProvider

import com.amazonaws.auth.BasicAWSCredentials

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.regions.Region
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
class AwsSqsConfig {
    private val awsAccessKey: String = System.getenv("AWS_ACCESS_KEY")

    private val awsSecretKey: String = System.getenv("AWS_SECRET_KEY")

    @Bean
    fun credential(): AWSCredentials {
        return BasicAWSCredentials(awsAccessKey, awsSecretKey)
    }

    @Bean
    fun awsCredentialsProvider(): AWSCredentialsProvider {
        return AWSStaticCredentialsProvider(credential())
    }

    @Bean
    fun regionProvider(): RegionProvider {
        return RegionProvider { Region.getRegion(Regions.AP_NORTHEAST_2) }
    }

    @Bean
    @Primary 
    fun amazonSQSAsync(): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credential()))
            .withRegion(Regions.AP_NORTHEAST_2)
            .build()
    }
}