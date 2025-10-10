package com.green.backend_root.counter;

import com.green.backend_root.counter.VisitorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// org.springframework.boot' version 3.4.9 : 버젼이 3이상의 경우 jakarta.servlet 을 사용
// import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class VisitorController {

  private final VisitorService visitorService;

  /**
   * 방문자 카운트 증가 API
   * POST /api/visitor/count
   * @param request HTTP 요청 객체 (IP 주소 추출용)
   * @return 오늘 방문자 수와 총 방문자 수
   */
  @PostMapping("/count")
  public Map<String, Long> countVisitor(HttpServletRequest request) {
    // 클라이언트 IP 주소 추출
    String ipAddress = getClientIpAddress(request);
    System.out.println(ipAddress);

    // 방문자 카운트 증가 및 결과 반환
    return visitorService.incrementVisitor(ipAddress);
  }

  /**
   * 현재 방문자 통계 조회 API
   * GET /api/visitor/stats
   * @return 오늘 방문자 수와 총 방문자 수
   */
  @GetMapping("/stats")
  public Map<String, Long> getVisitorStats() {
    Long todayCount = visitorService.getTodayVisitorCount();
    Long totalCount = visitorService.getTotalVisitorCount();

    return Map.of(
            "todayCount", todayCount,
            "totalCount", totalCount
    );
  }

  /**
   * 클라이언트의 실제 IP 주소 추출
   * 프록시나 로드 밸런서를 거칠 경우를 대비한 처리
   * @param request HTTP 요청 객체
   * @return 클라이언트 IP 주소
   */
  private String getClientIpAddress(HttpServletRequest request) {
    // X-Forwarded-For 헤더 확인 (프록시를 거친 경우)
    String ip = request.getHeader("X-Forwarded-For");

    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      // X-Real-IP 헤더 확인 (Nginx 등에서 사용)
      ip = request.getHeader("X-Real-IP");
    }

    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      // Proxy-Client-IP 헤더 확인
      ip = request.getHeader("Proxy-Client-IP");
    }

    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      // WL-Proxy-Client-IP 헤더 확인 (WebLogic)
      ip = request.getHeader("WL-Proxy-Client-IP");
    }

    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      // 직접 연결된 경우의 IP 주소
      ip = request.getRemoteAddr();
    }

    // X-Forwarded-For에 여러 IP가 있는 경우 첫 번째 IP 사용
    if (ip != null && ip.contains(",")) {
      ip = ip.split(",")[0].trim();
    }

    return ip;
  }
}
