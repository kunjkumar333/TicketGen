package com.jmd.service;

import com.jmd.model.Ticket;

import java.util.List;

public interface TicketService {

    public void save(Ticket message);

    public String createTicketIndexOfElastic(Ticket ticket);

    public List<Ticket> processSearch(final String query);
    public List<String> fetchSuggestions(String query);


}
