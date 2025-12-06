import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagmentSystem {

    
    private List<Users> users = new ArrayList<>();
    private List<Reservations> reservations = new ArrayList<>();
    private List<Reports> reports = new ArrayList<>();

    private CommandInvoker commandInvoker;
    private Librarian librarian;
    private Scanner scanner;

    public LibraryManagmentSystem() {
        scanner = new Scanner(System.in);
        users = new ArrayList<>();
        reports = new ArrayList<>();
        reservations = new ArrayList<>();
        commandInvoker = new CommandInvoker();
        librarian = new Librarian(); 
    }

    // -------------------------------------------------------------
    // MAIN MENU
    // -------------------------------------------------------------

    public void mainMenu() {
        int choice;
        do {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. System Mode");
            System.out.println("2. Librarian Mode");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> systemMenu();
                case 2 -> librarianLoginMenu();
                case 0 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ------------------- SYSTEM MODE -------------------
    private void systemMenu() {
        int choice;
        do {
            System.out.println("\n=== User System ===");
            System.out.println("1. Register Users");
            System.out.println("2. View Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Reserve Book");
            System.out.println("6. View Reservations");
            System.out.println("7. View Notifications");
            System.out.println("0. Back");

            System.out.print("Choose: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerUsers();
                case 2 -> viewBooks();
                case 3 -> borrowBookMenu();
                case 4 -> returnBookMenu();
                case 5 -> reserveBookMenu();
                case 6 -> viewReservations();
                case 7 -> viewNotifications();
                case 0 -> System.out.println("Back...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    // ------------------- LIBRARIAN LOGIN & MENU -------------------
    private void librarianLoginMenu() {
        System.out.print("Enter username: ");
        String user = scanner.nextLine();
        System.out.print("Enter password: ");
        String pass = scanner.nextLine();

        if (!librarian.login(user, pass)) {
            System.out.println("Invalid login!");
            return;
        }

        librarianMenu();
    }

    private void librarianMenu() {
        int choice;
        do {
            System.out.println("\n=== Librarian Mode ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add Complex Books (with Metadata)");
            System.out.println("3. Update Book");
            System.out.println("4. Remove Book");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> librarian.addBook();
                case 2 -> librarian.addComplexBooks();
                case 3 -> librarian.updateBook();
                case 4 -> librarian.removeBook();
                case 0 -> System.out.println("Back...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    // ------------------- COMMAND-BASED USER ACTIONS -------------------
    private void borrowBookMenu() {
        Users user = chooseUser();
        Book book = chooseBook();

        BorrowBookCommand cmd = new BorrowBookCommand(this, user, book);
        commandInvoker.executeCommand(cmd);
    }

    private void returnBookMenu() {
        Users user = chooseUser();
        Book book = chooseBook();

        ReturnBookCommand cmd = new ReturnBookCommand(this, user, book);
        commandInvoker.executeCommand(cmd);
    }

    private void reserveBookMenu() {
        Users user = chooseUser();
        Book book = chooseBook();

        ReserveBookCommand cmd = new ReserveBookCommand(this, user, book);
        commandInvoker.executeCommand(cmd);
    }

    // ------------------- USER ACTIONS -------------------
    public void borrowBook(Users user, Book book) {
        //if (!book.isAvailable()) {
           // System.out.println("Book is not available.");
           // return;
        //}
        //book.setAvailable(false);
        book.borrow(user.getUserId(), user);


    }

    public void returnBook(Users user, Book book) {

       LocalDate dueDate = book.getDueDate();  
       LocalDate today = LocalDate.now();

       long lateDays = 0;

       if (today.isAfter(dueDate)) {
        lateDays = ChronoUnit.DAYS.between(dueDate, today);
       }

      // ----------- Strategy Selection (NO FACTORY) ----------
      FineStrategy strategy;

      switch (user.getMembershipType().toLowerCase()) {
        case "student":
            strategy = new StudentStrategy();
            break;
        case "faculty":
            strategy = new FacultyStrategy();
            break;
        case "guest":
            strategy = new GuestStrategy();
            break;
        default:
            System.out.println("Unknown membership type!");
            return;
       }

      // ----------- Calculate Fine -----------
      double fine = strategy.calculateFine(lateDays);

      System.out.println(user.getName() + " returned: " + book.getTitle());

      if (lateDays > 0) {
        System.out.println("Late by " + lateDays + " days. Fine: LKR " + fine);
      } else {
        System.out.println("Returned on time. No fine.");
      }

      // Update user and book
      user.returnBook(book);
      book.returnBook();
    }

    
    public void reserveBook(Users user, Book book) {
      // Validation: check if book is borrowed or already reserved
      if (!book.isAvailable() && book.getReservationQueue().contains(user)) {
        System.out.println("You have already reserved this book.");
        return;
      } 
      if (!book.isAvailable() && !book.getReservationQueue().contains(user)) {
        System.out.println("Book is currently borrowed. You can reserve it if it's available later.");
        // Optional: automatically add to reservation queue
        // book.reserve(user);
        // Reservations r = new Reservations(user, book);
        // reservations.add(r);
        return;
      }

      if (book.getReservationQueue().contains(user)) {
        System.out.println("You have already reserved this book.");
        return;
      }

      // Reserve the book properly
      book.reserve(user);  // updates state, adds observer, notifies user

      // Add to reservation list
      Reservations r = new Reservations(user, book);
      reservations.add(r);

      System.out.println("Reservation successful for book: " + book.getTitle());
    }

    


    // ------------------- HELPER METHODS -------------------
    private Users chooseUser() {
        System.out.println("Choose user:");
        for (int i = 0; i < users.size(); i++)
            System.out.println(i + ": " + users.get(i).getName());
        return users.get(scanner.nextInt());
    }

    private Book chooseBook() {
        System.out.println("Choose book:");
        List<Book> librarianBooks = librarian.getBooks(); // Use librarian-owned books
        for (int i = 0; i < librarianBooks.size(); i++)
            System.out.println(i + ": " + librarianBooks.get(i).getTitle());
        return librarianBooks.get(scanner.nextInt());
    }

    

    

    // OTHER UNIMPLEMENTED METHODS
    private void registerUsers() {

       System.out.println("\n=== Register New User ===");

       System.out.print("Enter User ID: ");
       String id = scanner.nextLine();

       // Check duplicate ID
       if (findUserById(id) != null) {
           System.out.println("A user with this ID already exists!");
           return;
       }

       System.out.print("Enter Name: ");
       String name = scanner.nextLine();

       System.out.print("Enter Email: ");
       String email = scanner.nextLine();

       System.out.print("Enter Contact Number: ");
       String contact = scanner.nextLine();

       System.out.println("Choose Membership Type:");
       System.out.println("1. Student");
       System.out.println("2. Faculty");
       System.out.println("3. Guest");
       System.out.print("Enter choice: ");

       int typeChoice = scanner.nextInt();
       scanner.nextLine(); // Consume newline

       String membership;

       switch (typeChoice) {
           case 1 -> membership = "Student";
           case 2 -> membership = "Faculty";
           case 3 -> membership = "Guest";
           default -> {
               System.out.println("Invalid membership type!");
               return;
           }
       }

       // Create user
       Users newUser = new Users(id, name, email, contact, membership);

       // Add to list
       users.add(newUser);

       System.out.println("User registered successfully!");
    }
    
    private void viewBooks() {
        System.out.println("\n=== Available Books ===");

    // Check if librarian has any books
    if (librarian.getBooks().isEmpty()) {
        System.out.println("No books available in the library!");
        return;
    }

    // Display all books
    for (Book b : librarian.getBooks()) {
        System.out.println("Book ID: " + b.getBookId());
        System.out.println("Title: " + b.getTitle());
        System.out.println("Author: " + b.getAuthor());
        System.out.println("Category: " + b.getCategory());
        System.out.println("ISBN: " + b.getISBN());
        System.out.println("Availability: " + b.getState().getStatusName());
        System.out.println("-----------------------------");
    }
    }
    private void viewReservations() {
        System.out.println("\n=== All Reservations ===");

        if (reservations.isEmpty()) {
         System.out.println("No reservations found.");
         return;
        }

        for (Reservations r : reservations) {
         System.out.println("User: " + r.getUser().getName() +
                           " | Book: " + r.getBook().getTitle() +
                           " | Reservation Date: " + r.getReservationDate() +
                           " | Book Status: " + r.getBook().getState().getStatusName());
        }
    }
    private void viewNotifications() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        Users user = findUserById(userId);

        if (user == null) {
         System.out.println("User not found.");
         return;
        }

        user.viewNotifications();
    }


    // ------------------- Find User By ID -------------------
    private Users findUserById(String userId) {
     for (Users user : users) {
        if (user.getUserId().equalsIgnoreCase(userId)) {
            return user;
        }
     }
     return null;
    }



    // Find reservation
public Reservations getReservation(Users user, Book book) {
    for (Reservations r : reservations) {
        if (r.getUser() == user && r.getBook() == book) {
            return r;
        }
    }
    return null;
}

// Cancel reservation
public void cancelReservation(Reservations r) {
    //reservations.remove(r);

    Book book = r.getBook();
    Users user = r.getUser();

    // Let the book's state handle cancellation
    book.cancelReservation(user);

    // Remove reservation from system list
    reservations.remove(r);
    //r.getBook().setAvailabilityStatus("Available");
}

// Add reservation (for undo)
public void addReservation(Reservations r) {
    reservations.add(r);
    r.getBook().reserve(r.getUser());
    //r.getBook().setAvailabilityStatus("Reserved");
}


public static void main(String args[]){
    LibraryManagmentSystem system = new LibraryManagmentSystem();
    system.mainMenu();
}

}

