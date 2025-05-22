package com.shumisoft.realtime_chatapp_persistence_service.service.Impl;

import org.springframework.stereotype.Service;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;
import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoom;
import com.shumisoft.realtime_chatapp_persistence_service.entity.Message;
import com.shumisoft.realtime_chatapp_persistence_service.entity.User;
import com.shumisoft.realtime_chatapp_persistence_service.repository.ChatRoomRepository;
import com.shumisoft.realtime_chatapp_persistence_service.repository.MessageRepository;
import com.shumisoft.realtime_chatapp_persistence_service.repository.UserRepository;
import com.shumisoft.realtime_chatapp_persistence_service.service.MessageService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    @Override
    public void createMessage(MessageDTO dto) throws EntityNotFoundException {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("No user found with id: " + dto.getUserId()));

        ChatRoom chatRoom = chatRoomRepository.findById(dto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException("No chat room found with id: " + dto.getChatRoomId()));

        Message message = new Message();

        message.setChatRoom(chatRoom);
        message.setUser(user);
        message.setContent(dto.getContent());

        if (dto.getType() != null)
            message.setType(dto.getType());

        messageRepository.save(message);

    }

}
