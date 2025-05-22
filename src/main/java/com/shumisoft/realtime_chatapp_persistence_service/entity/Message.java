package com.shumisoft.realtime_chatapp_persistence_service.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.shumisoft.realtime_chatapp_persistence_service.model.MessageStatus;
import com.shumisoft.realtime_chatapp_persistence_service.model.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "chatId")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp timestamp;

    @Enumerated(value = EnumType.STRING)
    private MessageStatus status = MessageStatus.UNREAD;

    @Enumerated(value = EnumType.STRING)
    private MessageType type = MessageType.TEXT;

}
