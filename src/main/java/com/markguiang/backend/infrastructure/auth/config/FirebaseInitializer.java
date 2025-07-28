package com.markguiang.backend.infrastructure.auth.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FirebaseInitializer {
  @Value("${SERVICE_KEY_JSON}")
  private String serviceKey;

  @PostConstruct
  public void init() {
    try {
      // unsafe, put in file
      InputStream serviceAccount = new ByteArrayInputStream(serviceKey.getBytes(StandardCharsets.UTF_8));

      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .build();

      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
