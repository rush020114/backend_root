package com.green.backend_root.user.service;

import com.green.backend_root.email.service.EmailService;
import com.green.backend_root.user.dto.UserDTO;
import com.green.backend_root.user.dto.UserImgDTO;
import com.green.backend_root.user.mapper.UserMapper;
import com.green.backend_root.util.FileUploadUtil;
import com.green.backend_root.util.UploadPath;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.mail.MessagingException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

  // 회원 이미지 등록
  @Transactional(rollbackFor = Exception.class)
  public boolean regUserImg(MultipartFile userImg, UserImgDTO userImgDTO){
    if(userImg == null || userImg.isEmpty()){
      throw new IllegalArgumentException("이미지 파일이 없습니다.");
    }

    // 이미지 등록 여부 확인
    UserDTO userDTO = userMapper.getRegedImg(userImgDTO.getUserId());
    System.out.println("========== userDTO: " + userDTO);  // ← 추가

    UserImgDTO existingImg = userDTO.getUserImgDTO();
    System.out.println("========== existingImg: " + existingImg);  // ← 추가

    boolean isRegedImg = (existingImg != null && existingImg.getAttachedImgName() != null);
    System.out.println("========== 이미지 등록 여부: " + isRegedImg);  // ← 이미 있음

    // 이미지가 등록되어 있다면 기존 파일 삭제
    if(isRegedImg){
      List<String> attachedFileNames = new ArrayList<>();
      attachedFileNames.add(existingImg.getAttachedImgName());
      FileUploadUtil.deleteFiles(attachedFileNames, UploadPath.USER);
    }

    // 새 이미지 업로드
    String[] uploadUserImg = FileUploadUtil.uploadFile(userImg, UploadPath.USER);
    userImgDTO.setOriginImgName(uploadUserImg[0]);
    userImgDTO.setAttachedImgName(uploadUserImg[1]);


    System.out.println("========== DTO 세팅 완료: " + userImgDTO);  // ← 로그 추가

    if(isRegedImg){
      userMapper.updateUserImg(userImgDTO);
      System.out.println("========== UPDATE 실행");  // ← 로그 추가
    } else {
      userMapper.regUserImg(userImgDTO);
      System.out.println("========== INSERT 실행");  // ← 로그 추가
    }
    return isRegedImg;
  }
}
