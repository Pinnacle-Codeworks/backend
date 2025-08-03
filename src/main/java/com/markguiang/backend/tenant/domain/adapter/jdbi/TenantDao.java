package com.markguiang.backend.tenant.domain.adapter.jdbi;

import com.markguiang.backend.tenant.domain.Tenant;
import java.util.Optional;
import java.util.UUID;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TenantDao {

  @SqlQuery("""
          SELECT EXISTS (
            SELECT 1 FROM tenant WHERE name = :name
          )
      """)
  public boolean existsByName(@Bind("name") String name);

  @SqlQuery("""
        SELECT * FROM tenant WHERE id = :id
      """)
  public Optional<Tenant> findByID(@Bind("id") UUID id);

  @SqlUpdate("""
        INSERT INTO tenant (id, name)
          VALUES (:id, :name)
      """)
  public void insertTenant(@BindBean Tenant tenant);

  @SqlQuery("""
        SELECT id FROM tenant WHERE name = :name
      """)
  public Optional<UUID> getIdByName(@Bind("name") String name);
}