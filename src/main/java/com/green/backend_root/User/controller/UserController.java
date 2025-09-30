package com.green.backend_root.User.controller;

import com.green.backend_root.User.dto.UserDTO;
import com.green.backend_root.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  //회원 아이디 중복 확인 조회 API
  @GetMapping("/{userId}")
  public String checkUserId(@PathVariable("userId") String userId){
    return userService.checkUserId(userId);
  }

  //회원 목록 조회용 메서드
  @GetMapping("/userList")
  public List<UserDTO> getUserList() {
    return userService.getUserList();
  }

  // 회원 삭제 API
  @DeleteMapping("/{userId}")
  public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
    try {
      userService.deleteUser(userId);
      return ResponseEntity.ok("삭제 완료");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("삭제 중 오류 발생");
    }
  }
}