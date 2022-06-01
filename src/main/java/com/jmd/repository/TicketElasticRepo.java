package com.jmd.repository;

import java.util.List;

import com.jmd.model.Ticket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketElasticRepo extends ElasticsearchRepository<Ticket, Long> {

	List<Ticket> findByDescriptionContaining(String text);

//	@Query("{\"bool\": {\"must\": {\"match_phrase\": {\"Ticket.description\": \"?0\"}},  }}}")
//	List<Ticket> findByDescription(String text);
}