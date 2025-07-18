package com.markguiang.backend.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public interface ObjectStore {

  public URI store(InputStream inputStream) throws IOException;
}
