package com.markguiang.backend.infrastructure.storage.controller;

import com.markguiang.backend.infrastructure.storage.StorageService;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import com.markguiang.backend.infrastructure.storage.base.StorageDetails;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage")
@PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
@Profile("!dev")
public class StorageController {
  private final StorageService ss;

  public StorageController(StorageService ss) {
    this.ss = ss;
  }

  @PutMapping("/presigned-url/upload/{key}")
  public StorageDetails generatePresignedUrlForUpload(@PathVariable("key") java.util.UUID id,
                                                      @RequestParam("fileType") String fileType,
                                                      @RequestParam("fileExtension") String fileExtension,
                                                      @RequestParam(name = "public", required = false, defaultValue = "true")  Boolean isPublic) throws IOException {
      return ss.generatePresignedUrlForUpload(id, fileType, fileExtension, isPublic);
  }

  @GetMapping("/presigned-url/dowload/{key}")
  public URL generatePresignedUrlForDownload(@PathVariable UUID key) throws IOException {
    return ss.generatePresignedUrlForDownload(key.toString());
  }
}
