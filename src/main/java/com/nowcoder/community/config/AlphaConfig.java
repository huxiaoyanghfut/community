package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author:xiaoyang
 * @Title: AlphaConfig
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/10 19:49
 */
@Configuration
public class AlphaConfig {
    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
