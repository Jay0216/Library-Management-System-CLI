public class AvailableState implements BookState {

    @Override
    public void borrow(Book book, Users user) {
        book.setState(new BorrowedState());
        //book.getBorrowedHistory().add(user.getUserId());
        book.notifyObservers("Book borrowed: " + book.getTitle());
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("Book is already available.");
    }

    @Override
    public void reserve(Book book, Users user) {
      if (!book.getReservationQueue().contains(user)) {
        book.getReservationQueue().add(user);
        System.out.println(user.getName() + " added to reservation queue for: " + book.getTitle());
      } else {
        System.out.println(user.getName() + " has already reserved this book.");
      }
      book.setState(new ReservedState());
    }

    @Override
    public String getStatusName() {
        return "Available";
    }
}

