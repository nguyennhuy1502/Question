package com.techvify.question.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ConfigModelMapper {
        @Bean
        public ModelMapper initModelMapper() {
            return new ModelMapper();
        }
}
