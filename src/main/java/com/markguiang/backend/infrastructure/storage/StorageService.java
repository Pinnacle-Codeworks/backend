package com.markguiang.backend.infrastructure.storage;

import com.markguiang.backend.infrastructure.storage.base.DirectObjectStore;
import com.markguiang.backend.infrastructure.storage.base.ObjectStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

import com.markguiang.backend.infrastructure.storage.base.StorageDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
  private final ObjectStore os;
  private final GCPObjectStore gcpObjectStore;

  public StorageService(ObjectStore os, GCPObjectStore gcpObjectStore) {
    this.os = os;
    this.gcpObjectStore = gcpObjectStore;
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

  public StorageDetails generatePresignedUrlForUpload(UUID id, String fileType, String fileExtension, Boolean isPublic) throws IOException {
    return gcpObjectStore.generatePresignedUrlForUpload(id, fileType, fileExtension, isPublic);
  }

  public URL generatePresignedUrlForDownload(String key) throws IOException {
    return gcpObjectStore.generatePresignedUrlForDownload(key);
  }

}
