package com.tugasbesaroop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Dah Komplit

public class Book {
    private String title;
    private String author;
    private String publisher;
    private int stok;
    private Category category;

    @Override
    public String toString() {
        return "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private static final String INSERT_SQL = "INSERT INTO Book(title, author, publisher, category) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE Book SET author = ?, publisher = ?, category = ? WHERE title = ?";
    private static final String DELETE_SQL = "DELETE FROM Book WHERE title = ?";
    private static final String SELECT_SQL = "SELECT * FROM Book WHERE title = ?";

    public void insertIntoDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL);
        pstmt.setString(1, this.title);
        pstmt.setString(2, this.author);
        pstmt.setString(3, this.publisher);
        pstmt.setString(4, this.category.getName());
        pstmt.executeUpdate();
    }

    public void updateInDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL);
        pstmt.setString(1, this.author);
        pstmt.setString(2, this.publisher);
        pstmt.setString(3, this.category.getName());
        pstmt.setString(4, this.title);
        pstmt.executeUpdate();
    }

    public void deleteFromDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL);
        pstmt.setString(1, this.title);
        pstmt.executeUpdate();
    }

    public static Book selectFromDatabase(Connection conn, String title) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
        pstmt.setString(1, title);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Book book = new Book();
            // You'll need to retrieve the Category object associated with this Book
            // This could be done with an additional SELECT statement
            // For simplicity, I'm leaving this as null
            book.setCategory(null);
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setPublisher(rs.getString("publisher"));
            return book;
        } else {
            return null;
        }
    }
}