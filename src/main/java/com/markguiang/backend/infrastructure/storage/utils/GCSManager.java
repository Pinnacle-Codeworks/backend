package com.markguiang.backend.infrastructure.storage.utils;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GCSManager {
    public static String generateV4PutObjectSignedUrl(GCSProperties properties, String path, String contentType) throws StorageException, IOException {

        Storage storage = StorageOptions.newBuilder().setProjectId(properties.getProjectId()).build().getService();

        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(properties.getBucket(), path)).build();

        Map<String, String> extensionHeaders = new HashMap<>();
        extensionHeaders.put("Content-Type", contentType);
        URL url = storage.signUrl(
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

        return url.toString();
    }
}
