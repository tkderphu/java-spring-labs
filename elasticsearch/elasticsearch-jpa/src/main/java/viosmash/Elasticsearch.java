package viosmash;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.nodes.Transport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;

import static java.lang.Thread.sleep;

public class Elasticsearch {
    private static final Logger log = LoggerFactory.getLogger(Elasticsearch.class);
    private static ElasticsearchClient client = null;

    public static ElasticsearchClient getClient(int retry, int timeout, boolean isNgramTokenizer) {
        if(client != null) return client;
        while(retry > 0) {
            try {
                log.info("----->Try connect to elasticsearch instance<-----");
                RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
                RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
                client =  new ElasticsearchClient(transport);
                Index._createIndex(client, Plane.class.getName().toLowerCase(), isNgramTokenizer);
                break;
            } catch (Exception e) {
                try {
                    sleep(timeout);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            retry --;
        }
        if(client == null) {
            throw new RuntimeException("----->Can't connect to elasticsearch instance");
        }
        return client;
    }
}
