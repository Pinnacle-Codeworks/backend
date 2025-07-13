package com.markguiang.backend.config;

import com.markguiang.backend.event.domain.adapters.jdbi.EventDao;
import com.markguiang.backend.tenant.TenantContext;
import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.StatementCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JdbiConfiguration {

  private static final Logger log = LoggerFactory.getLogger(JdbiConfiguration.class);

  @Bean
  public Jdbi jdbi(DataSource ds, List<JdbiPlugin> plugins, List<RowMapper<?>> mappers) {
    TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);
    final Jdbi jdbi = Jdbi.create(proxy).addCustomizer(new TenantStatementCustomizer());

    log.info("Installing Plugins... ({} found)", plugins.size());
    plugins.forEach(jdbi::installPlugin);

    log.info("Installing Mappers... ({} found)", mappers.size());
    mappers.forEach(jdbi::registerRowMapper);
    return jdbi;
  }

  @Bean
  public EventDao eventDao(Jdbi jdbi) {
    return jdbi.onDemand(EventDao.class);
  }

  public class TenantStatementCustomizer implements StatementCustomizer {
    @Override
    public void beforeExecution(PreparedStatement stmt, StatementContext ctx) {
      Long tenantId = TenantContext.getTenantId();
      if (tenantId == null) {
        throw new IllegalStateException("Missing tenant ID in context");
      }
      ctx.getBinding().addNamed("tenantId", tenantId);
    }
  }
}
