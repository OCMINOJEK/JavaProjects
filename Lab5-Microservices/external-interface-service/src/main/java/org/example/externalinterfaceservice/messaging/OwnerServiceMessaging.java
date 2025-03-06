package org.example.externalinterfaceservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.common.dto.CatDto;
import org.example.common.dto.OwnerCreateDto;
import org.example.common.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceMessaging {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void addCatToOwner(Long ownerId, Long catId) {
        kafkaTemplate.send("request-ownerTopic", "addCatToOwner", new Long[]{ownerId, catId});
    }

    public void removeCatFromOwner(Long ownerId, Long catId) {
        kafkaTemplate.send("request-ownerTopic", "removeCatFromOwner", new Long[]{ownerId, catId});
    }


}
