package com.openai.chatgpt.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
// import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.openai.chatgpt.api2.interceptor.RequestResponseLoggingInterceptor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class OpenAIRestTemplateConfig {

  @Value("${openai.api.key}")
  private String openaiApiKey;

  @Bean
  @Qualifier("openaiRestTemplate")
  public RestTemplate openaiRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    log.info("## API_KRY : " + openaiApiKey);
    // restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    restTemplate.getInterceptors().add((request, body, execution) -> {
      request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
      return execution.execute(request, body);
    });

    log.info("## restTemplate : " + restTemplate.toString());
    restTemplate.getInterceptors().add(new RequestResponseLoggingInterceptor());
    restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    return restTemplate;
  }
}
