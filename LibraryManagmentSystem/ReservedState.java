public class ReservedState implements BookState {

    @Override
    public void borrow(Book book, Users user) {
        System.out.println("Book is reserved. Cannot borrow.");
    }

    @Override
    public void returnBook(Book book) {
        if (!book.getReservationQueue().isEmpty()) {
            Users next = book.getReservationQueue().remove(0);
            book.notifyObservers("Reserved book ready for: " + next.getName());
            book.setState(new AvailableState());
        } else {
            book.setState(new AvailableState());
        }
    }

    @Override
    public void reserve(Book book, Users user) {
        book.getReservationQueue().add(user);
        book.addObserver(user);
        book.notifyObservers("Added to reservation queue: " + book.getTitle());
    }

    @Override
    public String getStatusName() {
        return "Reserved";
    }

    @Override
    public void cancelReservation(Book book, Users user) {
     book.getReservationQueue().remove(user);
     book.removeObserver(user);

     if (book.getReservationQueue().isEmpty()) {
        book.setState(new AvailableState());
        book.notifyObservers("Reservation cancelled. Book is now AVAILABLE.");
     } else {
        Users next = book.getReservationQueue().get(0);
        book.notifyObservers("Reservation cancelled. Next user in queue: " + next.getName());
     }
    }

}

