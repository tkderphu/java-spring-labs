package viosmash.mappers;

import org.apache.ibatis.annotations.Mapper;
import viosmash.dataobject.PersonDO;

public interface PersonMapper {
    Integer insert(PersonDO personDO);
    Integer deleteById(Integer id);
    PersonDO selectByUsername(String username);
}
