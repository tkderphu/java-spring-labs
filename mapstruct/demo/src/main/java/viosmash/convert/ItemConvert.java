package viosmash.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import viosmash.bo.Item;
import viosmash.db.ItemDO;

import java.util.List;

@Mapper
public interface ItemConvert {
    ItemConvert INSTANCE = Mappers.getMapper(ItemConvert.class);

    Item convert(ItemDO item);
    default List<Item> convert(List<ItemDO> items) {
        return items.stream().map(this::convert).toList();
    }
}
