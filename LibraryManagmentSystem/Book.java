import java.util.ArrayList;
import java.util.List;

public class Book {

    private String bookId;
    private String title;
    private String author;
    private String category;
    private String ISBN;
    private String edition;
    private String availabilityStatus; // Available, Borrowed, Reserved

    private List<String> borrowedHistory;
    private List<String> tags;
    private List<String> reviews;

    // ------------------- Constructor for Simple Books -------------------
    public Book(String bookId, String title, String author, String category, String ISBN, String availabilityStatus) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.ISBN = ISBN;
        this.availabilityStatus = availabilityStatus;

        this.borrowedHistory = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    // ------------------- Constructor for Builder -------------------
    private Book(BookBuilder builder) {
        this.bookId = builder.bookId;
        this.title = builder.title;
        this.author = builder.author;
        this.category = builder.category;
        this.ISBN = builder.ISBN;
        this.edition = builder.edition;

        this.availabilityStatus = "Available";

        this.borrowedHistory = new ArrayList<>();
        this.tags = builder.tags;
        this.reviews = builder.reviews;
    }

    // ------------------- Getters & Setters -------------------
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public String getEdition() { return edition; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public List<String> getBorrowedHistory() { return borrowedHistory; }
    public List<String> getTags() { return tags; }
    public List<String> getReviews() { return reviews; }

    // ------------------- Availability -------------------
    public boolean isAvailable() {
        return availabilityStatus.equalsIgnoreCase("Available");
    }

    public void borrow(String userId) {
        if (isAvailable()) {
            availabilityStatus = "Borrowed";
            borrowedHistory.add(userId);
        }
    }

    public void returnBook() {
        availabilityStatus = "Available";
    }

    public void reserve() {
        availabilityStatus = "Reserved";
    }

    // ------------------- Display Book Details -------------------
    public void displayBook() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Edition: " + edition);
        System.out.println("Status: " + availabilityStatus);
        System.out.println("Tags: " + tags);
        System.out.println("Reviews: " + reviews);
        System.out.println("Borrowed History: " + borrowedHistory);
        System.out.println("----------------------------");
    }

    // ===========================================================
    //                    BUILDER CLASS
    // ===========================================================
    public static class BookBuilder {

        private String bookId;
        private String title;
        private String author;
        private String category;
        private String ISBN;
        private String edition;

        private List<String> tags = new ArrayList<>();
        private List<String> reviews = new ArrayList<>();

        public BookBuilder(String bookId, String title, String author) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
        }

        public BookBuilder category(String category) {
            this.category = category;
            return this;
        }

        public BookBuilder ISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }

        public BookBuilder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public BookBuilder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        public BookBuilder addReview(String review) {
            this.reviews.add(review);
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}


