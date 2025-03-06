package org.example.catservice.messaging;


import org.example.catservice.repositories.CatRepository;
import org.example.common.mapping.CatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OwnerLink {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @KafkaListener(topics = "request-catLinkOwnerTopic", groupId = "cat-service-group")
    public void listen(String key, Object data) {
        switch (key){
            case "findById":
                Long id = (Long) data;
                kafkaTemplate.send("response-catLinkOwnerTopic", CatMapper.toDto(Objects.requireNonNull(catRepository.findById(id).orElse(null))));
                break;
        }
    }
}
