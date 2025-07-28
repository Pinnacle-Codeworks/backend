package com.markguiang.backend.role.domain;

import java.util.List;

public enum Role {
  PARTICIPANT(List.of(Authority.READ)),
  ORGANIZER(List.of(Authority.READ, Authority.WRITE));

  public enum Authority {
    READ,
    WRITE
  }

  private final List<Authority> authorities;

  Role(List<Authority> authorities) {
    this.authorities = authorities;
  }

  public List<Authority> getAuthorities() {
    return authorities;
  }
}
