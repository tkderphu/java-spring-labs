package viosmash.mappper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import viosmash.dataobject.Person;

import java.util.List;


public interface PersonMapper extends BaseMapper<Person> {
    default Person selectByUsername(@Param("username") String username) {
        return selectOne(new QueryWrapper<Person>().eq("username", username));
    }

    default Integer deleteByUsername(String username) {
        return delete(new QueryWrapper<Person>().eq("username", username));
    }

    default List<Person> selectAll() {
        return selectList(null);
    }
    List<Person> selectListA();
}
