import java.util.List;
import java.util.Scanner;

public class Reservations {

    private Users user;       // The user who reserved the book
    private Book book;        // The reserved book
    private String reservationDate;


    private static Scanner scanner = new Scanner(System.in);

    // ------------------- CONSTRUCTOR -------------------
    public Reservations(Users user, Book book) {
        this.user = user;
        this.book = book;
        this.reservationDate = java.time.LocalDate.now().toString();
    }

    // ------------------- GETTERS -------------------
    public Users getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    // ------------------- HELPER METHODS -------------------

    // Choose a user from the list
    public static Users chooseUser(List<Users> users) {
        System.out.println("Choose user:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + ": " + users.get(i).getName());
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return users.get(choice);
    }

    // Choose a book from the librarian's book list
    public static Book chooseBook(Librarian librarian) {
        System.out.println("Choose book:");
        List<Book> librarianBooks = librarian.getBooks();
        for (int i = 0; i < librarianBooks.size(); i++) {
            System.out.println(i + ": " + librarianBooks.get(i).getTitle());
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return librarianBooks.get(choice);
    }

    // ------------------- DISPLAY RESERVATIONS -------------------
    public static void displayReservations(List<Reservations> reservations) {
        System.out.println("=== All Reservations ===");
        for (Reservations r : reservations) {
            System.out.println("User: " + r.getUser().getName() +
                    " | Book: " + r.getBook().getTitle() +
                    " | Date: " + r.getReservationDate());
        }
    }

    // Optionally, add this reservation to the static reservation list
    public void addToReservationList(List<Reservations> reservations) {
        reservations.add(this);
        System.out.println("Reservation added for book: " + book.getTitle());
    }
}

