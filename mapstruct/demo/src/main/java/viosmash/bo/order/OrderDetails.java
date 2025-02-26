package viosmash.bo.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import viosmash.bo.Item;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDetails extends OrderSimpleResp{
    private List<Item> items;
}
