package com.green.backend_root.plantBot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlantService {

  @Value("${plantnet.api.key:${2b10yRhD7K5AxdZgMH4dxTKGO}}")
  private String apiKey;

  private final String baseUrl = "https://my-api.plantnet.org/v2";
  private final String project = "the-plant-list";

  private final WebClient webClient;
  private final ObjectMapper objectMapper;

  public PlantService() {
    this.webClient = WebClient.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
            .build();
    this.objectMapper = new ObjectMapper();
  }

  public List<PlantResult> identifyPlant(MultipartFile imageFile) {
    try {
      System.out.println("API 키: " + apiKey); // 디버깅용

      // API 키 검증
      if (!isApiKeyValid()) {
        throw new RuntimeException("PlantNet API 키가 설정되지 않았습니다.");
      }

      // 파일 크기 검증 (5MB 제한)
      if (imageFile.getSize() > 5 * 1024 * 1024) {
        throw new RuntimeException("이미지 파일 크기는 5MB를 초과할 수 없습니다.");
      }

      // API 호출을 위한 멀티파트 데이터 구성
      MultipartBodyBuilder builder = new MultipartBodyBuilder();

      // 이미지 파일 추가
      builder.part("images", new ByteArrayResource(imageFile.getBytes()) {
        @Override
        public String getFilename() {
          return imageFile.getOriginalFilename();
        }
      }, MediaType.parseMediaType(imageFile.getContentType()));

      // organs 파라미터 추가 (필수)
      builder.part("organs", "auto");

      // API 호출
      String response = webClient.post()
              .uri(baseUrl + "/identify/" + project + "?api-key=" + apiKey)
              .contentType(MediaType.MULTIPART_FORM_DATA)
              .body(BodyInserters.fromMultipartData(builder.build()))
              .retrieve()
              .onStatus(
                      status -> status.is4xxClientError(),
                      clientResponse -> clientResponse.bodyToMono(String.class)
                              .map(body -> new RuntimeException("API 클라이언트 오류: " + body))
              )
              .onStatus(
                      status -> status.is5xxServerError(),
                      clientResponse -> clientResponse.bodyToMono(String.class)
                              .map(body -> new RuntimeException("API 서버 오류: " + body))
              )
              .bodyToMono(String.class)
              .block();

      return parseResponse(response);

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("식물 인식 실패: " + e.getMessage());
    }
  }

  private List<PlantResult> parseResponse(String response) {
    List<PlantResult> results = new ArrayList<>();

    try {
      JsonNode rootNode = objectMapper.readTree(response);
      JsonNode resultsArray = rootNode.get("results");

      if (resultsArray != null && resultsArray.isArray()) {
        int count = Math.min(resultsArray.size(), 3); // 상위 3개만

        for (int i = 0; i < count; i++) {
          JsonNode result = resultsArray.get(i);
          JsonNode species = result.get("species");

          if (species != null) {
            String scientificName = getTextValue(species, "scientificNameWithoutAuthor", "정보 없음");
            String commonName = "정보 없음";

            // 공통 이름 처리
            JsonNode commonNames = species.get("commonNames");
            if (commonNames != null && commonNames.isArray() && commonNames.size() > 0) {
              commonName = commonNames.get(0).asText("정보 없음");
            }

            // 과(Family) 정보 처리
            String family = "정보 없음";
            JsonNode familyNode = species.get("family");
            if (familyNode != null) {
              family = getTextValue(familyNode, "scientificNameWithoutAuthor", "정보 없음");
            }

            // 신뢰도 처리
            JsonNode scoreNode = result.get("score");
            int confidence = 0;
            if (scoreNode != null) {
              confidence = (int) (scoreNode.asDouble(0.0) * 100);
            }

            results.add(new PlantResult(scientificName, commonName, family, confidence));
          }
        }
      }
    } catch (Exception e) {
      System.err.println("응답 파싱 오류: " + e.getMessage());
      e.printStackTrace();
    }

    return results;
  }

  private String getTextValue(JsonNode node, String fieldName, String defaultValue) {
    if (node != null && node.has(fieldName)) {
      JsonNode fieldNode = node.get(fieldName);
      if (fieldNode != null && !fieldNode.isNull()) {
        return fieldNode.asText(defaultValue);
      }
    }
    return defaultValue;
  }

  public boolean isApiKeyValid() {
    return apiKey != null && !apiKey.trim().isEmpty();
  }
}