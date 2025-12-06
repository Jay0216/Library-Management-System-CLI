public class AvailableState implements BookState {

    @Override
    public void borrow(Book book, Users user) {
        book.setState(new BorrowedState());
        book.getBorrowedHistory().add(user.getUserId());
        book.notifyObservers("Book borrowed: " + book.getTitle());
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("Book is already available.");
    }

    @Override
    public void reserve(Book book, Users user) {
        book.getReservationQueue().add(user);
        book.addObserver(user);
        book.setState(new ReservedState());
        book.notifyObservers("You reserved: " + book.getTitle());
    }

    @Override
    public String getStatusName() {
        return "Available";
    }
}

