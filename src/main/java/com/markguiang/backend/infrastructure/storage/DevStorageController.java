package com.markguiang.backend.infrastructure.storage;

import java.io.IOException;
import java.net.URI;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
@Profile("dev")
public class DevStorageController {
  private final StorageService ss;

  public DevStorageController(StorageService ss) {
    this.ss = ss;
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @GetMapping("/presigned-url")
  public URI generatePresignedUrl() {
    return ss.generatePresignedUrl();
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @PostMapping("/object/{presignedUrl}")
  public URI uploadObject(MultipartFile object, @PathVariable URI presignedUrl) throws IOException {
    return ss.store(object, presignedUrl);
  }
}
