package com.markguiang.backend.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.hibernate.validator.constraints.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  @GetMapping("/presigned-url/{eventId}")
  public URI generatePresignedUrlForUpload(@PathVariable UUID eventId) {
    return ss.generatePresignedUrlForUpload(eventId.toString());
  }

  @GetMapping("/presigned-url/{eventId}")
  public URI generatePresignedUrlForDownload(@PathVariable UUID eventId) {
    return ss.generatePresignedUrlForDownload(eventId.toString());
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @PostMapping("/object")
  public URI uploadObject(MultipartFile object, @RequestParam URI presignedUrl) throws IOException {
    return ss.store(object, presignedUrl);
  }

  @GetMapping("/object")
  public ResponseEntity<Resource> download(@RequestParam URI presignedUrl) throws IOException {
    InputStream inputStream = ss.fetch(presignedUrl);
    InputStreamResource resource = new InputStreamResource(inputStream);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }
}
