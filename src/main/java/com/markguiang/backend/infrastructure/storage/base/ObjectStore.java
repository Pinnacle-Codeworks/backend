package com.markguiang.backend.infrastructure.storage.base;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

public interface ObjectStore {
  public URL generatePresignedUrlForDownload(String key) throws IOException;

  public StorageDTO generatePresignedUrlForUpload(UUID id, String fileType, String fileExtension, boolean isPublic) throws IOException;
}
