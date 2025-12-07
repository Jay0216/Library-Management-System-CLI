public class RecommendedBook extends BookDecorator {
    public RecommendedBook(Book book) {
        super(book);
    }

    @Override
    public void displayBook() {
        decoratedBook.displayBook();
        System.out.println("Feature: ✅ Recommended Book");
    }
}
