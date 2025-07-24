package com.markguiang.backend.user.domain.adapter;

import com.markguiang.backend.user.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {
  @SqlUpdate("""
        INSERT INTO user_
          (tenant_id, id, auth_id, email, role, first_name, last_name)
        VALUES
          (:tenantId, :id, :authId, :email, :role, :firstName, :lastName)
      """)
  public void insertUser(@BindBean User user);

  @SqlQuery("""
          SELECT
            u.tenant_id,
            u.id,
            u.auth_id,
            u.email,
            u.role,
            u.first_name,
            u.last_name
          FROM user_ u
          WHERE u.tenant_id = :tenantId AND u.auth_id = :authId
      """)
  @RegisterBeanMapper(UserBean.class)
  public Optional<UserBean> findByAuthId(
      @Bind("tenantId") Long tenantId, @Bind("authId") String authId);

  @SqlQuery("""
          SELECT
            u.tenant_id,
            u.id,
            u.auth_id,
            u.email,
            u.role,
            u.first_name,
            u.last_name
          FROM user_ u
          WHERE u.tenant_id = :tenantId AND u.id = :id
      """)
  @RegisterBeanMapper(UserBean.class)
  public Optional<UserBean> findById(@Bind("tenantId") Long tenantId, @Bind("id") UUID id);

  @SqlQuery("""
          SELECT EXISTS (
            SELECT 1 FROM user_ WHERE tenant_id = :tenantId AND auth_id = :authId
          )
      """)
  boolean existsByAuthId(@Bind("tenantId") Long tenantId, @Bind("authId") String authId);
}
