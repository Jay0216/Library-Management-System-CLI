public class BorrowedState implements BookState {

    @Override
    public void borrow(Book book, Users user) {
        System.out.println("Book is already borrowed.");
    }

    @Override
    public void returnBook(Book book) {

        if (!book.getReservationQueue().isEmpty()) {
         Users next = book.getReservationQueue().get(0); // peek first user
         next.update("Book returned and ready for you to borrow: " + book.getTitle());
         System.out.println("Notification sent to " + next.getName());
         book.setState(new ReservedState()); // Book now reserved for first user
        } else {
         book.setState(new AvailableState());
        }
    }

    @Override
    public void reserve(Book book, Users user) {
        if (!book.getReservationQueue().contains(user)) {
            book.getReservationQueue().add(user);
            System.out.println(user.getName() + " reserved: " + book.getTitle());
        } else {
            System.out.println(user.getName() + " has already reserved this book.");
        }
        book.setState(new ReservedState());
    }

    @Override
    public String getStatusName() {
        return "Borrowed";
    }
}

