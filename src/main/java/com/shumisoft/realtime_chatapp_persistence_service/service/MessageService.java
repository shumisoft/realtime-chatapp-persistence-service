package com.shumisoft.realtime_chatapp_persistence_service.service;

import java.util.UUID;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;

public interface MessageService {

    public void createMessage(MessageDTO dto, UUID requesterId);

}
