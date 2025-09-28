package com.markguiang.backend.infrastructure.storage;

import com.markguiang.backend.infrastructure.storage.base.DirectObjectStore;
import com.markguiang.backend.infrastructure.storage.base.ObjectStore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;

import com.markguiang.backend.infrastructure.storage.base.StorageDTO;
import com.markguiang.backend.infrastructure.storage.base.StoreProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.markguiang.backend.event.utils.Utils.concatenateStr;

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

  public InputStream fetch(URI presignedUrl) throws IOException {
    if (os instanceof DirectObjectStore dos) {
      return dos.fetch(presignedUrl);
    }
    throw new UnsupportedOperationException("direct-storage-not-supported-by-this-implementation");
  }

  public StorageDTO generatePresignedUrlForUpload(UUID id, String fileType, String fileExtension, Boolean isPublic) throws IOException {
    return os.generatePresignedUrlForUpload(id, fileType, fileExtension, isPublic);
  }

  public URL generatePresignedUrlForDownload(String key) throws IOException {
    return os.generatePresignedUrlForDownload(key);
  }

}
