package com.nowcoder.community.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @author:xiaoyang
 * @Title: ElasticsearchConfig
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/10/06 22:02
 */
@Configuration
public class ElasticsearchConfig {
    @Bean
    RestHighLevelClient elasticsearchClient() {
        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        RestHighLevelClient client = RestClients.create(configuration).rest();

        return client;
    }
}
