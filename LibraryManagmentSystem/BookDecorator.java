// Abstract Decorator
public abstract class BookDecorator extends Book {
    protected Book decoratedBook;

    public BookDecorator(Book book) {
        super(book.getBookId(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getISBN(), "Available");
        this.decoratedBook = book;
    }

    @Override
    public void displayBook() {
        decoratedBook.displayBook();
    }

    // You can override other methods if needed
}
