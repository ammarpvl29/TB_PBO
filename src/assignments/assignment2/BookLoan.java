package assignments.assignment2;

// TODO
public class BookLoan {
    private static long DENDA_PER_HARI = 3000;
    private Member member;
    private Book book;
    private String loanDate;
    private String returnDate = "-";
    private long fine;
    private boolean status;

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

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
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

/*
 * +-----------------+
 * | BookLoan |
 * +-----------------+
 * | - DENDA_PER_HARI: long (static)
 * | - member: Member
 * | - book: Book
 * | - loanDate: String
 * | - returnDate: String
 * | - fine: long
 * | - status: boolean
 * +-----------------+
 * | + getMember(): Member
 * | + setMember(Member): void
 * | + getBook(): Book
 * | + setBook(Book): void
 * | + getLoanDate(): String
 * | + setLoanDate(String): void
 * | + getReturnDate(): String
 * | + setReturnDate(String): void
 * | + getFine(): long
 * | + setFine(long): void
 * | + isStatus(): boolean
 * | + setStatus(boolean): void
 * +-----------------+
 */