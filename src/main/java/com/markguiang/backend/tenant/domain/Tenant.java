package com.markguiang.backend.tenant.domain;

import com.markguiang.backend.base.model.Entity;
import java.util.UUID;

public class Tenant extends Entity {
  public static Tenant register(String name) {
      return new Tenant(name);
  }

  private final String name;

  private Tenant(String name) {
    this(null, name);
  }

  private Tenant(UUID id, String name) {
    super(id);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}