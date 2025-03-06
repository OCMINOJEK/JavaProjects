package org.example.externalinterfaceservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.common.dto.CatCreateDto;
import org.example.common.dto.CatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class CatServiceMessaging {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void addFriendToCat(Long catId, Long friendId) {
        kafkaTemplate.send("request-catTopic", "addFriendToCat", new Long[]{catId, friendId});
    }

    public void removeFriendFromCat(Long catId, Long friendId) {
        kafkaTemplate.send("request-catTopic", "removeFriendFromCat", new Long[]{catId, friendId});
    }
}
