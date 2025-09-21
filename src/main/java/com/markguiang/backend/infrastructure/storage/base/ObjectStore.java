package com.markguiang.backend.infrastructure.storage.base;

import java.net.URI;
import java.net.URL;

public interface ObjectStore {
  public URI generatePresignedUrlForDownload(String key);

  public URL generatePresignedUrlForUpload(String key);
}
