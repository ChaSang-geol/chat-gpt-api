package com.openai.chatgpt.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.chatgpt.dto.ChatRequest;
import com.openai.chatgpt.dto.ChatResponse;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ChatController {

  @Qualifier("openaiRestTemplate")
  @Autowired
  private RestTemplate restTemplate;

  @Value("${openai.model}")
  private String model;

  @Value("${openai.api.url}")
  private String apiUrl;

  @Value("${openai.api.key}")
  private String openaiApiKey;

  @PostMapping("/chat")
  public String chat(@RequestBody String prompt) throws IOException {
    if (prompt != null) {
      // create a request
      ChatRequest request = new ChatRequest(model, prompt);
      log.info("## prompt : " + request.getModel());
      try {
        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
          return "No response";
        }
        log.debug("## ChatResponse : ", response);
        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
      } catch (Exception e) {
        // handle exception
        log.error("## API 호출 에러 : " + prompt, e);
        // return response.
        return "API response Error";
      }

    } else {
      return "No Parameter";
    }
  }

  @PostMapping("/chatgpt")
  public String chatGpt(@RequestBody String prompt) throws IOException {

    // OkHttpClient client = new OkHttpClient();
    // ObjectMapper objectMapper = new ObjectMapper();
    // try {
    // ChatRequest chatrequest = new ChatRequest(model, prompt);
    // MediaType mediaType = MediaType.parse("application/json");
    // // JSONObject requestJson = (JSONObject) chatrequest;
    // RequestBody body = RequestBody.create ();
    // Request request = new Request.Builder()
    // .url(apiUrl)
    // .post(body)
    // .addHeader("Accept", "*/*")
    // .addHeader("Authorization", "Bearer " + openaiApiKey)
    // .addHeader("Content-Type", "application/json")
    // .build();

    // Response response = client.newCall(request).execute();
    // // Response형 -> String형
    // String chatString = response.body().string();

    // // String형 ->json형
    // // JSONParser parser = new JSONParser();
    // // Object obj = parser.parse(chatString);
    // // JSONObject responsJson = (JSONObject) obj;

    // return chatString;

    // } catch (Exception e) {
    // // handle exception
    // log.error("## API 호출 에러 : " + prompt, e);
    // return "API response Error";
    // }
    return "No Message";
  }

  @PostMapping("/chattest")
  public JSONObject chatTest(@RequestBody String prompt) throws IOException, Exception {

    String messageString = "{  \"id\": \"chatcmpl-123\",     \"object\": \"chat.completion\",     \"created\": 1677652288,     \"choices\": [{       \"index\": 0,       \"message\": {         \"role\": \"assistant\",         \"content\": \"\n\nHello there, how may I assist you today?\"       },       \"finish_reason\": \"stop\"     }],     \"usage\": {       \"prompt_tokens\": 9,       \"completion_tokens\": 12,       \"total_tokens\": 21     }   }";
    // String형 ->json형
    JSONParser parser = new JSONParser();
    Object obj = new Object();
    try {
      obj = parser.parse(messageString);
    } catch (ParseException e) {

      log.error(messageString, e);
    }
    JSONObject responsJson = (JSONObject) obj;

    return responsJson;

  }
}
