package viosmash.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import viosmash.dataobject.Person;

public interface PersonMapper {

    @Insert("insert into persons(username, password, created_time) values (#{username}, #{password}, #{createdTime})")
//    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer save(Person person);

    @Select("select id, username, password, created_time FROM persons where username = #{username}")
    Person selectByUsername(@Param("username") String username);
}
