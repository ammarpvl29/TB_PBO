package assignments.assignment2;

import java.text.ParseException;
import java.util.Scanner;
import Member.Member;
import Cateogry.Category;
import Book.Book;

public class Library {

    public static final String ANSI_BLUE = "\u001B[44m";
    public static final String ANSI_RED = "\u001B[41m";

    private Scanner input = new Scanner(System.in);

    private Member[] members;
    private Book[] books;
    private Category[] categories;
    int i = 0, j = 0, k = 0;

    private int checkMember(String id) {
        for (int i = 0; i < members.length; i++) {
            if (members[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private Category checkCategory(String nama) {
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].getName().toLowerCase().equals(nama.toLowerCase())) {
                return categories[i];
            }
        }
        return null;
    }

    private int checkBook(String judul, String penulis) {
        for (int i = 0; i < books.length; i++) {
            if (books[i].getTitle().toLowerCase().equals(judul.toLowerCase()) &&
                    books[i].getAuthor().toLowerCase().equals(penulis.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    private BookLoan checkBookLoan(BookLoan[] bookLoans, String judul, String penulis) {
        for (int i = 0; i < bookLoans.length; i++) {
            if (bookLoans[i].getBook().getTitle().toLowerCase().equals(judul.toLowerCase()) &&
                    bookLoans[i].getBook().getAuthor().toLowerCase().equals(penulis.toLowerCase())) {
                return bookLoans[i];
            }
        }
        return null;
    }

    private void addMember() {
        IdGenerator.buildMapCharToValue();
        Scanner scanner = new Scanner(System.in);
        members[i] = new Member();
        System.out.println("---------- Tambah Anggota ----------");
        System.out.print("Nama : ");
        String nama = scanner.nextLine();
        System.out.print("Program Studi : ");
        String programStudi = scanner.nextLine();
        System.out.print("Angkatan : ");
        String angkatan = scanner.nextLine();
        System.out.print("Tanggal Lahir : ");
        String tanggalLahir = scanner.nextLine();
        String id = IdGenerator.generateId(programStudi, angkatan, tanggalLahir);
        members[i].setId(id);
        members[i].setName(nama);
        members[i].setStudyProgram(programStudi);
        members[i].setAngkatan(angkatan);
        members[i].setDateOfBirth(tanggalLahir);
        members[i].setPoint(0);
        members[i].setFine(0l);
        System.out.println("Member " + members[i].getName() + " berhasil ditambahkan dengan data:");
        System.out.println("ID Anggota: " + members[i].getId());
        System.out.println("Nama Anggota: " + members[i].getName());
        System.out.println("Total Point: " + members[i].getPoint());
        System.out.println("Denda: " + members[i].getFine());
        i++;
    }

    private void addCategory() {
        Scanner scanner = new Scanner(System.in);
        categories[j] = new Category();
        System.out.println("---------- Tambah Kategori ----------");
        System.out.print("Nama Kategori: ");
        String nama = scanner.nextLine();
        System.out.print("Point : ");
        int point = scanner.nextInt();
        if (j == 0) {
            categories[j].setName(nama);
            categories[j].setPoint(point);
            System.out.println("Kategori " + categories[j].getName() + " dengan " + categories[i].getPoint()
                    + " point berhasil ditambahkan");
            j++;
        } else {
            if (checkCategory(nama) == null) {
                categories[j].setName(nama);
                categories[j].setPoint(point);
                System.out.println("Kategori " + categories[j].getName() + " dengan " + categories[i].getPoint()
                        + " point berhasil ditambahkan");
                j++;
            } else {
                System.out.println("Kategori " + nama + " sudah pernah ditambahkan");
            }
        }
    }

    private void addBook() {
        Scanner scanner = new Scanner(System.in);
        books[k] = new Book();
        System.out.println("---------- Tambah Buku ----------");
        System.out.print("Judul : ");
        String judul = scanner.nextLine();
        System.out.print("Penulis : ");
        String penulis = scanner.nextLine();
        System.out.print("Penerbit : ");
        String penerbit = scanner.nextLine();
        System.out.print("Kategori : ");
        String kategori = scanner.nextLine();
        System.out.print("Stok : ");
        int stok = scanner.nextInt();
        if (k == 0) {
            if (stok > 0) {
                books[k].setCategory(checkCategory(kategori));
                books[k].setTitle(judul);
                books[k].setAuthor(penulis);
                books[k].setPublisher(penerbit);
                books[k].setStok(stok);
                System.out.println(
                        "Buku " + books[k].getTitle() + " oleh " + books[k].getAuthor() + " berhasil ditambahkan");
                k++;
            } else {
                System.out.println("Stok harus lebih dari 0");
            }
        } else {
            if (checkBook(judul, penulis) == -1) {
                if (checkCategory(kategori) != null) {
                    if (stok > 0) {
                        books[k].setCategory(checkCategory(kategori));
                        books[k].setTitle(judul);
                        books[k].setAuthor(penulis);
                        books[k].setPublisher(penerbit);
                        books[k].setStok(stok);
                        System.out.println("Buku " + books[k].getTitle() + " oleh " + books[k].getAuthor()
                                + " berhasil ditambahkan");
                        k++;
                    } else {
                        System.out.println("Stok harus lebih dari 0");
                    }
                } else {
                    System.out.println("Kategori " + kategori + " tidak ditemukan");
                }
            } else {
                System.out.println("Buku " + judul + " oleh " + penulis + " sudah pernah ditambahkan");
            }
        }
    }

    private void peminjaman() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------- Peminjaman Buku ----------");
        System.out.println("ID Anggota: ");
        String id = scanner.nextLine();
        System.out.println("Judul Buku: ");
        String judul = scanner.nextLine();
        System.out.println("ID Anggota: ");
        String penulis = scanner.nextLine();
        System.out.println("Tangal Peminjaman: ");
        String tanggalPeminjaman = scanner.nextLine();
        int indeks = checkMember(id);
        if (indeks != -1) {
            int indeksBuku = checkBook(judul, penulis);
            Book book = books[indeksBuku];
            if (book != null) {
                int stok = book.getStok();
                if (stok >= 1) {
                    if (members[indeks].getBookLoans().length < 3) {
                        if (members[indeks].getFine() < 5000) {
                            if (checkBookLoan(members[indeks].getBookLoans(), judul, penulis) == null) {
                                members[indeks].pinjam(book, tanggalPeminjaman);
                                books[indeksBuku].setStok(stok - 1);
                            } else {
                                System.out.println("Buku " + judul + " oleh " + penulis + " sedang dipinjam");
                            }
                        } else {
                            System.out.println("Denda lebih dari Rp 5000");
                        }
                    } else {
                        System.out.println("Jumlah buku yang sedang dipinjam sudah mencapai batas maksimal");
                    }
                } else {
                    System.out.println("Buku " + judul + " oleh " + penulis + " tidak tersedia");
                }
            } else {
                System.out.println("Buku " + judul + " oleh " + penulis + " tidak ditemukan");
            }
        } else {
            System.out.println("Anggota dengan ID " + id + " tidak ditemukan");
        }
    }

    private void pengembalian() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------- Pengembalian Buku ----------");
        System.out.println("ID Anggota: ");
        String id = scanner.nextLine();
        System.out.println("Judul Buku: ");
        String judul = scanner.nextLine();
        System.out.println("ID Anggota: ");
        String penulis = scanner.nextLine();
        System.out.println("Tangal Pengembalian: ");
        String tanggalPengembalian = scanner.nextLine();
        int indeks = checkMember(id);
        if (indeks != -1) {
            int indeksBuku = checkBook(judul, penulis);
            Book book = books[indeksBuku];
            if (book != null) {
                int stok = book.getStok();
                if (stok >= 1) {
                    if (members[indeks].getBookLoans().length <= 3) {
                        if (members[indeks].getFine() < 5000) {
                            BookLoan bookLoan = checkBookLoan(members[indeks].getBookLoans(), judul, penulis);
                            if (bookLoan != null) {
                                members[indeks].kembali(book, tanggalPengembalian);
                                books[indeksBuku].setStok(stok + 1);
                            } else {
                                System.out.println("Buku " + judul + " oleh " + penulis + " tidak sedang dipinjam");
                            }
                        } else {
                            System.out.println("Denda lebih dari Rp 5000");
                        }
                    } else {
                        System.out.println("Jumlah buku yang sedang dipinjam sudah mencapai batas maksimal");
                    }
                } else {
                    System.out.println("Buku " + judul + " oleh " + penulis + " tidak tersedia");
                }
            } else {
                System.out.println("Buku " + judul + " oleh " + penulis + " tidak ditemukan");
            }
        } else {
            System.out.println("Anggota dengan ID " + id + " tidak ditemukan");
        }
    }

    private void bayarDenda() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------- Pembayaran Denda ----------");
        System.out.println("ID Anggota: ");
        String id = scanner.nextLine();
        System.out.println("Jumlah: ");
        long jumlah = scanner.nextLong();
        int indeks = checkMember(id);
        if (indeks != -1) {
            long fine = members[indeks].getFine();
            if (fine != 0) {
                members[indeks].bayarDenda(jumlah);
            } else {
                System.out.println(members[indeks].getName() + " tidak memiliki denda");
            }
        } else {
            System.out.println("Anggota dengan ID " + id + " tidak ditemukan");
        }
    }

    private void detailAnggota() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------- Detail Anggota ----------");
        System.out.println("ID Anggota: ");
        String id = scanner.nextLine();
        int indeks = checkMember(id);
        if (indeks != -1) {
            members[indeks].detail();
        } else {
            System.out.println("Anggota dengan ID " + id + " tidak ditemukan");
        }
    }

    public static void main(String[] args) throws ParseException {
        Library program = new Library();
        program.members = new Member[100];
        program.categories = new Category[100];
        program.books = new Book[100];
        program.menu();
    }

    public void menu() throws ParseException {
        int command = 0;
        boolean hasChosenExit = false;
        System.out.println("Selamat Datang di Sistem Perpustakaan SistakaNG!");
        while (!hasChosenExit) {
            System.out.println("================ Menu Utama ================\n");
            System.out.println("1. Tambah Anggota");
            System.out.println("2. Tambah Kategori");
            System.out.println("3. Tambah Buku");
            System.out.println("4. Peminjaman");
            System.out.println("5. Pengembalian");
            System.out.println("6. Pembayaran Denda");
            System.out.println("7. Detail Anggota");
            System.out.println("8. 3 Peringkat Pertama");
            System.out.println("99. Keluar");
            System.out.print("Masukkan pilihan menu: ");
            command = Integer.parseInt(input.nextLine());
            System.out.println();
            if (command == 1) {
                addMember();
            } else if (command == 2) {
                addCategory();
            } else if (command == 3) {
                addBook();
            } else if (command == 4) {
                peminjaman();
            } else if (command == 5) {
                pengembalian();
            } else if (command == 6) {
                bayarDenda();
            } else if (command == 7) {
                detailAnggota();
            } else if (command == 8) {
                // Belum selesai :(
            } else if (command == 99) {
                System.out.println("Terima kasih telah menggunakan SistakaNG!");
                hasChosenExit = true;
            } else {
                System.out.println("Menu tidak dikenal!");
            }
            System.out.println();
        }

        input.close();
    }
}
