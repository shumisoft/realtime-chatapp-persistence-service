package com.shumisoft.realtime_chatapp_persistence_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shumisoft.realtime_chatapp_persistence_service.entity.ChatRoom;
import com.shumisoft.realtime_chatapp_persistence_service.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatRoom(ChatRoom chatRoom);

}
