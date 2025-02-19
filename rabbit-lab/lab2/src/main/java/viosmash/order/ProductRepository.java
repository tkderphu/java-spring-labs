package viosmash.order;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> products;

    public ProductRepository() {
        this.products = new ArrayList<>(List.of(new Product("liverpool", 50)));
    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateQuantity(String id, Integer quantity) {
        Product product = getProductById(id);
        product.setQuantity(product.getQuantity() + quantity);
    }

    public List<Product> getProduct() {
        return products;
    }
}
