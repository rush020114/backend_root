package com.green.backend_root.user.service;

import com.green.backend_root.email.service.EmailService;
import com.green.backend_root.user.dto.UserDTO;
import com.green.backend_root.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
  public boolean findUserPw(UserDTO userDTO) {
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

  // 마이그레이션용 (일회성) 기존 비밀번호 암호화 후 삭제 예정
//  public void migratePasswords() {
//    System.out.println("=== 비밀번호 마이그레이션 시작 ===");
//
//    List<UserDTO> users = userMapper.selectAllUsers();
//    System.out.println("총 " + users.size() + "명의 사용자 발견");
//
//    int count = 0;
//    for (UserDTO user : users) {
//      // 이미 암호화된 비밀번호는 건너뛰기
//      if (user.getUserPw().startsWith("$2a$")) {
//        System.out.println(user.getUserId() + " - 이미 암호화됨 (건너뜀)");
//        continue;
//      }
//
//      // 평문 비밀번호 암호화
//      String encodedPw = passwordEncoder.encode(user.getUserPw());
//
//      // DB 업데이트 (새로운 메서드 사용)
//      userMapper.updatePassword(user.getUserId(), encodedPw);
//
//      count++;
//      System.out.println(user.getUserId() + " - 암호화 완료");
//    }
//
//    System.out.println("=== 마이그레이션 완료: " + count + "명 처리됨 ===");
//  }
}
