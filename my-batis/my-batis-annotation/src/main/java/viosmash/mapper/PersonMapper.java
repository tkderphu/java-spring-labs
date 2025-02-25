package viosmash.mapper;

import org.apache.ibatis.annotations.*;
import viosmash.dataobject.Person;

import java.util.Collection;
import java.util.List;

public interface PersonMapper {

    @Insert("insert into persons(username, password, created_time) values (#{username}, #{password}, #{createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer save(Person person);

    @Update(value = {
            "<script>",
            "update persons",
            "<set>",
            "<if test='username != null'>username=#{username},</if>",
            "<if test='password != null'>password=#{password},</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    Integer update(Person person);

    @Select("select id, username, password, created_time FROM persons where username = #{username}")
    Person selectByUsername(@Param("username") String username);


    @Select(value = {
            "<script>",
            "SELECT id, username, password, created_time FROM persons",
            "<where> id IN",
            "<foreach item='id' collection='ids' index='' open='(' close=')' separator=',' >",
            "#{id}",
            "</foreach>",
            "</where>",
            "</script>"
    })
    List<Person> selectByIds(@Param("ids")Collection<Integer> ids);


}
