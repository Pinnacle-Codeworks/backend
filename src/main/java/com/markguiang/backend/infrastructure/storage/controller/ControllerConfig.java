package com.markguiang.backend.infrastructure.storage.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.markguiang.backend.infrastructure.storage.StorageService;

@Configuration
public class ControllerConfig {

  @Bean
  @Profile("dev")
  public DevStorageController devStorageController(StorageService ss) {
    return new DevStorageController(ss);
  }

  @Bean
  @Profile("!dev")
  public StorageController prodStorageController(StorageService ss) {
    return new StorageController(ss);
  }
}
