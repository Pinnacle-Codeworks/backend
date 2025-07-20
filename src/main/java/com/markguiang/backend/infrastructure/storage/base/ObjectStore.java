package com.markguiang.backend.infrastructure.storage.base;

import java.net.URI;

public interface ObjectStore {
  public URI generatePresignedUrlForDownload(String key);

  public URI generatePresignedUrlForUpload(String key);
}
