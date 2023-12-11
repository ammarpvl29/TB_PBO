package com.tugasbesaroop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LibraryDatabase {
    public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:Library.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet executeQuery(String sql) {
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void createNewTables() {
        // SQL statement for creating new tables
        String sqlMember = "CREATE TABLE IF NOT EXISTS Member (\n"
                + " id text PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " fine integer NOT NULL,\n"
                + " point integer NOT NULL\n"
                + ");";

        String sqlBook = "CREATE TABLE IF NOT EXISTS Book (\n"
                + " title text PRIMARY KEY,\n"
                + " author text NOT NULL,\n"
                + " category text NOT NULL\n"
                + ");";

        String sqlBookLoan = "CREATE TABLE IF NOT EXISTS BookLoan (\n"
                + " member_id text NOT NULL,\n"
                + " book_title text NOT NULL,\n"
                + " loan_date text NOT NULL,\n"
                + " return_date text,\n"
                + " fine integer NOT NULL,\n"
                + " FOREIGN KEY (member_id) REFERENCES Member(id),\n"
                + " FOREIGN KEY (book_title) REFERENCES Book(title)\n"
                + ");";

        String sqlCategory = "CREATE TABLE IF NOT EXISTS Category (\n"
                + " name text PRIMARY KEY,\n"
                + " point integer NOT NULL\n"
                + ");";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            // create new tables
            stmt.execute(sqlMember);
            stmt.execute(sqlBook);
            stmt.execute(sqlBookLoan);
            stmt.execute(sqlCategory);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
