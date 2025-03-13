package viosmash;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("Loading data.....");
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("D:\\WorkSpaceD\\project\\java-spring-labs\\elasticsearch\\elasticsearch-jpa\\apod_raw.json");
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Plane.class);

            List<Object> documents =new ArrayList<>( mapper.readValue(file, collectionType));
//            System.out.println(documents);
            Index.indexData(Elasticsearch.getClient(5, 5000, true), Plane.class.getName().toLowerCase(), documents, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(App.class, args);
    }
}
