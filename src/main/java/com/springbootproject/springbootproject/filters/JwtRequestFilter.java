package com.springbootproject.springbootproject.filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springbootproject.springbootproject.Utils.JwtUtil;
import com.springbootproject.springbootproject.model.UserInfo;
import com.springbootproject.springbootproject.model.entity.Token;
import com.springbootproject.springbootproject.service.TokenService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private TokenService verificationTokenService;

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("x-authorization");

		UserInfo user = new UserInfo();
		Token token = null;

		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Token ")) {
			String jwt = authorizationHeader.substring(6);
			user = jwtUtil.getUserFromToken(jwt);
			token = verificationTokenService.findByToken(jwt);
		}

		if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
			Set<GrantedAuthority> authorities = new HashSet<>();
			user.getAuthorities().forEach(p -> authorities.add(new SimpleGrantedAuthority((String) p)));
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
					authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			request.setAttribute("user_info", user);
		}

		filterChain.doFilter(request, response);
	}

}