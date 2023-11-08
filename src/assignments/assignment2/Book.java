package assignments.assignment2;
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
}

/*
 * +-----------------+
 * | Book |
 * +-----------------+
 * | - title: String
 * | - author: String
 * | - publisher: String
 * | - stok: int
 * | - category: Category
 * +-----------------+
 * | + getTitle(): String
 * | + setTitle(String): void
 * | + getAuthor(): String
 * | + setAuthor(String): void
 * | + getPublisher(): String
 * | + setPublisher(String): void
 * | + getStok(): int
 * | + setStok(int): void
 * | + getCategory(): Category
 * | + setCategory(Category): void
 * | + toString(): String
 * +-----------------+
 */