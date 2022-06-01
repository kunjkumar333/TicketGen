package com.jmd.service;


import com.jmd.model.Ticket;

import java.util.List;

public interface kafkaService {

	public void Produce(Ticket user);
	
	public void saveToElastic(Ticket user);
	
	public List<Ticket> getByText(String text);
}
