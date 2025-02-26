package viosmash.bo.order;

import lombok.Data;
import lombok.experimental.Accessors;
import viosmash.bo.Item;
import viosmash.bo.UserDetails;
import viosmash.db.ItemDO;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderSimpleResp {
    private String id;
    private String status;
    private UserDetails userDetails;
    private Integer numItem;
    private Integer totalQuantity;
}
