package com.markguiang.backend.infrastructure.storage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import com.markguiang.backend.infrastructure.storage.StorageService;
import com.markguiang.backend.infrastructure.storage.base.StorageDetails;
import org.hibernate.validator.constraints.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
@Profile("dev")
public class DevStorageController {
  private final StorageService ss;

  public DevStorageController(StorageService ss) {
    this.ss = ss;
  }

  @PreAuthorize("permitAll()")
  @PutMapping("/presigned-url/upload/{key}")
  public StorageDetails generatePresignedUrlForUpload(@PathVariable("key") java.util.UUID id,
                                                      @RequestParam("fileType") String fileType,
                                                      @RequestParam("fileExtension") String fileExtension,
                                                      @RequestParam(name = "public", required = false, defaultValue = "true")  Boolean isPublic) throws IOException {
      return ss.generatePresignedUrlForUpload(id, fileType, fileExtension, isPublic);
  }

  @GetMapping("/presigned-url/{eventId}")
  public URL generatePresignedUrlForDownload(@PathVariable UUID eventId) throws IOException {
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
