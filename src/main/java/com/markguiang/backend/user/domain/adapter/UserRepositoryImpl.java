package com.markguiang.backend.user.domain.adapter;

import com.markguiang.backend.tenant.TenantContext;
import com.markguiang.backend.user.domain.User;
import com.markguiang.backend.user.domain.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final UserDao userDao;
  private final Long tenantId = TenantContext.getTenantId();

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
    Optional<UserBean> userBean = userDao.findByAuthId(tenantId, authId);
    return userBean.map(UserBean::toUser);
  }

  @Override
  public Optional<User> findByID(UUID id) {
    Optional<UserBean> userBean = userDao.findById(tenantId, id);
    return userBean.map(UserBean::toUser);
  }

  @Override
  public boolean existsByAuthId(String authId) {
    return userDao.existsByAuthId(tenantId, authId);
  }
}
