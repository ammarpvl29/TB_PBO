package com.tugasbesaroop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// TODO
public class Category {
    private String name;
    private int point;

    @Override
    public String toString() {
        // TODO
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    private static final String INSERT_SQL = "INSERT INTO Category(name, point) VALUES(?, ?)";
    private static final String UPDATE_SQL = "UPDATE Category SET point = ? WHERE name = ?";
    private static final String DELETE_SQL = "DELETE FROM Category WHERE name = ?";
    private static final String SELECT_SQL = "SELECT * FROM Category WHERE name = ?";

    public void insertIntoDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL);
        pstmt.setString(1, this.name);
        pstmt.setInt(2, this.point);
        pstmt.executeUpdate();
    }

    public void updateInDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL);
        pstmt.setInt(1, this.point);
        pstmt.setString(2, this.name);
        pstmt.executeUpdate();
    }

    public void deleteFromDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL);
        pstmt.setString(1, this.name);
        pstmt.executeUpdate();
    }

    public static Category selectFromDatabase(Connection conn, String name) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Category category = new Category();
            category.setName(rs.getString("name"));
            category.setPoint(rs.getInt("point"));
            return category;
        } else {
            return null;
        }
    }
}