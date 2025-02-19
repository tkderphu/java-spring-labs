package viosmash.order;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<Order> orders;

    public OrderRepository() {
        orders = new ArrayList<>();
    }

    public void save(Order order) {
        orders.add(order);
    }

    public void updateStatusOrder(String orderId, Order.OrderStatus orderStatus) {
        orders.forEach(order -> {
            if(order.getId().equals(orderId)) {
                order.setOrderStatus(orderStatus);
            }
        });
    }

    public List<Order> getAll() {
        return orders;
    }

}
