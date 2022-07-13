package com.nttdada.application.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PurchaseListener {
  @KafkaListener(topics = "topic-3", groupId = "group-3")
  public void listen(String messages) {

    log.info("Thread: {} Messages: {}", Thread.currentThread().getId(),"Id Transaction: " + messages);
  }
}
