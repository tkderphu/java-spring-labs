package viosmash;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/search")
public class Controller {

    private final ElasticsearchClient client = Elasticsearch.getClient(5, 5000, true);

    @GetMapping
    public List<Plane> search(@RequestParam("query") String query,
                              @RequestParam(value = "skip", required = false, defaultValue = "1") Integer skip,
                              @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                              @RequestParam(value = "year", required = false) Integer year) {
        Query elasQuery = new Query.Builder().bool(bool -> {
            BoolQuery.Builder boolQuery = bool.must(new Query.Builder().multiMatch((match) -> {
                return match.query(query).fields("title", "explanation");
            }).build());
            if (year != null) {
                boolQuery.filter(new Query.Builder()
                        .range(range -> {
                            return range.field("date")
                                    .gt(JsonData.of(String.format("%d-01-01", year)))
                                    .lte(JsonData.of(String.format("%d-12-31", year)))
                                    .format("yyyy-MM-dd");
                        })
                        .build());
            }
            return boolQuery;
        }).build();
        try {
            return client.search(new SearchRequest.Builder()
                            .index(Plane.class.getName().toLowerCase())
                            .query(elasQuery)
                            .from(skip)

                            .size(limit)
                            .build(), Plane.class)
                    .hits().hits().stream().map(Hit::source)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/count")
    public List<CountYearResp> countDocumentsByYear() {

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(Plane.class.getName().toLowerCase())
                .aggregations("docs_per_year", builder -> {
                    return builder.dateHistogram(builder1 ->  {
                        return builder1.calendarInterval(CalendarInterval.Year)
                                .format("yyyy")
                                .field("date");
                    });
                })
                .build();
        try {
            Map<String, Aggregate> aggregations =
                    client.search(searchRequest, Object.class).aggregations();
            List<DateHistogramBucket> docsPerYear = aggregations.get("docs_per_year").dateHistogram().buckets().array();
            return docsPerYear.stream().map(result -> {
                return new CountYearResp(Integer.parseInt(result.keyAsString()), (int) result.docCount());
            }).toList();
        } catch (Exception e) {
            return List.of();
        }
    }
}
