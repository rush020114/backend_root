package com.green.backend_root.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class VisitorService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Redis Key 상수 정의
    private static final String TOTAL_VISITOR_KEY = "visitor:total";  // 총 방문자 수
    private static final String DAILY_VISITOR_PREFIX = "visitor:daily:";  // 오늘 방문자 수 (날짜별)
    private static final String VISITOR_IP_PREFIX = "visitor:ip:";  // IP 중복 체크용

    /**
     * 방문자 카운트 증가 (IP 기반 중복 방지)
     * @param ipAddress 방문자 IP 주소
     * @return 오늘 방문자 수와 총 방문자 수를 담은 Map
     */
public Map<String, Long> incrementVisitor(String ipAddress) {
  // 오늘 날짜를 문자열로 생성 (예: 2025-10-01)
  String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
  String dailyKey = DAILY_VISITOR_PREFIX + today;  // "visitor:daily:2025-10-01"
  String ipKey = VISITOR_IP_PREFIX + today + ":" + ipAddress;  // "visitor:ip:2025-10-01:192.168.0.1"

  // IP 중복 체크: 오늘 이미 방문한 IP인지 확인
  Boolean isNewVisitor = redisTemplate.opsForValue().setIfAbsent(ipKey, "1", 24, TimeUnit.HOURS);
  // setIfAbsent: 키가 없으면 저장하고 true 반환, 이미 있으면 false 반환
  // 24시간 후 자동 삭제 (자정이 지나면 다시 카운트 가능)

  System.out.println("접속 ip : " + ipAddress);
  System.out.println("중복 여부 : " + isNewVisitor);


  if (Boolean.TRUE.equals(isNewVisitor)) {
    // 새로운 방문자인 경우에만 카운트 증가

    // 1. 오늘 방문자 수 증가 (자정에 리셋)
    redisTemplate.opsForValue().increment(dailyKey, 1);
    // 오늘 자정까지 유지되도록 만료 시간 설정
    redisTemplate.expireAt(dailyKey,
            LocalDate.now().plusDays(1).atStartOfDay()
                    .atZone(java.time.ZoneId.systemDefault()).toInstant());

    // 2. 총 방문자 수 증가 (영구 저장)
    redisTemplate.opsForValue().increment(TOTAL_VISITOR_KEY, 1);
  }

  // 현재 카운트 조회
  Long todayCount = getTodayVisitorCount();
  Long totalCount = getTotalVisitorCount();

  // 결과 반환
  Map<String, Long> result = new HashMap<>();
  result.put("todayCount", todayCount);
  result.put("totalCount", totalCount);
  return result;
}

/**
 * 오늘 방문자 수 조회
 * @return 오늘 방문자 수
 */
public Long getTodayVisitorCount() {
  String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
  String dailyKey = DAILY_VISITOR_PREFIX + today;

  // Redis에서 값을 가져와 Long으로 변환 (값이 없으면 0 반환)
  Object value = redisTemplate.opsForValue().get(dailyKey);
  return value != null ? Long.parseLong(value.toString()) : 0L;
}

/**
 * 총 방문자 수 조회
 * @return 총 방문자 수
 */
public Long getTotalVisitorCount() {
  Object value = redisTemplate.opsForValue().get(TOTAL_VISITOR_KEY);
  return value != null ? Long.parseLong(value.toString()) : 0L;
}
}