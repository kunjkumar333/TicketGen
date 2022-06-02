package com.jmd.service.serviceimpl;

import com.jmd.model.Ticket;
import com.jmd.repository.TicketRepository;
import com.jmd.service.TicketService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository tRepo;

    private static final String TICKET_INDEX = "description";

    private ElasticsearchOperations elasticsearchOperations;


    @Override
    public void save(Ticket message) {
        tRepo.save(message);
    }

    @Override
    public String createTicketIndexOfElastic(Ticket ticket) {
        IndexQuery indexQuery = new IndexQueryBuilder().withId(ticket.getId().toString()).withObject(ticket).build();
        String documentId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of(TICKET_INDEX));

        return documentId;
    }

    public List<Ticket> processSearch(final String query) {

        // 1. Create query on phrase
        QueryBuilder queryBuilder =
                QueryBuilders
                        .matchPhraseQuery(query, "description")
                        .slop(1).boost(100.0f);


        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .build();

        // 2. Execute search
        SearchHits<Ticket> productHits =
                elasticsearchOperations
                        .search(searchQuery, Ticket.class,
                                IndexCoordinates.of(TICKET_INDEX));

        // 3. Map searchHits to product list
        List<Ticket> productMatches = new ArrayList<Ticket>();
        productHits.forEach(srchHit -> {
            productMatches.add(srchHit.getContent());
        });
        return productMatches;
    }

    public List<String> fetchSuggestions(String query) {
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("description", query + "*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<Ticket> searchSuggestions =
                elasticsearchOperations.search(searchQuery,
                        Ticket.class,
                        IndexCoordinates.of(TICKET_INDEX));

        List<String> suggestions = new ArrayList<String>();

        searchSuggestions.getSearchHits().forEach(searchHit -> {
            suggestions.add(searchHit.getContent().getDescription());
        });
        return suggestions;
    }


}
