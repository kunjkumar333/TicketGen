package com.jmd.service.serviceimpl;

import com.jmd.model.Ticket;
import com.jmd.repository.TicketElasticRepo;
import com.jmd.service.kafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KafkaServiceimpl implements kafkaService {

    @Autowired
    public KafkaTemplate<String, Ticket> kafkaTemp;

    @Autowired
    public TicketElasticRepo tERepo;

    String KafkaTopic = "Token_micro";

    // producing data into kafka
    @Override
    public void Produce(Ticket message) {
        kafkaTemp.send(KafkaTopic, message);
    }

    @Override
    public void saveToElastic(final Ticket user) {
        tERepo.save(user);
    }

    @Override
    public List<Ticket> getByText(String text) {
        List<Ticket> res = tERepo.findByDescriptionContaining(text);
        return res;
    }

}
