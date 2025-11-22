package com.shumisoft.realtime_chatapp_persistence_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoom;
import com.shumisoft.realtime_chatapp_persistence_service.entity.Message;
import com.shumisoft.realtime_chatapp_persistence_service.entity.User;
import com.shumisoft.realtime_chatapp_persistence_service.factory.PersistenceDataFactory;
import com.shumisoft.realtime_chatapp_persistence_service.model.MessageStatus;
import com.shumisoft.realtime_chatapp_persistence_service.model.MessageType;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    private ChatRoom savedRoom;
    private User savedUser;

    @BeforeEach
    void setUp() {
        // 1. We MUST save the parent entities first to satisfy foreign key constraints
        // in H2
        ChatRoom room = PersistenceDataFactory.createValidChatRoom();
        savedRoom = chatRoomRepository.save(room);

        User user = PersistenceDataFactory.createValidUser();
        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("Should successfully persist a Message with all relationships and enums")
    void saveMessage_Success() {
        // Arrange
        Message message = PersistenceDataFactory.createValidMessage(savedRoom);
        message.setUser(savedUser); // Ensure it is linked to the exact user we just saved to the DB

        // Act
        Message savedMessage = messageRepository.save(message);

        // Assert
        assertNotNull(savedMessage);
        assertEquals(PersistenceDataFactory.DEFAULT_MESSAGE_ID, savedMessage.getMessageId());

        // Fetch it back from the DB to prove Hibernate actually mapped it correctly
        Optional<Message> retrievedMessageOpt = messageRepository.findById(savedMessage.getMessageId());
        assertTrue(retrievedMessageOpt.isPresent());

        Message retrievedMessage = retrievedMessageOpt.get();

        // Verify standard fields
        assertEquals(PersistenceDataFactory.DEFAULT_CONTENT, retrievedMessage.getContent());

        // Verify Enums mapped correctly (didn't crash as tinyint vs varchar)
        assertEquals(MessageType.TEXT, retrievedMessage.getType());
        assertEquals(MessageStatus.SENT, retrievedMessage.getStatus());

        // Verify Foreign Keys mapped correctly
        assertEquals(savedRoom.getChatId(), retrievedMessage.getChatRoom().getChatId());
        assertEquals(savedUser.getUserId(), retrievedMessage.getUser().getUserId());
    }

}
