package viosmash.convert;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import viosmash.bo.User;
import viosmash.bo.UserDetails;
import viosmash.db.UserDO;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    User convert(UserDO userDO);

    @Mappings({
            @Mapping(source = "id", target = "userId")
    })
    UserDetails convertDetail(UserDO userDO);
}
