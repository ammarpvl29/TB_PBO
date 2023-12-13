package com.tugasbesaroop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

// TODO
public class BookLoan {
    private static long DENDA_PER_HARI = 3000;
    private Member member;
    private Book book;
    private Date loanDate;
    private Date returnDate;
    private long fine;
    private boolean isOnLoan;
    private boolean status;

    public BookLoan(Member member, Book book, Date loanDate) {
        this.member = member;
        this.book = book;
        this.loanDate = loanDate;
        this.isOnLoan = true;
    }

    public void returnBook(Date returnDate) {
        this.returnDate = returnDate;
        this.isOnLoan = false;
        calculateFine();
    }

    private void calculateFine() {
        long diff = returnDate.getTime() - loanDate.getTime();
        long overdueDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) - 7;
        if (overdueDays > 0) {
            this.fine = overdueDays * DENDA_PER_HARI;
        }
    }

    public boolean isOnLoan() {
        return this.isOnLoan;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getLoanDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(loanDate);
    }

    public String getReturnDate() {
        if (returnDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(returnDate);
        } else {
            return null;
        }
    }

    public long getFine() {
        return fine;
    }

    public void setFine(long fine) {
        this.fine = fine;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private static final String INSERT_SQL = "INSERT INTO BookLoan(member_id, book_title, loan_date, return_date, fine) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE BookLoan SET return_date = ?, fine = ? WHERE member_id = ? AND book_title = ?";
    private static final String DELETE_SQL = "DELETE FROM BookLoan WHERE member_id = ? AND book_title = ?";
    private static final String SELECT_SQL = "SELECT * FROM BookLoan WHERE member_id = ? AND book_title = ?";

    public void insertIntoDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL);
        pstmt.setString(1, this.member.getId());
        pstmt.setString(2, this.book.getTitle());
        pstmt.setString(3, this.getLoanDate());
        pstmt.setString(4, this.getReturnDate());
        pstmt.setLong(5, this.fine);
        pstmt.executeUpdate();
    }

    public void updateInDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL);
        pstmt.setString(1, this.getReturnDate());
        pstmt.setLong(2, this.fine);
        pstmt.setString(3, this.member.getId());
        pstmt.setString(4, this.book.getTitle());
        pstmt.executeUpdate();
    }

    public void deleteFromDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL);
        pstmt.setString(1, this.member.getId());
        pstmt.setString(2, this.book.getTitle());
        pstmt.executeUpdate();
    }

    public static BookLoan selectFromDatabase(Connection conn, String memberId, String bookTitle)
            throws SQLException, ParseException {
        PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
        pstmt.setString(1, memberId);
        pstmt.setString(2, bookTitle);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            // Retrieve the Member and Book objects associated with this BookLoan
            Member member = Member.selectFromDatabase(conn, memberId);
            Book book = Book.selectFromDatabase(conn, bookTitle);

            // Convert the loan and return dates from String to Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date loanDate = dateFormat.parse(rs.getString("loan_date"));
            Date returnDate = rs.getString("return_date") != null ? dateFormat.parse(rs.getString("return_date"))
                    : null;

            // Create a new BookLoan object using the existing constructor
            BookLoan bookLoan = new BookLoan(member, book, loanDate);

            // Set the return date and fine
            if (returnDate != null) {
                bookLoan.returnBook(returnDate);
            }
            bookLoan.setFine(rs.getLong("fine"));

            return bookLoan;
        } else {
            return null;
        }
    }

}
