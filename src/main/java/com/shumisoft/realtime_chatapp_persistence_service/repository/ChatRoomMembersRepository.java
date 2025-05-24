package com.shumisoft.realtime_chatapp_persistence_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoom;
import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoomMembers;
import com.shumisoft.realtime_chatapp_persistence_service.entity.User;
import com.shumisoft.realtime_chatapp_persistence_service.model.ChatRoomMembersId;

public interface ChatRoomMembersRepository extends JpaRepository<ChatRoomMembers, ChatRoomMembersId> {

  boolean existsByChatRoomAndUser(ChatRoom chatRoom, User user);

  List<ChatRoomMembers> findByChatRoom(ChatRoom chatRoom);

  ChatRoomMembers findByChatRoomAndUser(ChatRoom chatRoom, User user);

  Page<ChatRoomMembers> findByUser(User user, Pageable pageable);

}
