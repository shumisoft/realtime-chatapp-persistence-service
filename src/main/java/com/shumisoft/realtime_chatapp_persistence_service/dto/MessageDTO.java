package com.shumisoft.realtime_chatapp_persistence_service.dto;

import java.sql.Timestamp;

import com.shumisoft.realtime_chatapp_persistence_service.entity.Message;
import com.shumisoft.realtime_chatapp_persistence_service.model.MessageStatus;
import com.shumisoft.realtime_chatapp_persistence_service.model.MessageType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private Long messageId;
    @NotNull
    private Long chatRoomId;
    @NotNull
    private Long userId;
    @NotEmpty
    private String content;
    private Timestamp timestamp;
    private MessageStatus status = MessageStatus.UNREAD;
    private MessageType type = MessageType.TEXT;

    // Static method to convert entity to DTO
    public static MessageDTO fromEntity(Message entity) {
        return new MessageDTO(entity.getMessageId(), entity.getChatRoom().getChatId(), entity.getUser().getUserId(),
                entity.getContent(), entity.getTimestamp(), entity.getStatus(), entity.getType());
    }

    // Method to convert DTO to entity
    public Message toEntity() {
        return new Message(this.messageId, null, null, this.content, this.timestamp, this.status, this.type);
    }

}
