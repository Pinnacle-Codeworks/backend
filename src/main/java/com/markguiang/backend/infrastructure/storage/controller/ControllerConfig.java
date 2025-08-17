package com.markguiang.backend.infrastructure.storage.controller;

import com.markguiang.backend.infrastructure.storage.GCSService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.markguiang.backend.infrastructure.storage.StorageService;

@Configuration
public class ControllerConfig {

  @Bean
  @Profile("dev")
  public DevStorageController devStorageController(StorageService ss, GCSService gcsService) {
    return new DevStorageController(ss, gcsService);
  }

  @Bean
  @Profile("!dev")
  public StorageController prodStorageController(StorageService ss, GCSService gcsService) {
    return new StorageController(ss, gcsService);
  }
}
