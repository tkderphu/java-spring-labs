package viosmash;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ConnectToElastic implements CommandLineRunner {
    private final P p;

    public ConnectToElastic(P p) {
        this.p = p;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ConnectToElastic.class, args);

//        Elasticsearch elasticsearch = new Elasticsearch();
//        elasticsearch.createIndex("my_index");
//        User user = new User();
//        user.setUsername("tes");
//        user.setDob(new Date());
//        user.setPassword("tes");
//        elasticsearch.insertData("my_index", user);
//        elasticsearch.getData("my_index");

        Test test= new Test(
                List.of(
                        new User("3", "4", new Date(), new User.FullName("r", "r")),
                        new User("t", "t", new Date(), new User.FullName("t", "t"))

                ),
                "gehe"
        );

//        elasticsearch.createIndex("my_test");
//        elasticsearch.insertData("my_test", test);

//        elasticsearch.deleteDocument("my_index", "osrYeZUBwrzckwoVJr9d");

//        elasticsearch.countDocument("my_test");

//        elasticsearch.documentIsExistsInIndex("my_index", "pMrdeZUBwrzckwoVUr8C");

//        elasticsearch.updateDocument("my_index", "pMrdeZUBwrzckwoVUr8C", new User("change", "change", new Date(),null), User.class);

//        elasticsearch.createIndex("index_product");

//        Product product = new Product("Ao the thao", "Rat la dep", 500_000, "shirt", "adidas");
//        Product product1 = new Product("Liver pool ao the thao cuc pham mau do", "Rat la dep", 700_000, "shirt", "nike");
//        Product product2 = new Product("quan the thao", "Rat la dep", 300_000, "trousers", "adidas");
//
//
//        elasticsearch.insertData("index_product", product1);
//        elasticsearch.insertData("index_product", product);
//        elasticsearch.insertData("index_product", product2);


//        elasticsearch.searchProduct();

//        elasticsearch.aggregationPrice();
    }

    @Override
    public void run(String... args) throws Exception {
        p.push();
    }
}
