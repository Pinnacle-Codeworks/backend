package com.markguiang.backend.infrastructure.storage;

import com.fasterxml.uuid.Generators;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;

@Component
public class LocalObjectStore implements ObjectStore {
  private final Path storagePath = Paths.get("store").toAbsolutePath().normalize();

  public LocalObjectStore() throws IOException {
    Files.createDirectories(storagePath);
  }

  public URI store(InputStream inputStream) throws IOException {
    Path filename = Paths.get(Generators.timeBasedEpochGenerator().generate().toString());
    Path target = storagePath.resolve(filename);
    Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
    return target.toUri();
  }
}
