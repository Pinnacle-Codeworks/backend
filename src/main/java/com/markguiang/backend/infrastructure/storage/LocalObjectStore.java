package com.markguiang.backend.infrastructure.storage;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.markguiang.backend.infrastructure.storage.base.DirectObjectStore;
import com.markguiang.backend.infrastructure.storage.base.StorageDTO;
import com.markguiang.backend.infrastructure.storage.base.StoreProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.markguiang.backend.event.utils.Utils.concatenateStr;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
public class LocalObjectStore implements DirectObjectStore {
  private final Path storagePath = Paths.get("store").toAbsolutePath().normalize();

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

  public URL generatePresignedUrlForDownload(String key) throws IOException {
    return generateSignedUrl(key);
  }

  public StorageDTO generatePresignedUrlForUpload( UUID id, String fileType, String fileExtension, boolean isPublic) throws IOException {
      String filename = RandomStringUtils.randomAlphabetic(20) + "." + fileExtension;

      String filePath = concatenateStr(BUCKET_PATH_EVENT, id.toString(), SUFFIX, filename);

      URL signedUrl = generateV4PutObjectSignedUrl(initProperties(isPublic), filePath, fileType);

      return new StorageDTO(filePath, signedUrl);
    }


    private URL generateV4PutObjectSignedUrl(StoreProperties properties, String path, String contentType) throws IOException {

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