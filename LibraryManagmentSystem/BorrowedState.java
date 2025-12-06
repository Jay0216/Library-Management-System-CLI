public class BorrowedState implements BookState {

    @Override
    public void borrow(Book book, Users user) {
        System.out.println("Book is already borrowed.");
    }

    @Override
    public void returnBook(Book book) {

        if (!book.getReservationQueue().isEmpty()) {
            Users next = book.getReservationQueue().get(0);
            book.notifyObservers("Book returned and available for: " + next.getName());
            book.setState(new ReservedState());
        } else {
            book.setState(new AvailableState());
        }
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
        return "Borrowed";
    }
}

