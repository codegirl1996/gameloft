package com.gameloft.profiler_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan
public class ProfilerConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
