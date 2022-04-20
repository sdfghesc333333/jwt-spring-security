package com.springbootproject.springbootproject.model;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserInfo implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4238740499756417941L;

	private Long userId;
	private String username;
	private String password;
	@SuppressWarnings("rawtypes")
	private Collection authorities;

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
