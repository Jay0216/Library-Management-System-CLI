public class ReturnBookCommand implements Command {

    private LibraryManagmentSystem system;
    private Users user;
    private Book book;

    public ReturnBookCommand(LibraryManagmentSystem system, Users user, Book book) {
        this.system = system;
        this.user = user;
        this.book = book;
    }

    @Override
    public void execute() {
        system.returnBook(user, book);
    }

    @Override
    public void undo() {
        system.borrowBook(user, book);
        System.out.println("Undo return: " + book.getTitle() + " borrowed again.");
    }
}
