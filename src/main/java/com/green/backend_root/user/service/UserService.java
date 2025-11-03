package com.green.backend_root.user.service;

import com.green.backend_root.email.service.EmailService;
import com.green.backend_root.user.dto.UserDTO;
import com.green.backend_root.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;
  private final EmailService emailService;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  // 회원 정보 등록 기능 구현 메서드
  public void insertUser(UserDTO userDTO){
    // 비밀번호 암호화
    String encodePw = passwordEncoder.encode(userDTO.getUserPw());
    userDTO.setUserPw(encodePw);

    // 데이터베이스에 저장
    userMapper.insertUser(userDTO);
  }

  // 회원 아이디 중복 확인 조회 기능 구현 메서드
  public String checkUserId(String userId){
    return userMapper.checkUserId(userId);
  }

  // 회원 아이디와 비밀번호 로그인 기능 구현 메서드
  public UserDTO selectUser(UserDTO userDTO){
    // 아이디로 사용자 조회
    UserDTO user = userMapper.selectUserById(userDTO.getUserId());

    if (user == null){
      return null;
    }

    if (passwordEncoder.matches(userDTO.getUserPw(), user.getUserPw())){
      return user;
    }
    return null;
  }

  // 회원 아이디 찾기 기능 구현 메서드
  public String findUserId(UserDTO userDTO){
    return userMapper.findUserId(userDTO);
  }

  // 회원 비밀번호 찾기 기능 구현 메서드
  public boolean findUserPw(UserDTO userDTO) throws MessagingException {
    // 회원 확인
    String userId = userMapper.findUserPw(userDTO);

    if (userId == null) {
      return  false;
    }

    // 임시 비밀번호 생성
    String tempPw = getTempPw();

    // 암호화해서 데이터베이스에 저장
    String encodePw = passwordEncoder.encode(tempPw);
    userDTO.setUserPw(encodePw);
    userMapper.updateUserPw(userDTO);

    // 평문 임시 비밀번호를 이메일로 발송
    emailService.sendUserPw(userDTO.getUserEmail(), tempPw);

    return true;
  }

  // 회원 임시 비밀번호 생성 기능 구현 메서드
  private String getTempPw(){
    return RandomStringUtils.randomAlphanumeric(8);
  }
  
  //회원 목록 조회용 메서드
  public List<UserDTO> getUserList(){
    return userMapper.getUserList();
  }

  // 회원 삭제
  public void deleteUser(String userId) {
    userMapper.deleteUser(userId);
  }

  //회원명으로 검색
  public List<UserDTO> searchUserByName(String userName)
  {return userMapper.searchUserByName(userName);}


  // 최신 7일기준 고객(가입자)수 카운팅 메서드
  public int countWeeklySign(){
    return userMapper.countWeeklySign();
  }

  // 총 고객(가입자)수 카운팅 메서드
  public int countTotalSign(){
    return userMapper.countTotalSign();
  }

  // 회원 정보
  public UserDTO getUserInfo(String userId){
    return userMapper.getUserInfo(userId);
  }

  // 회원 정보 수정
  public void updateUserInfo(UserDTO userDTO){
    userMapper.updateUserInfo(userDTO);
  }
}
