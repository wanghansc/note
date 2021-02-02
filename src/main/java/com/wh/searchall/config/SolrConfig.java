package com.wh.searchall.config;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @ClassName SolrConfig
 * @Description SolrTemplate配置类
 * @Author wanghan
 * @Date 2020/1/20 14:46
 **/
@Configuration
public class SolrConfig {
    @Autowired
    private SolrClient solrClient;

    @Bean
    public SolrTemplate getSoleTemple(){
        return new SolrTemplate(solrClient);
    }


}
