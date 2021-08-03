package com.example.demo.mapper;

import com.example.demo.bean.Blog;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface BlogMapper {

    @Results(id = "blogMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "content", column = "content"),
            @Result(property = "picture", column = "picture"),
            @Result(property = "view", column = "view"),
            @Result(property = "like", column = "like"),
            @Result(property = "time", column = "time"),
            @Result(property = "tag", column = "id", one = @One(select = "com.example.demo.mapper.TagMapper.getOneByBlogId")),
            @Result(property = "commentNumber", column = "id", one = @One(select = "com.example.demo.mapper.CommentMapper.countCommentByBlogId"))
    })
    @Select("select * from t_blog")
    public List<Blog> listBlog();


    @Insert("insert into t_blog(`title`, `description`, `content`, `picture`, `view`, `like`, `time`,`tagId`) values (#{title},#{description},#{content},#{picture},#{view},#{like},#{time},#{tagId})")
    public Integer insertBlog(@Param("title") String title, @Param("description") String description, @Param("content") String content, @Param("picture") String picture, @Param("view") Integer view, @Param("like") Integer like, @Param("time") Date time, @Param("tagId") Integer tagId);

    @Select("select count(*) from t_blog")
    public Integer countBlog();

    @Select("select ifnull(sum(view),0) from t_blog")
    public Integer countTotalView();

    @Select("select count(*) from t_blog where tagId = #{tagId}")
    public Integer countBlogByTagId(@Param("tagId") Integer tagId);

    @Results(id = "blogMap2", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "content", column = "content"),
            @Result(property = "picture", column = "picture"),
            @Result(property = "view", column = "view"),
            @Result(property = "like", column = "like"),
            @Result(property = "time", column = "time"),
            @Result(property = "tag", column = "id", one = @One(select = "com.example.demo.mapper.TagMapper.getOneByBlogId")),
            @Result(property = "commentNumber", column = "id", one = @One(select = "com.example.demo.mapper.CommentMapper.countCommentByBlogId"))
    })
    @Select("select * from t_blog where id = #{id}")
    public Blog getOneById(@Param("id") Integer id);

    @ResultMap("blogMap")
    @Select("select * from t_blog order by view desc limit 0,5")
    public List<Blog> listBlogByViewLimited();

    @ResultMap("blogMap")
    @Select("select * from t_blog where tagId in (select id from t_tag where name = #{tagName})")
    public List<Blog> listBlogByTagName(@Param("tagName") String tagName);

    @Delete("delete from t_blog where id = #{id}")
    public void deleteBlogById(@Param("id") Integer id);

    @Delete("delete from t_blog where tagId = #{tagId}")
    public void deleteBlogByTagId(@Param("tagId") Integer id);

    @Update("update t_blog set title = #{title} , description = #{description},content=#{content},picture=#{picture},tagId = #{tagId} where id = #{id}")
    public void updateBlogById(@Param("id") Integer id,@Param("title") String title,@Param("description") String description,@Param("content") String content,@Param("picture") String picture,@Param("tagId") Integer tagId);

    @Update("update t_blog set view = view + 1  where id = #{id}")
    public void incrementViewByBlogId(@Param("id") Integer id);
}
