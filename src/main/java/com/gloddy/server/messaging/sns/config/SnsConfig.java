package com.gloddy.server.messaging.sns.config;

import io.awspring.cloud.sns.core.TopicArnResolver;
import io.awspring.cloud.sns.core.TopicsListingTopicArnResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
@EnableConfigurationProperties(SnsProperties.class)
public class SnsConfig {

    @Bean
    public TopicArnResolver topicArnResolver(SnsClient snsClient) {
        return new TopicsListingTopicArnResolver(snsClient);
    }
}
