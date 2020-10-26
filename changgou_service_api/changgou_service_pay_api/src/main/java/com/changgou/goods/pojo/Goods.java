package com.changgou.goods.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.List;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/289:17
 */
@Data
public class Goods {
    private Spu spu;
    //sku集合
    private List<Sku> skuList;


}
