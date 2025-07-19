package com.markguiang.backend.infrastructure.storage.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public interface DirectObjectStore extends ObjectStore {
  public URI store(InputStream inputStream, URI presignedUri) throws IOException;
}
