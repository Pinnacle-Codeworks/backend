package com.markguiang.backend.infrastructure.storage.controller;

import com.markguiang.backend.infrastructure.storage.GCSService;
import com.markguiang.backend.infrastructure.storage.StorageService;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/storage")
@PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
@Profile("!dev")
public class StorageController {
  private final StorageService ss;
  private final GCSService gcsService;

  public StorageController(StorageService ss, GCSService gcsService) {
    this.ss = ss;
    this.gcsService = gcsService;
  }

  @GetMapping("/upload/presigned-url/upload/{key}")
  public URI generatePresignedUrlForUpload(@PathVariable UUID key) {
    return ss.generatePresignedUrlForUpload(key.toString());
  }

  @GetMapping("/download/presigned-url/dowload/{key}")
  public URI generatePresignedUrlForDownload(@PathVariable UUID key) {
    return ss.generatePresignedUrlForDownload(key.toString());
  }

  @PutMapping("/presigned-url/{id}")
  public ResponseEntity<Object> generatePresignedUrlGCS(@PathVariable("id") java.util.UUID id,
                                                        @RequestParam("fileType") String fileType,
                                                        @RequestParam("fileExtension") String fileExtension,
                                                        @RequestParam(name = "public", required = false, defaultValue = "true")  Boolean isPublic) throws IOException {
    return new ResponseEntity<>(gcsService.generatePresignedUrl(id, fileType, fileExtension, isPublic), HttpStatus.OK);
  }

  @GetMapping("/getSignedURL")
  public ResponseEntity<Object> generateSignedURL(@RequestParam("path") String path) throws IOException {
    return  new ResponseEntity<>(gcsService.generateSignedUrl(path), HttpStatus.OK);
  }
}