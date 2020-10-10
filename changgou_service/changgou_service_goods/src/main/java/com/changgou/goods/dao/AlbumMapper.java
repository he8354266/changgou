package com.changgou.goods.dao;

import com.changgou.goods.pojo.Album;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface AlbumMapper extends Mapper<Album> {
    @Select("select * from tb_album")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "image", property = "image"),
            @Result(column = "image_items", property = "imageItems"),
    })
    List<Album> findList();


    @Insert("insert into tb_album(title,image) values(#{title,jdbcType=VARCHAR},#{image,jdbcType=VARCHAR})")
    int insertByMap(Map<String, Object> map);

    @Delete("delete from tb_album where id={#id}")
    int deleteAlbum(int id);

    @Update("update tb_album set image_items={#imageItems} where id={#id}")
    int updateAlbum(Album album);
}
