package com.gloddy.server.config;

import com.gloddy.server.authSms.infra.ncloud.SmsFeignError;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients({"com.gloddy.server"})
public class OpenFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new SmsFeignError();
    }
}
