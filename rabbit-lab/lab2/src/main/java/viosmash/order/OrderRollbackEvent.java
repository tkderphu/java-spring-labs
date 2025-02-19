package viosmash.order;

public class OrderRollbackEvent {
    private String orderId;
    private String productId;
    private Integer quantity;
    /**
     * 1: order
     * 2: product
     * 3: payment
     *
     * sequence: 1 -> 2 -> 3
     */
    private Integer stageRollback;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStageRollback() {
        return stageRollback;
    }

    public void setStageRollback(Integer stageRollback) {
        this.stageRollback = stageRollback;
    }
}
