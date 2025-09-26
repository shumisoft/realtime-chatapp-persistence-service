package com.shumisoft.realtime_chatapp_persistence_service.service;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;

public interface KafkaConsumerService {

  void consume(MessageDTO dto);

}