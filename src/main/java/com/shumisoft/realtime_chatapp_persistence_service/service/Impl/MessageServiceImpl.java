package com.shumisoft.realtime_chatapp_persistence_service.service.Impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;
import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoom;
import com.shumisoft.realtime_chatapp_persistence_service.entity.Message;
import com.shumisoft.realtime_chatapp_persistence_service.entity.User;
import com.shumisoft.realtime_chatapp_persistence_service.exception.BadRequestException;
import com.shumisoft.realtime_chatapp_persistence_service.exception.ResourceNotFoundException;
import com.shumisoft.realtime_chatapp_persistence_service.repository.ChatRoomMembersRepository;
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
    private final ChatRoomMembersRepository chatRoomMembersRepository;

    private void ensureMember(Long chatId, UUID requesterId) {
        ChatRoom room = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat room not found"));

        User user = userRepository.findById(requesterId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isMember = chatRoomMembersRepository.existsByChatRoomAndUser(room, user);

        if (!isMember)
            throw new BadRequestException("User is not part of this chat room.");
    }

    @Override
    public void createMessage(MessageDTO dto, UUID requesterId) throws EntityNotFoundException {
        ensureMember(dto.getChatRoomId(), requesterId);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("No user found with id: " + dto.getUserId()));

        ChatRoom chatRoom = chatRoomRepository.findById(dto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException("No chat room found with id: " + dto.getChatRoomId()));

        Message message = Message.builder().chatRoom(chatRoom).user(user).content(dto.getContent()).build();

        if (dto.getType() != null)
            message.setType(dto.getType());

        messageRepository.save(message);

    }

}
