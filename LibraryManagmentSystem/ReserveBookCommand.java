public class ReserveBookCommand implements Command {

    private LibraryManagmentSystem system;
    private Users user;
    private Book book;
    private Reservations reservation;

    public ReserveBookCommand(LibraryManagmentSystem system, Users user, Book book) {
        this.system = system;
        this.user = user;
        this.book = book;
    }

    @Override
    public void execute() {
        system.reserveBook(user, book);
    }

    @Override
    public void undo() {
        reservation = system.getReservation(user, book);
        if (reservation != null) {
            system.cancelReservation(reservation);
            System.out.println("Undo reservation: " + book.getTitle() + " reservation cancelled.");
        }
    }
}

