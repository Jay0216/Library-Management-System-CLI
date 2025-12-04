public class BorrowBookCommand implements Command {

    private LibraryManagmentSystem system;
    private Users user;
    private Book book;

    public BorrowBookCommand(LibraryManagmentSystem system, Users user, Book book) {
        this.system = system;
        this.user = user;
        this.book = book;
    }

    @Override
    public void execute() {
        system.borrowBook(user, book);
    }

    @Override
    public void undo() {
        system.returnBook(user, book);
        System.out.println("Undo borrow: " + book.getTitle() + " returned.");
    }
}

