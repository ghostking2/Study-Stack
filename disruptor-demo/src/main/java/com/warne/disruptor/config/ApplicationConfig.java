package com.warne.disruptor.config;

import com.warne.disruptor.mongodb.MongoTools;
import com.warne.disruptor.service.OrderModuleQueue;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * function：description
 * datetime：2019-04-16 16:20
 * author：warne
 */
@Configuration
@ComponentScan(basePackages = "com.warne.*")
public class ApplicationConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    static OrderModuleQueue orderInfoQueue() {
        return new OrderModuleQueue();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    static MongoTools mongoTools() {
        return new MongoTools();
    }

    /**
     * 配置static bean可以早些实例化
     */
}
