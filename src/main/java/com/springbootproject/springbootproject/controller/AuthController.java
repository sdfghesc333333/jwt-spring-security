//package com.springbootproject.springbootproject.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.springbootproject.springbootproject.Utils.JwtUtil;
//import com.springbootproject.springbootproject.model.UserInfo;
//import com.springbootproject.springbootproject.model.entity.Token;
//import com.springbootproject.springbootproject.model.entity.UserEntity;
//import com.springbootproject.springbootproject.service.TokenService;
//import com.springbootproject.springbootproject.service.UserService;
//
//@RestController
//public class AuthController {
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private TokenService tokenService;
//
//	@Autowired
//	private JwtUtil jwtUtil;
//
//	@PostMapping("/register")
//	public UserEntity register(@RequestBody UserEntity userEntity) {
//		userEntity.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
//		
//		
//		userEntity.setState(1);
//		return userService.createUser(userEntity);
//	}
//
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody UserEntity userEntity) {
//		UserInfo userPrincipal = userService.findByUserName(userEntity.getUserName());
//		if (null == userEntity
//				|| !new BCryptPasswordEncoder().matches(userEntity.getPassword(), userPrincipal.getPassword())) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
//		}
//		Token token = new Token();
//		token.setToken(jwtUtil.generateToken(userPrincipal));
//		token.setTokenExpDate(jwtUtil.generateExpirationDate());
//		token.setCreatedBy(userPrincipal.getUserId());
//		tokenService.createToken(token);
//		return ResponseEntity.ok(token.getToken());
//	}
//
//	@GetMapping("/hello")
//	@PreAuthorize("hasAnyAuthority('USER_READ')")
//	public ResponseEntity<String> hello(HttpServletRequest request) {
//
//		System.out.println("==" + request.getUserPrincipal());
//
//		UserInfo userEntity = (UserInfo) request.getAttribute("user_info");
//
//		System.out.println(userEntity);
//
//		return ResponseEntity.ok("hello");
//	}
//}