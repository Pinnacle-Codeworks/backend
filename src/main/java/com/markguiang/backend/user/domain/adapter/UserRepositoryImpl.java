package com.markguiang.backend.user.domain.adapter;

import com.markguiang.backend.user.domain.User;
import com.markguiang.backend.user.domain.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final UserDao userDao;

  public UserRepositoryImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UUID save(User user) {
    userDao.insertUser(user);
    return user.getId();
  }

  @Override
  public Optional<User> findByAuthId(String authId) {
    Optional<UserBean> userBean = userDao.findByAuthId(authId);
    return userBean.map(UserBean::toUser);
  }

  @Override
  public Optional<User> findByID(UUID id) {
    throw new UnsupportedOperationException("findByID with no tenantId is not supported");
  }

  @Override
  public boolean existsByAuthId(String authId) {
    return userDao.existsByAuthId(authId);
  }
}
