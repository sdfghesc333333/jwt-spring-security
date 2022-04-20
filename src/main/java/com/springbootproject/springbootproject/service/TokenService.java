package com.springbootproject.springbootproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootproject.springbootproject.model.entity.Token;
import com.springbootproject.springbootproject.repository.TokenRepository;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	public Token createToken(Token token) {
		return tokenRepository.saveAndFlush(token);
	}

	public Token findByToken(String token) {
		return tokenRepository.findByToken(token);
	}
}