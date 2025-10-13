package com.green.backend_root.plantBot;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ChatMessage {
  private String type; // "user" or "bot"
  private String content;
  private String imageUrl;
  private LocalDateTime timestamp;

  public ChatMessage() {}

  public ChatMessage(String type, String content) {
    this.type = type;
    this.content = content;
    this.timestamp = LocalDateTime.now();
  }
}

@Getter
@Setter
class MessageRequest {
  private String content;

  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
}

@Getter
@Setter
class PlantResult {
  private String scientificName;
  private String commonName;
  private String family;
  private int confidence;

  public PlantResult() {}

  public PlantResult(String scientificName, String commonName, String family, int confidence) {
    this.scientificName = scientificName;
    this.commonName = commonName;
    this.family = family;
    this.confidence = confidence;
  }
}