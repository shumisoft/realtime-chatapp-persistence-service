package com.shumisoft.realtime_chatapp_persistence_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoom;
import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoomMember;
import com.shumisoft.realtime_chatapp_persistence_service.entity.User;
import com.shumisoft.realtime_chatapp_persistence_service.model.ChatRoomMembersId;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, ChatRoomMembersId> {

  boolean existsByChatRoomAndUser(ChatRoom chatRoom, User user);

  List<ChatRoomMember> findByChatRoom(ChatRoom chatRoom);

  ChatRoomMember findByChatRoomAndUser(ChatRoom chatRoom, User user);

  Page<ChatRoomMember> findByUser(User user, Pageable pageable);

}
