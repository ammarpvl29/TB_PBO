package com.tugasbesaroop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Member {

    private String id;
    private String name;
    private String dateOfBirth;
    private String studyProgram;
    private String angkatan;
    private long fine;
    private int point;
    private BookLoan[] bookLoans;
    private int i = 0;
    private int numLoans = 0;

    private int checkBookLoan(BookLoan[] bookLoans, String judul, String penulis) {
        for (int i = 0; i < bookLoans.length; i++) {
            if (bookLoans[i].getBook().getTitle().toLowerCase().equals(judul.toLowerCase())
                    && bookLoans[i].getBook().getAuthor().toLowerCase().equals(penulis.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    public Member() {
        bookLoans = new BookLoan[3];
    }

    public void pinjam(Book book, Date loanDate) {
        bookLoans[i] = new BookLoan(this, book, loanDate);
        numLoans++;
    }

    public void kembali(Book book, Date returnDate) throws ParseException {
        int indeks = checkBookLoan(bookLoans, book.getTitle(), book.getAuthor());
        if (indeks != -1) {
            bookLoans[indeks].setReturnDate(returnDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String loanDate = bookLoans[indeks].getLoanDate();
            Date loan = sdf.parse(loanDate);
            long diff = returnDate.getTime() - loan.getTime();
            long durasi = diff / (24 * 60 * 60 * 1000);
            long denda = (durasi - 7) * 3000;
            bookLoans[indeks].setFine(denda);
            point = point + book.getCategory().getPoint();
            fine = fine + denda;
            System.out.println("Buku " + book.getTitle() + " berhasil dikembalikan oleh " + name + " dengan denda Rp "
                    + bookLoans[indeks].getFine() + "!");
        }
    }

    public void detail() {
        System.out.println("ID Anggota: " + id);
        System.out.println("Nama Anggota: " + name);
        System.out.println("Total Point: " + point);
        System.out.println("Denda: " + fine);
        System.out.println("Riwayat Peminjaman Buku : ");

        boolean hasBorrowed = false;
        for (int i = 0; i < bookLoans.length; i++) {
            if (bookLoans[i] != null) {
                hasBorrowed = true;
                System.out.println("---------- " + (i + 1) + " ----------");
                System.out.println("Judul Buku : " + bookLoans[i].getBook().getTitle());
                System.out.println("Penulis Buku : " + bookLoans[i].getBook().getAuthor());
                System.out.println("Penerbit Buku : " + bookLoans[i].getBook().getPublisher());
                System.out.println("Kategori : " + bookLoans[i].getBook().getCategory().getName());
                System.out.println("Point : " + bookLoans[i].getBook().getCategory().getPoint());
                System.out.println("Tanggal Peminjaman : " + bookLoans[i].getLoanDate());
                System.out.println("Tanggal Pengembalian : " + bookLoans[i].getReturnDate());
                System.out.println("Denda : Rp" + bookLoans[i].getFine());
            }
        }
        if (!hasBorrowed) {
            System.out.println("Belum meminjam buku");
        }
    }

    public void bayarDenda(long amount) {
        if (amount < fine) {
            System.out.println(name + " berhasil membayar denda sebesar Rp " + amount);
            System.out.println("Sisa denda saat ini: Rp " + (fine - amount));
            fine = fine - amount;
        } else {
            System.out.println(name + " berhasil membayar lunas denda");
            System.out.println("Jumlah kembalian: Rp " + (amount - fine));
            fine = 0;
        }
    }

    @Override
    public String toString() {
        return "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public long getFine() {
        return fine;
    }

    public void setFine(long fine) {
        this.fine = fine;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public BookLoan[] getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(BookLoan[] bookLoans) {
        this.bookLoans = bookLoans;
    }

    public int getNumLoans() {
        return numLoans;
    }

    private static final String INSERT_SQL = "INSERT INTO Member(id, name, fine, point) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE Member SET name = ?, fine = ?, point = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM Member WHERE id = ?";
    private static final String SELECT_SQL = "SELECT * FROM Member WHERE id = ?";

    public void insertIntoDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL);
        pstmt.setString(1, this.id);
        pstmt.setString(2, this.name);
        pstmt.setLong(3, this.fine);
        pstmt.setInt(4, this.point);
        pstmt.executeUpdate();
    }

    public void updateInDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL);
        pstmt.setString(1, this.name);
        pstmt.setLong(2, this.fine);
        pstmt.setInt(3, this.point);
        pstmt.setString(4, this.id);
        pstmt.executeUpdate();
    }

    public void deleteFromDatabase(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL);
        pstmt.setString(1, this.id);
        pstmt.executeUpdate();
    }

    public static Member selectFromDatabase(Connection conn, String id) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Member member = new Member();
            member.setId(rs.getString("id"));
            member.setName(rs.getString("name"));
            member.setFine(rs.getLong("fine"));
            member.setPoint(rs.getInt("point"));
            return member;
        } else {
            return null;
        }
    }
}
