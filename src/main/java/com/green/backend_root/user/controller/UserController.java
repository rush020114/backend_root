package com.green.backend_root.user.controller;

import com.green.backend_root.user.dto.UserDTO;
import com.green.backend_root.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  //회원 정보 등록 API
  @PostMapping("")
  public ResponseEntity<?> insertUser(@RequestBody UserDTO userDTO){
    try {
      userService.insertUser(userDTO);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body("회원가입이 완료되었습니다.");
    } catch (Exception e){
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("회원가입 중 오류가 발생했습니다.");
    }
  }

  //회원 아이디 중복 확인 조회 API
  @GetMapping("/{userId}")
  public ResponseEntity<?> checkUserId(@PathVariable("userId") String userId){
    try {
      String result = userService.checkUserId(userId);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(result);
    } catch (Exception e){
       e.printStackTrace();
       return ResponseEntity
               .status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body("아이디 중복 확인 중 오류가 발생했습니다.");
    }
  }

  //회원 아이디와 비밀번호 로그인 API
  @PostMapping("/login")
  public ResponseEntity<?> selectUser(@RequestBody UserDTO userDTO){
    try {
      UserDTO loginUser = userService.selectUser(userDTO);
      if (loginUser == null){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("아이디 또는 비밀번호가 일치하지 않습니다.");
      }
      else {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginUser);

      }
    } catch (Exception e){
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("로그인 중 오류가 발생했습니다.");
    }
  }
}