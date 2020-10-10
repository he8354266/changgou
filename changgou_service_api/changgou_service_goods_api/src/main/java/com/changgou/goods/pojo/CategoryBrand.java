package com.changgou.goods.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/2811:33
 */
@Table(name = "tb_category_brand")
@Data
public class CategoryBrand {
    //分类id
    @Id
    private Integer categoryId;
    //品牌ID
    @Id
    private Integer brandId;

}
