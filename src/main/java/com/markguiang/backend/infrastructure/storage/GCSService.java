package com.markguiang.backend.infrastructure.storage;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.markguiang.backend.infrastructure.storage.utils.GCSManager;
import com.markguiang.backend.infrastructure.storage.utils.GCSProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.markguiang.backend.utils.Utils.concatenateStr;

@Service
public class GCSService {

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

    private GCSProperties initPublicGCSProperties() {
        return GCSProperties
                .builder()
                .projectId(projectId)
                .bucket(publicBucket)
                .sa(sa)
                .build();
    }

    private GCSProperties initPrivateGCSProperties() {
        return GCSProperties
                .builder()
                .projectId(projectId)
                .bucket(privateBucket)
                .sa(sa)
                .build();
    }

    public Map<String, String> generatePresignedUrl(UUID id, String fileType, String fileExtension, boolean isPublic) throws IOException {
        String filePath;
        String filename = RandomStringUtils.randomAlphabetic(20) + "." + fileExtension;

        filePath = concatenateStr( BUCKET_PATH_EVENT, id.toString(), SUFFIX, filename);

        HashMap<String, String> map = new HashMap<>();

        if (isPublic) {
            map.put(filePath, GCSManager.generateV4PutObjectSignedUrl(initPublicGCSProperties(), filePath, fileType));
        } else {
            map.put(filePath, GCSManager.generateV4PutObjectSignedUrl(initPrivateGCSProperties(), filePath, fileType));
        }

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
                SignUrlOption.withV4Signature()
        );
    }
}
