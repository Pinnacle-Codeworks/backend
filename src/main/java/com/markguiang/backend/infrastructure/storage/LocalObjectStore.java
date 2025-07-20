package com.markguiang.backend.infrastructure.storage;

import com.markguiang.backend.infrastructure.storage.base.DirectObjectStore;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class LocalObjectStore implements DirectObjectStore {
  private final Path storagePath = Paths.get("store").toAbsolutePath().normalize();

  public LocalObjectStore() throws IOException {
    Files.createDirectories(storagePath);
  }

  public URI store(InputStream inputStream, URI presignedUrl) throws IOException {
    Path filename = Paths.get(presignedUrl.toString());
    Path target = storagePath.resolve(filename);
    Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
    return target.toUri();
  }

  public InputStream fetch(URI presignedUrl) throws IOException {
    Path filename = Paths.get(presignedUrl.toString());
    Path source = storagePath.resolve(filename);
    return Files.newInputStream(source);
  }

  public URI generatePresignedUrlForDownload(String key) {
    return URI.create(key);
  }

  public URI generatePresignedUrlForUpload(String key) {
    return URI.create(key);
  }
}