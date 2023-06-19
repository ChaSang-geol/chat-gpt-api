package com.openai.chatgpt.api2.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.openai.chatgpt.api2.dto.BotRequest;
import com.openai.chatgpt.api2.dto.BotResponse;
import com.openai.chatgpt.api2.dto.Message;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v2")
public class BotController {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${openai.model}")
  private String model;

  @Value("${openai.max-completions}")
  private int maxCompletions;

  @Value("${openai.temperature}")
  private double temperature;

  @Value("${openai.max_tokens}")
  private int maxTokens;

  @Value("${openai.api.url}")
  private String apiUrl;

  @PostMapping("/chat")
  public BotResponse chat(@RequestParam("prompt") String prompt) {

    BotRequest request = new BotRequest(model,
        List.of(new Message("user", prompt)),
        maxCompletions,
        temperature,
        maxTokens);

    BotResponse response = restTemplate.postForObject(apiUrl, request, BotResponse.class);
    return response;
  }
}