package com.openai.chatgpt.api2.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotRequest {

  private String model;
  private List<Message> messages;
  private int n;
  private double temperature;
  private int max_tokens;
}