package org.example.catservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.catservice.services.CatService;
import org.example.common.dto.CatCreateDto;
import org.example.common.dto.CatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatMessageListener {

    @Autowired
    private CatService catService;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "request-catTopic", groupId = "myGroup")
    public void receiveMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        String key = record.key();
        Object data = record.value();
        System.out.println("key: " + key + " data: " + data);
        switch (key) {
            case "getAllCats":
                List<CatDto> cats = catService.getAllCats();
                kafkaTemplate.send("response-catTopic-list", cats);
                break;
            case "getAllCatsByOwnerId":
                List<CatDto> OwnerCats = catService.getAllCatsByOwnerId(Long.parseLong((String) data));
                kafkaTemplate.send("response-catTopic-list", OwnerCats);
                break;
            case "getCatById":
                CatDto cat = catService.getCatById(Long.parseLong((String) data));
                kafkaTemplate.send("response-catTopic", cat);
                break;
            case "createCat":
                CatCreateDto newCat = objectMapper.readValue(data.toString(), CatCreateDto.class);
                kafkaTemplate.send("response-catTopic", newCat);
                break;
            case "addFriendToCat":
                Long[] friendData = objectMapper.readValue(data.toString(), Long[].class);
                catService.addFriendToCat(friendData[0], friendData[1]);
                break;
            case "removeFriendFromCat":
                Long[] removeFriendData = objectMapper.readValue(data.toString(), Long[].class);
                catService.removeFriendFromCat(removeFriendData[0], removeFriendData[1]);
                break;
        }
    }
}
