import java.util.ArrayList;
import java.util.List;

public class Users {

    private String userId;
    private String name;
    private String email;
    private String contactNumber;
    private String membershipType; // Student, Faculty, Guest
    private List<Book> borrowedBooks;

    // ------------------- Constructor -------------------
    public Users(String userId, String name, String email, String contactNumber, String membershipType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.membershipType = membershipType;
        this.borrowedBooks = new ArrayList<>();
    }

    // ------------------- Getters & Setters -------------------
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // ------------------- Borrow & Return Books -------------------
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    // ------------------- Display Borrowed Books -------------------
    public void displayBorrowedBooks() {
        System.out.println("Borrowed books by " + name + ":");
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            for (Book book : borrowedBooks) {
                System.out.println("- " + book.getTitle() + " (" + book.getBookId() + ")");
            }
        }
    }
}

