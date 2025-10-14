package com.green.backend_root.user.controller;

import com.green.backend_root.user.dto.UserDTO;
import com.green.backend_root.user.service.UserService;
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

  // 회원 정보 등록 API
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

  // 회원 아이디 중복 확인 조회 API
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

  // 회원 아이디와 비밀번호 로그인 API
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

  // 회원 아이디 찾기 API
  @PostMapping("/findId")
  public ResponseEntity<?> findUserId(@RequestBody UserDTO userDTO){
    try {
      String findId = userService.findUserId(userDTO);

      if(findId == null){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("입력하신 정보와 일치하는 회원이 없습니다.");
      }

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(findId);

    } catch (Exception e){
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("아이디 찾기 중 오류가 발생했습니다.");
    }
  }

  // 회원 비밀번호 찾기 API
  @PostMapping("/findPw")
  public ResponseEntity<?> findUserPw(@RequestBody UserDTO userDTO){
    try {
      boolean result = userService.findUserPw(userDTO);

      if(result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("임시 비밀번호가 이메일로 발송되었습니다.");
      }
      else {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("입력하신 정보와 일치하는 회원이 없습니다.");
      }
    } catch (Exception e){
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("비밀번호 찾기 중 오류가 발생했습니다.");
    }
  }

  //회원 목록 조회 API
  @GetMapping("/userList")
  public List<UserDTO> getUserList() {
    return userService.getUserList();
  }

  //회원 삭제 API
  @DeleteMapping("/delete/{userId}")
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

  //회원명으로 검색
  @GetMapping("/userList/{userName}")
  public List<UserDTO> searchUserByName(@PathVariable("userName") String userName)
  {return userService.searchUserByName(userName);}


  // 최신 7일기준 고객(가입자)수 카운팅 API
  @GetMapping("/weekly")
  public int countWeeklySign(){
    return userService.countWeeklySign();
  }

  // 총 고객(가입자)수 카운팅 API
  @GetMapping("/total")
  public int countTotalSign(){
    return userService.countTotalSign();
  }

}