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

import com.markguiang.backend.infrastructure.storage.base.StoreProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.markguiang.backend.event.utils.Utils.concatenateStr;

@Service
public class StorageService {
  private final ObjectStore os;

  @Value("${GCP_PROJECTID}")
  private String projectId;

  @Value("${GCP_BUCKET_PUBLIC}")
  private String publicBucket;

  @Value("${GCP_BUCKET_PRIVATE}")
  private String privateBucket;

  @Value("${GOOGLE_CLOUD_STORAGE_JSON}")
  private String sa;

  private static final String SUFFIX = "/";
  private static final String BUCKET_PATH_EVENT = "event/";

  private static final String BUCKET_PATH_USER = "users/";

  private StoreProperties initProperties(Boolean isPublic) {
    return StoreProperties
            .builder()
            .projectId(projectId)
            .bucket(isPublic ? publicBucket : privateBucket)
            .sa(sa)
            .build();
  }

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

  public URL generatePresignedUrlForUpload(String key) {
    return os.generatePresignedUrlForUpload(key);
  }

  public URI generatePresignedUrlForDownload(String key) {
    return os.generatePresignedUrlForDownload(key);
  }

  public URL generateV4PutObjectSignedUrl(StoreProperties properties, String path, String contentType) throws IOException {

    Storage storage = StorageOptions.newBuilder().setProjectId(properties.getProjectId()).build().getService();

    BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(properties.getBucket(), path)).build();

    Map<String, String> extensionHeaders = new HashMap<>();
    extensionHeaders.put("Content-Type", contentType);

      return storage.signUrl(
            blobInfo,
            15,
            TimeUnit.MINUTES,
            Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
            Storage.SignUrlOption.withExtHeaders(extensionHeaders),
            Storage.SignUrlOption.withV4Signature(),
            Storage.SignUrlOption.signWith(
                    ServiceAccountCredentials
                            .fromStream(new ByteArrayInputStream(properties.getSa().getBytes(StandardCharsets.UTF_8)))
            )
    );
  }

  public Map<String, URL> generatePresignedUrl(UUID id, String fileType, String fileExtension, boolean isPublic) throws IOException {
    String filePath;
    String filename = RandomStringUtils.randomAlphabetic(20) + "." + fileExtension;

    filePath = concatenateStr( BUCKET_PATH_EVENT, id.toString(), SUFFIX, filename);

    HashMap<String, URL> map = new HashMap<>();

    map.put(filePath, generateV4PutObjectSignedUrl(initProperties(isPublic), filePath, fileType));

    return map;
  }

  public URL generateSignedUrl(String objectPath) throws IOException {
    Storage storage = StorageOptions.newBuilder()
            .setProjectId(projectId)
            .setCredentials(
                    ServiceAccountCredentials.fromStream(
                            new ByteArrayInputStream(sa.getBytes(StandardCharsets.UTF_8)) // since sa is a JSON string
                    )
            )
            .build()
            .getService();

    BlobInfo blobInfo = BlobInfo.newBuilder(privateBucket, objectPath).build();

    return storage.signUrl(
            blobInfo,
            15,
            TimeUnit.MINUTES,
            Storage.SignUrlOption.withV4Signature()
    );
  }
}
