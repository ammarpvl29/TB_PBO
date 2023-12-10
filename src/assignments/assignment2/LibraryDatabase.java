package com.tugasbesaroop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LibraryDatabase {
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:Library.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS books (\n"
                + " id integer PRIMARY KEY,\n"
                + " title text NOT NULL,\n"
                + " author text NOT NULL,\n"
                + " category text NOT NULL,\n"
                + " point integer NOT NULL\n"
                + ");";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
