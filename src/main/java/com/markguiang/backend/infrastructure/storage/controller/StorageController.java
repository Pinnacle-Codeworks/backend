package com.markguiang.backend.infrastructure.storage.controller;

import com.markguiang.backend.infrastructure.storage.StorageService;
import java.net.URI;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/storage")
@PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
@Profile("!dev")
public class StorageController {
  private final StorageService ss;

  public StorageController(StorageService ss) {
    this.ss = ss;
  }

  @GetMapping("/presigned-url/upload/{key}")
  public URI generatePresignedUrlForUpload(@PathVariable UUID key) {
    return ss.generatePresignedUrlForUpload(key.toString());
  }

  @GetMapping("/presigned-url/dowload/{key}")
  public URI generatePresignedUrlForDownload(@PathVariable UUID key) {
    return ss.generatePresignedUrlForDownload(key.toString());
  }
}
