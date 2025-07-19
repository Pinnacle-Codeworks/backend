package com.markguiang.backend.infrastructure.storage;

import com.markguiang.backend.infrastructure.storage.base.DirectObjectStore;
import com.markguiang.backend.infrastructure.storage.base.ObjectStore;
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

  public URI store(InputStream is, URI presignedUrl) throws IOException {
    if (os instanceof DirectObjectStore dos) {
      return dos.store(is, presignedUrl);
    }
    throw new UnsupportedOperationException("direct-storage-not-supported-by-this-implementation");
  }

  public URI store(MultipartFile mf, URI presignedUrl) throws IOException {
    return this.store(mf.getInputStream(), presignedUrl);
  }

  public URI generatePresignedUrl() {
    return os.generatePresignedUrl();
  }
}
