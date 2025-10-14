package com.green.backend_root.user.mapper;

import com.green.backend_root.user.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;

@Mapper
public interface UserMapper {
  // 회원 정보 등록 쿼리 실행하는 메서드
  public int insertUser(UserDTO userDTO);

  // 회원 아이디 중복 확인 조회 쿼리 실행하는 메서드
  public String checkUserId(String userId);

  // 회원 아이디와 비밀번호 로그인 쿼리 실행하는 메서드
  public UserDTO selectUser(UserDTO userDTO);

  // 회원 아이디로만 사용자 조회 쿼리 실행하는 메서드
  public UserDTO selectUserById(String userId);

  // 회원 아이디 찾기 쿼리 실행하는 메서드
  public String findUserId(UserDTO userDTO);

  // 회원 비밀번호 찾기 쿼리 실행하는 메서드
  public String findUserPw(UserDTO userDTO);

  // 회원 임시 비밀번호 발급 쿼리 실행하는 메서드
  public int updateUserPw(UserDTO userDTO);

  //회원 목록 조회용 메서드
  public List<UserDTO> getUserList();

  // 회원 삭제
  public void deleteUser(String userId);

  //회원명으로 검색
  public List<UserDTO> searchUserByName(String userName);


  // 최신 7일기준 고객(가입자)수 카운팅
  public int countWeeklySign();

  // 총 고객(가입자)수 카운팅
  public int countTotalSign();


}

