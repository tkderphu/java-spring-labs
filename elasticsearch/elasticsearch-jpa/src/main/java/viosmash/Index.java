package viosmash;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.analysis.Analyzer;
import co.elastic.clients.elasticsearch._types.analysis.TokenChar;
import co.elastic.clients.elasticsearch._types.analysis.Tokenizer;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
    public static  void _createIndex(ElasticsearchClient es, String indexName,final boolean isNgramTokenizer) {
        try {
            es.indices().delete(req -> {
                return req.index(indexName).ignoreUnavailable(true);
            });
            es.indices().create(req ->{
                CreateIndexRequest.Builder index = req.index(indexName);
                if(isNgramTokenizer) {
                    index.settings(builder -> {
                        return builder.analysis(builder1 -> {
                            Map<String, Tokenizer> map = new HashMap<>();
                            map.put("n_gram_tokenizer", new Tokenizer.Builder()
                                            .definition(builder2 -> {
                                                return builder2.edgeNgram(builder3 -> {
                                                    return builder3.minGram(1).maxGram(3)
                                                            .tokenChars(TokenChar.Digit, TokenChar.Letter);
                                                });
                                            })
                                    .build());
                            Map<String, Analyzer> analyzerMap = new HashMap<>();
                            analyzerMap.put("default", new Analyzer.Builder()
                                            .custom(builder2 -> {
                                                return builder2.tokenizer("n_gram_tokenizer");
                                            })
                                    .build());
                            return builder1.tokenizer(map).analyzer(analyzerMap);
                        });
                    });
                }
                return index;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void _insert_documents(ElasticsearchClient es, String indexName,  List<Object> documents) {
        try {
            List<BulkOperation> list = new ArrayList<>();
            for(int i = 0; i < documents.size(); i++) {
                Object document = documents.get(i);
                BulkOperation bulkOperation  = BulkOperation.of(builder -> {
                    return builder.index(index -> index.index(indexName).document(document));
                });
                list.add(bulkOperation);
            }
            es.bulk(req -> {
                return req.operations(list);
            });
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    public static void indexData(ElasticsearchClient es, String indexName, List<Object> documents, boolean isNgramTokenizer) {
        _createIndex(es, indexName, isNgramTokenizer);
        _insert_documents(es, indexName, documents);
    }
}
