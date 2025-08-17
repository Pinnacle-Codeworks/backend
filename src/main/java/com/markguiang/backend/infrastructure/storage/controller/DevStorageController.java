package com.markguiang.backend.infrastructure.storage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.Principal;

import com.markguiang.backend.infrastructure.storage.GCSService;
import com.markguiang.backend.infrastructure.storage.StorageService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
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
  private final GCSService gcsService;

  public DevStorageController(StorageService ss, GCSService gcsService) {
    this.ss = ss;
    this.gcsService = gcsService;
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @GetMapping("/upload/presigned-url/{eventId}")
  public URI generatePresignedUrlForUpload(@PathVariable UUID eventId) {
    return ss.generatePresignedUrlForUpload(eventId.toString());
  }

  @GetMapping("/download/presigned-url/{eventId}")
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
