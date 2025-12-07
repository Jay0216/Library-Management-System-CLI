public class FeaturedBook extends BookDecorator {
    public FeaturedBook(Book book) {
        super(book);
    }

    @Override
    public void displayBook() {
        decoratedBook.displayBook();
        System.out.println("Feature: ★ Featured Book ★");
    }
}
