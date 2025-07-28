package com.markguiang.backend.user.domain;

import com.markguiang.backend.base.model.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<User> {
  Optional<User> findByAuthId(String authId);

  boolean existsByAuthId(String authId);
}
