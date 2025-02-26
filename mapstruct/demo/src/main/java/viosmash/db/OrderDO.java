package viosmash.db;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDO {
    private String id;
    private List<ItemDO> itemDOS;
    private String status;
    private UserDO customer;
}
