package com.shumisoft.realtime_chatapp_persistence_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;
import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoom;
import com.shumisoft.realtime_chatapp_persistence_service.entity.Message;
import com.shumisoft.realtime_chatapp_persistence_service.entity.User;
import com.shumisoft.realtime_chatapp_persistence_service.exception.BadRequestException;
import com.shumisoft.realtime_chatapp_persistence_service.exception.ResourceNotFoundException;
import com.shumisoft.realtime_chatapp_persistence_service.factory.PersistenceDataFactory;
import com.shumisoft.realtime_chatapp_persistence_service.repository.ChatRoomMemberRepository;
import com.shumisoft.realtime_chatapp_persistence_service.repository.ChatRoomRepository;
import com.shumisoft.realtime_chatapp_persistence_service.repository.MessageRepository;
import com.shumisoft.realtime_chatapp_persistence_service.repository.UserRepository;
import com.shumisoft.realtime_chatapp_persistence_service.service.Impl.MessageServiceImpl;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ChatRoomMemberRepository chatRoomMembersRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    private MessageDTO mockMessageDTO;
    private ChatRoom mockChatRoom;
    private User mockUser;

    @BeforeEach
    void setUp() {
        mockMessageDTO = PersistenceDataFactory.createValidMessageDTO();
        mockChatRoom = PersistenceDataFactory.createValidChatRoom();
        mockUser = PersistenceDataFactory.createValidUser();
    }

    @Test
    @DisplayName("Should successfully validate membership, build entity, and save")
    void createMessage_Success() {
        // Arrange
        when(chatRoomRepository.findById(PersistenceDataFactory.DEFAULT_CHAT_ROOM_ID))
                .thenReturn(Optional.of(mockChatRoom));
        when(userRepository.findById(PersistenceDataFactory.DEFAULT_SENDER_ID))
                .thenReturn(Optional.of(mockUser));
        when(chatRoomMembersRepository.existsByChatRoomAndUser(mockChatRoom, mockUser))
                .thenReturn(true);

        // Act
        messageService.createMessage(mockMessageDTO, PersistenceDataFactory.DEFAULT_SENDER_ID);

        // Assert
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException if chat room is missing")
    void createMessage_RoomNotFound() {
        // Arrange
        when(chatRoomRepository.findById(PersistenceDataFactory.DEFAULT_CHAT_ROOM_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> messageService.createMessage(mockMessageDTO, PersistenceDataFactory.DEFAULT_SENDER_ID));

        assertEquals("Chat room not found", ex.getMessage());
        verify(messageRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException if user is missing")
    void createMessage_UserNotFound() {
        // Arrange
        when(chatRoomRepository.findById(PersistenceDataFactory.DEFAULT_CHAT_ROOM_ID))
                .thenReturn(Optional.of(mockChatRoom));
        when(userRepository.findById(PersistenceDataFactory.DEFAULT_SENDER_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> messageService.createMessage(mockMessageDTO, PersistenceDataFactory.DEFAULT_SENDER_ID));

        assertEquals("User not found", ex.getMessage());
        verify(messageRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw BadRequestException if user is not a member of the room")
    void createMessage_NotAMember() {
        // Arrange
        when(chatRoomRepository.findById(PersistenceDataFactory.DEFAULT_CHAT_ROOM_ID))
                .thenReturn(Optional.of(mockChatRoom));
        when(userRepository.findById(PersistenceDataFactory.DEFAULT_SENDER_ID))
                .thenReturn(Optional.of(mockUser));
        when(chatRoomMembersRepository.existsByChatRoomAndUser(mockChatRoom, mockUser))
                .thenReturn(false); // User is NOT a member

        // Act & Assert
        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> messageService.createMessage(mockMessageDTO, PersistenceDataFactory.DEFAULT_SENDER_ID));

        assertEquals("User is not part of this chat room.", ex.getMessage());
        verify(messageRepository, never()).save(any());
    }

}
