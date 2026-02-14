package com.shumisoft.realtime_chatapp_persistence_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final MessageService messageService;

    @KafkaListener(topics = "chat-messages", groupId = "persistence-group")
    public void consume(MessageDTO dto) {
        try {
            log.info(dto.toString());
            messageService.createMessage(dto, dto.getUserId());

            log.info("Message persisted: " + dto.getMessageId());
        } catch (Exception e) {
            // Handle DB errors (log or send to a Dead Letter Queue)
            log.error("Failed to persist message: " + e.getMessage());
        }
    }
}
