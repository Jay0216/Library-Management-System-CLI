public interface BookState {
    void borrow(Book book, Users user);
    void returnBook(Book book);
    void reserve(Book book, Users user);
    String getStatusName();
    default void cancelReservation(Book book, Users user) {
      System.out.println("Cancellation not allowed in this state.");
    }
}


