package com.markguiang.backend.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
  private final ObjectStore os;

  public StorageService(ObjectStore os) {
    this.os = os;
  }

  public URI store(InputStream is) throws IOException {
    return os.store(is);
  }

  public URI store(MultipartFile mf) throws IOException {
    return os.store(mf.getInputStream());
  }
}
