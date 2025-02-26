package viosmash.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Item {
    private String name;
    private Integer quantity;
}
