package com.green.backend_root.user.mapper;

import com.green.backend_root.user.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
  //회원 정보 등록 쿼리 실행하는 메서드
  public int insertUser(UserDTO userDTO);

  //회원 아이디 중복 확인 조회 쿼리 실행하는 메서드
  public String checkUserId(String userId);

  //회원 아이디와 비밀번호 로그인 쿼리 실행하는 메서드
  public UserDTO selectUser(UserDTO userDTO);

  //회원 목록 조회용 메서드
  public List<UserDTO> getUserList();

  // 회원 삭제
  void deleteUser(String userId);
}
