package com.example.demo.mapper;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_user where binary username = #{username}")
    public User getUserByUsername(@Param("username")String username);

    @Select("select * from t_user where id in (select userId from t_comment where id = #{commentId})")
    public User getUserByCommentId(@Param("commentId")Integer commentId);

    @Insert("INSERT INTO t_user (`username`, `password`, `email`, `picture`, `time`, `type`) VALUES (#{username},#{password},#{email},#{picture},#{time},#{type})")
    public void insertOne(@Param("username") String username,@Param("password") String password,@Param("email") String email,@Param("picture") String picture,@Param("time") Date date,@Param("type") int i);

    @Select("select email from t_user")
    public List<String> listAllEmail();

    @Select("select * from t_user where email = #{email}")
    public User getUserByEmail(@Param("email") String email);
}
