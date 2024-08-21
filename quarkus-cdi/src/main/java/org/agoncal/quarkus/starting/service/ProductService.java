package org.agoncal.quarkus.starting.service;

import org.agoncal.quarkus.starting.qualifier.MySQL;
import org.agoncal.quarkus.starting.qualifier.Postgres;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@ApplicationScoped
public class ProductService {

  @Inject
  @MySQL
  private DataSource mysqlDataSource;

  @Inject
  @Postgres
  private DataSource postgresDataSource;

  public void printMySQLProducts() {
    String query = "SELECT id, name, price FROM products";

    try (Connection connection = mysqlDataSource.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

      System.out.println("Products from MySQL database:");
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        System.out.printf("ID: %d, Name: %s, Price: %.2f%n", id, name, price);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void printPostgresProducts() {
    String query = "SELECT id, name, price FROM products";

    try (Connection connection = postgresDataSource.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

      System.out.println("Products from PostgreSQL database:");
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        System.out.printf("ID: %d, Name: %s, Price: %.2f%n", id, name, price);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
