package com.changgou.search.dao;

import com.changgou.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/2918:35
 */
public interface ESManagerMapper extends ElasticsearchCrudRepository<SkuInfo,Long> {
}
