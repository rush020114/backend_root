package com.green.backend_root.User.controller;

import com.green.backend_root.User.dto.UserDTO;
import com.green.backend_root.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  //회원 정보 등록 API
  @PostMapping("")
  public int insertUser(@RequestBody UserDTO userDTO){
    return userService.insertUser(userDTO);
  }

  //회원 아이디와 비밀번호 로그인 API
  @PostMapping("/login")
  public UserDTO selectUser(UserDTO userDTO){
    return userService.selectUser(userDTO);
  }
}
