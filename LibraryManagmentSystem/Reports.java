import java.time.LocalDate;
import java.util.List;

public class Reports {

    private int type;          // 1,2,3
    private List<Book> books;
    private List<Users> users;

    public Reports(int type, List<Book> books, List<Users> users) {
        this.type = type;
        this.books = books;
        this.users = users;
    }

    public void generate() {

        System.out.println("\n===== REPORT =====");

        switch (type) {

            case 1:
                System.out.println("Most Borrowed Books");
                generateMostBorrowed();
                break;

            case 2:
                System.out.println("Active Borrowers");
                generateActiveBorrowers();
                break;

            case 3:
                System.out.println("Overdue Books");
                generateOverdueBooks();
                break;

            default:
                System.out.println("Invalid Report Type!");
        }
    }

    private void generateMostBorrowed() {
        System.out.println("\n--- Most Borrowed Books ---");

        books.stream()
                .sorted((b1, b2) -> b2.getBorrowedHistory().size() - b1.getBorrowedHistory().size())
                .forEach(b ->
                        System.out.println(b.getTitle()
                                + " | Borrowed: "
                                + b.getBorrowedHistory().size() + " times")
                );
    }

    private void generateActiveBorrowers() {
        System.out.println("\n--- Active Borrowers ---");

        users.stream()
                .sorted((u1, u2) -> u2.getBorrowCount() - u1.getBorrowCount())
                .forEach(u ->
                        System.out.println(u.getName()
                                + " | Borrowed: "
                                + u.getBorrowCount())
                );
    }

    private void generateOverdueBooks() {
        System.out.println("\n--- Overdue Books ---");

        LocalDate today = LocalDate.now();

        for (Book b : books) {

            if (b.getDueDate() != null && today.isAfter(b.getDueDate())) {

                System.out.println(
                        b.getTitle() +
                        " | Borrower: " + b.getCurrentBorrower().getName() +
                        " | Due: " + b.getDueDate()
                );
            }
        }
    }
}

