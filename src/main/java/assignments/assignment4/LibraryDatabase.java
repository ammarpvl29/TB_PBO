package assignments.assignment4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import assignments.assignment4.backend.buku.Buku;
import assignments.assignment4.backend.buku.Kategori;
import assignments.assignment4.backend.pengguna.Anggota;
import assignments.assignment4.backend.pengguna.Dosen;
import assignments.assignment4.backend.pengguna.Mahasiswa;

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

    public void addBook(Buku buku) {
        String sql = "INSERT INTO Book(title, author, publisher, category) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, buku.getJudul());
            pstmt.setString(2, buku.getPenulis());
            pstmt.setString(3, buku.getPenerbit());
            pstmt.setString(4, buku.getKategori().getNama()); // Assuming Kategori has a getNama() method
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCategory(Kategori kategori) {
        String sql = "INSERT INTO Category(name, point) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kategori.getNama());
            pstmt.setInt(2, kategori.getPoin());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addMember(Anggota anggota) {
        String sql = "INSERT INTO Member(id, name, type) VALUES(?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, anggota.getId());
            pstmt.setString(2, anggota.getNama());
            pstmt.setString(3, anggota instanceof Dosen ? "Dosen" : "Mahasiswa");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " on line " + e.getStackTrace()[0].getLineNumber());
        }
    }

    public void addStudent(Mahasiswa mahasiswa) {
        String sql = "INSERT INTO Student(id, name, birthdate, studyProgram, year) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mahasiswa.getId());
            pstmt.setString(2, mahasiswa.getNama());
            pstmt.setString(3, mahasiswa.getTanggalLahir());
            pstmt.setString(4, mahasiswa.getProgramStudi());
            pstmt.setString(5, mahasiswa.getAngkatan());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addLecturer(Dosen dosen) {
        String sql = "INSERT INTO Lecturer(id, name) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dosen.getId());
            pstmt.setString(2, dosen.getNama());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " on line " + e.getStackTrace()[0].getLineNumber());
        }
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
        String dropSqlMember = "DROP TABLE IF EXISTS Member";
        String dropSqlStudent = "DROP TABLE IF EXISTS Student";
        String dropSqlLecturer = "DROP TABLE IF EXISTS Lecturer";
        String dropSqlBook = "DROP TABLE IF EXISTS Book";
        String dropSqlBookLoan = "DROP TABLE IF EXISTS BookLoan";
        String dropSqlCategory = "DROP TABLE IF EXISTS Category";

        // SQL statement for creating new tables

        String sqlMember = "CREATE TABLE IF NOT EXISTS Member (\n"
                + " id text PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " type text NOT NULL\n" // This will indicate whether the member is a 'Student' or 'Lecturer'
                + ");";

        String sqlStudent = "CREATE TABLE IF NOT EXISTS Student (\n"
                + " id text PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " birthdate text NOT NULL,\n"
                + " studyProgram text NOT NULL,\n"
                + " year text NOT NULL\n"
                + ");";

        String sqlLecturer = "CREATE TABLE IF NOT EXISTS Lecturer (\n"
                + " id text PRIMARY KEY,\n"
                + " name text NOT NULL\n"
                + ");";
        ;

        String sqlBook = "CREATE TABLE IF NOT EXISTS Book (\n"
                + " title text PRIMARY KEY,\n"
                + " author text NOT NULL,\n"
                + " publisher text NOT NULL,\n"
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
            stmt.execute(dropSqlMember);
            stmt.execute(dropSqlStudent);
            stmt.execute(dropSqlLecturer);
            stmt.execute(dropSqlBook);
            stmt.execute(dropSqlBookLoan);
            stmt.execute(dropSqlCategory);
            // create new tables
            stmt.execute(sqlMember);
            stmt.execute(sqlStudent);
            stmt.execute(sqlLecturer);
            stmt.execute(sqlBook);
            stmt.execute(sqlBookLoan);
            stmt.execute(sqlCategory);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " on line " + e.getStackTrace()[0].getLineNumber());
        }

    }

}