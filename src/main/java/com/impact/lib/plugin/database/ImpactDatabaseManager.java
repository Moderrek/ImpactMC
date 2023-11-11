package com.impact.lib.plugin.database;

import com.impact.lib.ImpactLibPlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ImpactDatabaseManager {

  private final ImpactLibPlugin plugin;
  private Connection connection;

  public ImpactDatabaseManager(final @NotNull ImpactLibPlugin plugin) {
    this.plugin = plugin;
  }

  public void connect() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    connection = DriverManager.getConnection("jdbc:sqlite:plugins/" + plugin.getName() + "/database.db");
    onConnect();
  }

  public void onConnect() throws SQLException {
    createCustomBlocksTable();
  }

  private void createCustomBlocksTable() throws SQLException {
    String tableSql = "CREATE TABLE IF NOT EXISTS customblocks (WORLD_NAME VARCHAR(256) NOT NULL, X INT NOT NULL , Y INT NOT NULL , Z INT NOT NULL , NAMESPACED_KEY TEXT NOT NULL , PRIMARY KEY (WORLD_NAME, X, Y, Z))";
    Statement statement = connection.createStatement();
    statement.execute(tableSql);
  }

  public Collection<CustomBlockRecord> getSavedCustomBlocks() throws SQLException {
    Collection<CustomBlockRecord> collection = new ArrayList<>();
    String sql = "SELECT WORLD_NAME, X, Y, Z, NAMESPACED_KEY FROM customblocks";
    Statement stmt = connection.createStatement();
    ResultSet result = stmt.executeQuery(sql);
    while (result.next()) {
      String world_name = result.getString("WORLD_NAME");
      int x = result.getInt("X");
      int y = result.getInt("Y");
      int z = result.getInt("Z");
      String namespaced_key = result.getString("NAMESPACED_KEY");
      collection.add(new CustomBlockRecord(world_name, x, y, z, namespaced_key));
    }
    return collection;
  }

}
