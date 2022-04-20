package com.springbootproject.springbootproject.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootproject.springbootproject.model.UserInfo;
import com.springbootproject.springbootproject.model.entity.UserEntity;
import com.springbootproject.springbootproject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserEntity createUser(UserEntity userEntity) {
		return userRepository.saveAndFlush(userEntity);
	}

	public UserInfo findByUserName(String userName) {
		UserEntity userEntity = userRepository.findByUserName(userName);
		UserInfo userPrincipal = new UserInfo();
		if (null != userEntity) {
			Set<String> authorities = new HashSet<>();
			if (null != userEntity.getRoles())
				userEntity.getRoles().forEach(r -> {
					authorities.add(r.getRoleKey());
					r.getPermissions().forEach(p -> authorities.add(p.getPermissionKey()));
				});

			userPrincipal.setUserId(userEntity.getId());
			userPrincipal.setUsername(userEntity.getUserName());
			userPrincipal.setPassword(userEntity.getPassword());
			userPrincipal.setAuthorities(authorities);
		}
		return userPrincipal;
	}

}