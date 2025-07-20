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
@Profile("!dev")
public class StorageController {
  private final StorageService ss;

  public StorageController(StorageService ss) {
    this.ss = ss;
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @GetMapping("/presigned-url/{eventId}")
  public URI generatePresignedUrlForUpload(@PathVariable UUID eventId) {
    return ss.generatePresignedUrlForUpload(eventId.toString());
  }

  @GetMapping("/presigned-url/{eventId}")
  public URI generatePresignedUrlForDownload(@PathVariable UUID eventId) {
    return ss.generatePresignedUrlForDownload(eventId.toString());
  }
}
