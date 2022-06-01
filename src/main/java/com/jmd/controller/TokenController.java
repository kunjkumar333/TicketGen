package com.jmd.controller;


import com.jmd.model.Ticket;
import com.jmd.service.TicketService;
import com.jmd.service.kafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	private TicketService tService;

	@Autowired
	public kafkaService kService;

	@PostMapping("/createtoken")
	public String create(@RequestBody Ticket user) {
		tService.save(user);
		kService.Produce(user);
		return "saved";
	}

	@GetMapping("/getByText/{text}")
	public List<Ticket> getDesByText(@RequestParam("text") String text) {
		List<Ticket> result= tService.processSearch(text);
		return result;
	}

	@GetMapping("/suggestions/{text}")
	public List<String> fetchSuggestions(@RequestParam("text") String  query) {
		List<String> suggests = tService.fetchSuggestions(query);
		return suggests;
	}

}
