package viosmash.db;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ItemDO {
    private String name;

    private Integer quantity;
}
