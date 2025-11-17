package com.shumisoft.realtime_chatapp_persistence_service.factory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;
import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoom;
import com.shumisoft.realtime_chatapp_persistence_service.entity.Message;
import com.shumisoft.realtime_chatapp_persistence_service.entity.User;
import com.shumisoft.realtime_chatapp_persistence_service.model.MessageStatus;
import com.shumisoft.realtime_chatapp_persistence_service.model.MessageType;

public class PersistenceDataFactory {
    public static final String DEFAULT_MESSAGE_ID = UUID.randomUUID().toString();
    public static final Long DEFAULT_CHAT_ROOM_ID = 1234L;
    public static final UUID DEFAULT_SENDER_ID = UUID.randomUUID();
    public static final String DEFAULT_CONTENT = "Hello, world! This is a test message.";
    public static final Timestamp DEFAULT_TIMESTAMP = Timestamp.from(Instant.now());

    public static ChatRoom createValidChatRoom() {
        ChatRoom room = new ChatRoom();
        room.setName("Test Room");
        return room;
    }

    public static User createValidUser() {
        User user = new User();
        user.setUserId(DEFAULT_SENDER_ID);
        return user;
    }

    public static Message createValidMessage(ChatRoom chatRoom) {
        return Message.builder()
                .messageId(DEFAULT_MESSAGE_ID)
                .chatRoom(chatRoom)
                .user(createValidUser())
                .content(DEFAULT_CONTENT)
                .timestamp(DEFAULT_TIMESTAMP)
                .type(MessageType.TEXT)
                .status(MessageStatus.SENT)
                .build();
    }

    public static MessageDTO createValidMessageDTO() {
        return MessageDTO.builder()
                .messageId(DEFAULT_MESSAGE_ID)
                .chatRoomId(DEFAULT_CHAT_ROOM_ID)
                .userId(DEFAULT_SENDER_ID)
                .content(DEFAULT_CONTENT)
                .timestamp(DEFAULT_TIMESTAMP)
                .type(MessageType.TEXT)
                .status(MessageStatus.SENT)
                .build();
    }

}
