package com.example.demo.mapper;

import com.example.demo.bean.Comment;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("select count(*) from t_comment")
    public Integer countComment();

    @Select("select count(*) from t_comment where blogId = #{blogId}")
    public Integer countCommentByBlogId(@Param("blogId") Integer blogId);

    @Select("select * from t_comment where parentId is null and blogId = #{blogId}")
    @Results(id = "commentMap1", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "user", column = "id", one = @One(select = "com.example.demo.mapper.UserMapper.getUserByCommentId")),
    })
    public List<Comment> listCommentWithoutNoParentByBlogId(@Param("blogId") Integer blogId);

    @Select("select * from t_comment where parentId = #{id}")
    @Results(id = "commentMap2", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "user", column = "id", one = @One(select = "com.example.demo.mapper.UserMapper.getUserByCommentId")),
            @Result(property = "parentComment", column = "id", one = @One(select = "com.example.demo.mapper.CommentMapper.getParentCommentById"))
    })
    public List<Comment> listRelatedSonCommentByParentId(@Param("id") Integer id);

    @Select("select * from t_comment where id in (select parentId from t_comment where id = #{id})")
    @Results(id = "commentMap3", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "user", column = "id", one = @One(select = "com.example.demo.mapper.UserMapper.getUserByCommentId")),
    })
    public List<Comment> getParentCommentById(@Param("id") Integer id);

    @Insert("INSERT INTO t_comment (`content`, `time`, `blogId`, `userId`) VALUES (#{content},#{time},#{blogId},#{userId})")
    public void insertCommentWithoutParentId(@Param("content") String content, @Param("time") Date date, @Param("blogId") Integer blogId, @Param("userId") Integer id);

    @Insert("INSERT INTO t_comment (`content`, `time`, `blogId`, `userId`, `parentId`) VALUES (#{content},#{time},#{blogId},#{userId},#{parentId})")
    void insertCommentWithParentId(@Param("content") String content, @Param("time") Date date, @Param("blogId") Integer blogId, @Param("userId") Integer id,@Param("parentId") Integer parentId);

    @Delete("delete from t_comment where blogId = #{blogId}")
    public void deleteCommentByBlogId(@Param("blogId") Integer blogId);

    @Delete("delete from t_comment where blogId in (select id from t_blog where tagId = #{tagId})")
    public void deleteCommentByTagId(@Param("tagId") Integer id);
}
