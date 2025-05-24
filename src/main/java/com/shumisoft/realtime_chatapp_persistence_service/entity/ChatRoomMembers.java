package com.shumisoft.realtime_chatapp_persistence_service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.shumisoft.realtime_chatapp_persistence_service.model.ChatRoomMembersId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomMembers {

  @EmbeddedId
  private ChatRoomMembersId chatRoomMembersId;

  @ManyToOne
  @JoinColumn(name = "chatId", insertable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ChatRoom chatRoom;

  @ManyToOne
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private User user;

  @Builder.Default
  @Column(nullable = false)
  private boolean admin = false;

}
