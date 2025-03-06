package org.example.ownerservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.common.dto.OwnerCreateDto;
import org.example.common.dto.OwnerDto;
import org.example.common.mapping.OwnerMapper;
import org.example.ownerservice.repositories.OwnerRepository;
import org.example.ownerservice.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OwnerMessageListener {

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    @KafkaListener(topics = "request-ownerTopic", groupId = "myGroup")
    public void receiveMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        String key = record.key();
        Object data = record.value();
        switch (key) {
            case "createOwner":
                OwnerCreateDto ownerCreateDto = objectMapper.readValue(data.toString(), OwnerCreateDto.class);
                OwnerDto newOwner = ownerService.createOwner(ownerCreateDto);
                kafkaTemplate.send("response-ownerTopic", newOwner);
                break;
            case "getOwnerById":
                Long id = objectMapper.readValue(data.toString(), Long.class);
                OwnerDto owner = ownerService.getOwnerById(id);
                kafkaTemplate.send("response-ownerTopic", owner);
                break;
            case "getAllOwners":
                List<OwnerDto> owners = ownerService.getAllOwners();
                kafkaTemplate.send("response-ownerTopic-list", owners);
                break;
            case "addCatToOwner":
                Long[] addCatToOwner = objectMapper.readValue(data.toString(), Long[].class);
                ownerService.addCatToOwner(addCatToOwner[0], addCatToOwner[1]);
                break;
            case "removeCatFromOwner":
                Long[] removeCatFromOwner = objectMapper.readValue(data.toString(), Long[].class);
                ownerService.removeCatFromOwner(removeCatFromOwner[0], removeCatFromOwner[1]);
                break;
            case "findByIdOwner":
                Long ownerId = objectMapper.readValue(data.toString(), Long.class);
                OwnerDto ownerDto = OwnerMapper.toDto(Objects.requireNonNull(ownerRepository.findById(ownerId).orElse(null)));
                kafkaTemplate.send("response-ownerTopic", ownerDto);
                break;
        }
    }
}
