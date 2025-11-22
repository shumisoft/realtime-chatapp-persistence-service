package com.shumisoft.realtime_chatapp_persistence_service.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shumisoft.realtime_chatapp_persistence_service.dto.MessageDTO;
import com.shumisoft.realtime_chatapp_persistence_service.factory.PersistenceDataFactory;
import com.shumisoft.realtime_chatapp_persistence_service.service.Impl.KafkaConsumerServiceImpl;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceImplTest {
    @Mock
    private MessageService messageService;

    @InjectMocks
    private KafkaConsumerServiceImpl kafkaConsumerService;

    private MessageDTO mockMessageDTO;

    @BeforeEach
    void setUp() {
        mockMessageDTO = PersistenceDataFactory.createValidMessageDTO();
    }

    @Test
    @DisplayName("Should consume DTO and pass to MessageService")
    void consume_Success() {
        // Act
        kafkaConsumerService.consume(mockMessageDTO);

        // Assert
        verify(messageService).createMessage(mockMessageDTO, mockMessageDTO.getUserId());
    }

    @Test
    @DisplayName("Should catch and suppress exceptions from MessageService")
    void consume_HandlesException() {
        // Arrange: Because createMessage returns void, we use doThrow
        doThrow(new RuntimeException("Database error"))
                .when(messageService).createMessage(any(MessageDTO.class), any());

        // Act & Assert
        // The test ensures that the exception is swallowed by the catch block and
        // doesn't crash the consumer
        assertDoesNotThrow(() -> kafkaConsumerService.consume(mockMessageDTO));

        verify(messageService).createMessage(mockMessageDTO, mockMessageDTO.getUserId());
    }
}