import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book implements NotifySubject {

    private String bookId;
    private String title;
    private String author;
    private String category;
    private String ISBN;
    private String edition;
    //private String availabilityStatus; // Available, Borrowed, Reserved

    private List<String> borrowedHistory;
    private List<String> tags;
    private List<String> reviews;
    private List<NotifyObserver> observers = new ArrayList<>();
    private List<Users> reservationQueue = new ArrayList<>();
    private BookState state;
    private LocalDate dueDate;





    // ------------------- Constructor for Simple Books -------------------
    public Book(String bookId, String title, String author, String category, String ISBN, String availabilityStatus) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.ISBN = ISBN;
        //this.availabilityStatus = availabilityStatus;
        this.state = new AvailableState();

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

        //this.availabilityStatus = "Available";

        this.state = new AvailableState();
        this.borrowedHistory = new ArrayList<>();
        this.tags = builder.tags;
        this.reviews = builder.reviews;
    }

    public List<Users> getReservationQueue() {
      return reservationQueue;
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

    public void setState(BookState newState) {
        this.state = newState;
    }

    public BookState getState() {
        return this.state;
    }

    public void cancelReservation(Users user) {
      state.cancelReservation(this, user);
    }

    public LocalDate getDueDate() {
      return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
    }

    //public String getAvailabilityStatus() { return availabilityStatus; }
    //public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
    public List<String> getBorrowedHistory() { return borrowedHistory; }
    public List<String> getTags() { return tags; }
    public List<String> getReviews() { return reviews; }

    // ------------------- Availability -------------------
    public boolean isAvailable() {
        return state.getStatusName().equals("Available");
    }

    public void borrow(String userId, Users user) {
     if (!isAvailable()) {
        System.out.println("Book is not available.");
        return;
     }

     // Update state
     state.borrow(this, user); // just changes state

     // Update borrowed history
     borrowedHistory.add(userId);

     // Set due date
     this.dueDate = LocalDate.now().minusDays(5);

     // Notify only this borrower
     user.update("You borrowed '" + title + "'. Due date: " + dueDate);

     System.out.println(user.getName() + " borrowed: " + title);

     // Remove from reservation queue if present
     reservationQueue.remove(user);
    }


    public void returnBook() {

        //if (!reservationQueue.isEmpty()) {
         //Users nextUser = reservationQueue.get(0);  // first person in queue
         //notifyObservers("Book '" + title + "' is now available for: " + nextUser.getName());
        //}

        state.returnBook(this);

        //availabilityStatus = "Available";
    }

    public void reserve(Users user) {
       // Prevent duplicate reservations
       if (reservationQueue.contains(user)) {
        System.out.println("You have already reserved this book.");
        return;
       }

       // If book is borrowed, add to reservation queue
       if (!isAvailable()) {
        reservationQueue.add(user);
        addObserver(user); // for notifications when book becomes available
        System.out.println("Book is currently borrowed. You are added to the reservation queue.");
        return;
       }

      // Book is available -> change state to Reserved
      state.reserve(this, user);
      reservationQueue.add(user);
      addObserver(user);
      notifyObservers("You reserved the book: " + title);
    }  

    // ------------------- Display Book Details -------------------
    public void displayBook() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Edition: " + edition);
        System.out.println("Status: " + state.getStatusName());
        System.out.println("Tags: " + tags);
        System.out.println("Reviews: " + reviews);
        System.out.println("Borrowed History: " + borrowedHistory);
        System.out.println("----------------------------");
    }

    @Override
    public void addObserver(NotifyObserver observer) {    
     observers.add(observer);
    }

    @Override
    public void removeObserver(NotifyObserver observer) {    
      observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for(NotifyObserver observer : observers) {
            observer.update(message);
        }
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


