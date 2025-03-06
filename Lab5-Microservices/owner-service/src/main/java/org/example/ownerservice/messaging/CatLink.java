package org.example.ownerservice.messaging;


import org.example.common.dto.CatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CatLink {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private CatDto catDto;

    public CatDto findById(Long id){
        kafkaTemplate.send("request-catLinkOwnerTopic", "findById", id);
        return catDto;
    }

    @KafkaListener(topics = "response-catLinkOwnerTopic", groupId = "owner-service-group")
    public void listen(CatDto response){
        if (response != null)
            this.catDto = response;
    }

}
