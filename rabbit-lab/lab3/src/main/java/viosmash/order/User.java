package viosmash.order;

import org.springframework.stereotype.Component;

@Component
public class User {
    private  String id = "quangphu";
    private  Integer amount = 500_000;

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }
}
