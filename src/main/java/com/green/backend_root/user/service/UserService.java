package com.green.backend_root.user.service;

import com.green.backend_root.user.dto.UserDTO;
import com.green.backend_root.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;

  //회원 정보 등록 기능 구현 메서드
  public int insertUser(UserDTO userDTO){
    return userMapper.insertUser(userDTO);
  }

  //회원 아이디 중복 확인 조회 기능 구현 메서드
  public String checkUserId(String userId){
    return userMapper.checkUserId(userId);
  }

  //회원 아이디와 비밀번호 로그인 기능 구현 메서드
  public UserDTO selectUser(UserDTO userDTO){
    return userMapper.selectUser(userDTO);
  }

}
