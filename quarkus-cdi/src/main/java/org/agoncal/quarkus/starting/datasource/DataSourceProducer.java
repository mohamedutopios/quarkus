package org.agoncal.quarkus.starting.datasource;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import org.agoncal.quarkus.starting.qualifier.MySQL;
import org.agoncal.quarkus.starting.qualifier.Postgres;
import org.apache.commons.dbcp2.BasicDataSource;

@ApplicationScoped
public class DataSourceProducer {

  @Produces
  @MySQL
  @ApplicationScoped
  public DataSource createMySQLDataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl("jdbc:mysql://localhost:3307/mydb_mysql");
    dataSource.setUsername("mysqluser");
    dataSource.setPassword("mysqlpass");
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    // Test de la connexion
    try {
      dataSource.getConnection().isValid(2);  // Vérifie si la connexion est valide dans les 2 secondes
      System.out.println("MySQL connection successful!");
    } catch (Exception e) {
      System.err.println("Failed to connect to MySQL database.");
      e.printStackTrace();
    }

    return dataSource;
  }

  @Produces
  @Postgres
  @ApplicationScoped
  public DataSource createPostgresDataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl("jdbc:postgresql://localhost:5432/mydb_postgres");
    dataSource.setUsername("postgresuser");
    dataSource.setPassword("postgrespass");
    dataSource.setDriverClassName("org.postgresql.Driver");
    return dataSource;
  }
}

