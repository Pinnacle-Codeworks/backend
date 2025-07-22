package com.markguiang.backend.infrastructure.storage;

import com.markguiang.backend.infrastructure.storage.base.ObjectStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

  @Bean
  public StorageService storageService(ObjectStore objectStore) {
    return new StorageService(objectStore);
  }
}