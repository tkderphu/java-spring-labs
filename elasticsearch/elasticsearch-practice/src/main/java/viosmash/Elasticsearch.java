package viosmash;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.cat.CountRequest;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;

public class Elasticsearch {
    private final ElasticsearchClient client;

    public Elasticsearch() {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        this.client =  new ElasticsearchClient(transport);
    }

    public void createIndex(String index) throws IOException {
        boolean value = this.client.indices().exists(e -> e.index(index)).value();
        if(!value) {
            this.client.indices().create((req) -> {
                return req.index(index);
            });
        }
    }

    public void deleteDocument(String index, String id) throws IOException {
        String s = this.client.delete(new DeleteRequest.Builder().index(index).id(id).build()).result().jsonValue();
        System.out.println(s);
    }

    public void countDocument(String index) throws IOException {
        long count = this.client.count(builder -> {
              builder.index(index);
              return builder;
        }).count();

        System.out.println(count);
    }

    public void documentIsExistsInIndex(String index, String id) throws IOException {
        boolean value = this.client.exists((e) -> {
            e.index(index).id(id);
            return e;
        }).value();
        System.out.println(value);
    }

    public void updateDocument(String index, String id, Object object, Class clazz) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest.Builder<>()
                .index(index).id(id)
                .docAsUpsert(true)
                .doc(object)
                .build();

        String s = this.client.update(updateRequest, clazz).result().jsonValue();
        System.out.println(s);
    }

    public void search(String index) throws IOException {
        Query query = new Query.Builder()
                .term(term -> {
                    return term.field("username").value("change1");
                })
                .build();
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(index)
                .query(query)
                .build();

        HitsMetadata<User> hits = this.client.search(searchRequest, User.class).hits();
        System.out.println(hits.hits());
    }

    public void insertData(String index, Object obj) throws IOException {
        this.client.index(builder -> {
            builder.index(index).document(obj);
            return builder;
        });
    }

    public void getMapping(String index) throws IOException {
        GetMappingResponse mapping = this.client.indices().getMapping((e) -> {
            e.index(index);
            return e;
        });

        System.out.println(mapping);
    }

    public void getData(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder().index("my_index").build();

        SearchResponse<User> search = this.client.search(searchRequest, User.class);
        HitsMetadata<User> hits = search.hits();
        List<Hit<User>> hits1 = hits.hits();
        for(Hit<User> hit : hits1) {

        }
        System.out.println(search);
    }

    public void searchProduct() throws IOException {
        Query query = new Query.Builder()
                .bool(new BoolQuery.Builder()
                        .must(new Query.Builder()
                                .match(m -> {
                                     return m.field("name").query(FieldValue.of("ao the thao"));
                                })
                                .build(),
                                new Query.Builder()
                                        .range(m -> {
                                            return m.field("price").gt(JsonData.of(250_000)).lt(JsonData.of(600_000));
                                        })
                                        .build())

                        .build())
                .build();
        HitsMetadata<Product> indexProduct = this.client.search(new SearchRequest.Builder()
                .index("index_product")
                .query(query)
                .build(), Product.class).hits();
        System.out.println(indexProduct.hits());
    }

    public void aggregationPrice() throws IOException {


        HitsMetadata<Object> hits = this.client.search(new SearchRequest.Builder()
                        .index("index_product")
                .aggregations("avg_price", s -> {
                    return s.avg(v -> v.field("price"));
                })
                .build(), Object.class).hits();

        System.out.println(hits);
    }


    public void listIndex() {}
}
