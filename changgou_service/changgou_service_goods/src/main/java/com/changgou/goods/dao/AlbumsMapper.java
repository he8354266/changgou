package com.changgou.goods.dao;

import com.changgou.goods.pojo.Album;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1010:27
 */
public interface AlbumsMapper {
    @Select("select * from tb_album")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "image", property = "image"),
            @Result(column = "image_items", property = "imageItems"),
    })
    List<Album> findList();

    @Select("select * from tb_category left JOIN tb_template ON tb_category.template_id=tb_template.id=#{templateId}")
    @Results({
            @Result(column = "goods_num", property = "goodsNum"),
            @Result(column = "template_id", property = "templateId"),
            @Result(column = "is_menu", property = "isMenu"),
            @Result(column = "is_show", property = "isShow")
    })
    List<Map> findTemplate(int templateId);

    @Insert("insert into tb_album(title,image) values(#{title,jdbcType=VARCHAR},#{image,jdbcType=VARCHAR})")
    int insertByMap(Map<String, Object> map);

    @Delete("delete from tb_album where id=#{id}")
    int deleteAlbum(int id);

    @Update("update tb_album set image_items=#{imageItems} where id=#{id}")
    int updateAlbum(Album album);
}
