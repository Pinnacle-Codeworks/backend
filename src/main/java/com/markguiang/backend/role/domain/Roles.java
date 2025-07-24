package com.markguiang.backend.role.domain;

import java.util.ArrayList;
import java.util.List;

public class Roles {
  public enum Role {
    PARTICIPANT,
    ORGANIZER,
  }

  public enum Authority {
    READ,
    WRITE,
  }

  public static List<Authority> getAuthoritiesFromRole(Role role) {

    List<Authority> res = new ArrayList<>();
    switch (role) {
      case PARTICIPANT:
        res.add(Authority.READ);
        break;
      case ORGANIZER:
        res.add(Authority.WRITE);
        res.addAll(getAuthoritiesFromRole(Role.PARTICIPANT));
        break;
    }
    return List.copyOf(res);
  }
}
