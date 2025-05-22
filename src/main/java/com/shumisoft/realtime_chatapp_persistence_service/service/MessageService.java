package com.shumisoft.realtime_chatapp_persistence_service.service;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;

public interface MessageService {

    public void createMessage(MessageDTO dto);

}
