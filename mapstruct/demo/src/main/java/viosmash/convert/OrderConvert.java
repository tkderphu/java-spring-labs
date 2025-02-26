package viosmash.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import viosmash.bo.order.OrderDetails;
import viosmash.bo.order.OrderSimpleResp;
import viosmash.db.OrderDO;

@Mapper
public interface OrderConvert {

    OrderConvert INSTANCE = Mappers.getMapper(OrderConvert.class);

    OrderSimpleResp convertSimple(OrderDO order);

    default OrderDetails convertDetail(OrderDO order) {
       return null;
    }

}
