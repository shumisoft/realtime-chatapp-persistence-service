package com.shumisoft.realtime_chatapp_persistence_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shumisoft.realtime_chatapp_persistence_service.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
