package com.tugasbesaroop;

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
}