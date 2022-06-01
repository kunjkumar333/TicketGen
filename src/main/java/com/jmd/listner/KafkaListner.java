package com.jmd.listner;

import com.jmd.model.Ticket;
import com.jmd.service.TicketService;
import com.jmd.service.kafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListner {
	
//	@Autowired
//	private kafkaService kService;

	@Autowired
	private TicketService tService;
	
	//Consuming data from kafka
		@KafkaListener(topics = "Token_micro", groupId = "group_id")
		public void consume(Ticket message) {
         tService.createTicketIndexOfElastic(message);
		}

}
