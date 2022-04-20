package com.springbootproject.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootproject.springbootproject.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUserName(String userName);
}