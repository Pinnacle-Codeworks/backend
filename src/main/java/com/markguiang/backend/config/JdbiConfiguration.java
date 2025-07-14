package com.markguiang.backend.config;

import com.markguiang.backend.event.domain.adapters.jdbi.EventDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class JdbiConfiguration {

  @Bean
  public Jdbi jdbi(DataSource ds) {
    TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);
    final Jdbi jdbi = Jdbi.create(proxy);
    jdbi.installPlugin(new SqlObjectPlugin());
    return jdbi;
  }

  @Bean
  public EventDao eventDao(Jdbi jdbi) {
    return jdbi.onDemand(EventDao.class);
  }
}