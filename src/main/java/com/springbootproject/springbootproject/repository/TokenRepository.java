package com.springbootproject.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootproject.springbootproject.model.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByToken(String token);
}
