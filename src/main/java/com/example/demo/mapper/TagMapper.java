package com.example.demo.mapper;

import com.example.demo.bean.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from t_tag where id in (select tagId from t_blog where id = #{blogId})")
    public Tag getOneByBlogId(@Param("blogId") Integer blogId);

    @Results(id = "tagMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "blogNumber", column = "id", one = @One(select = "com.example.demo.mapper.BlogMapper.countBlogByTagId")),
    })
    @Select("select * from t_tag")
    public List<Tag> listTag();

    @Delete("delete from t_tag where id = #{id}")
    public void deleteTagById(@Param("id") Integer id);

    @Insert("INSERT INTO t_tag (`name`) VALUES (#{name})")
    public void insertOne(@Param("name") String name);

    @Select("select name from t_tag")
    public List<String> listTagName();

    @Update("update t_tag set name = #{name} where id = #{id}")
    public void updateTagName(@Param("id") Integer id,@Param("name") String name);
}
