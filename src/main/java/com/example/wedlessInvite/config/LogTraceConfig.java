package com.example.wedlessInvite.config;

import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
