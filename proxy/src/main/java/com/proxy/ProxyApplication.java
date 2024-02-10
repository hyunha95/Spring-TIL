package com.proxy;

import com.proxy.config.AppV1Config;
import com.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import com.proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import com.proxy.trace.logtrace.LogTrace;
import com.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import({AppV1Config.class})
//@Import(ProxyFactoryConfigV1.class)
@Import(ProxyFactoryConfigV2.class)
@SpringBootApplication(scanBasePackages = "com.proxy.app")
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }


}
