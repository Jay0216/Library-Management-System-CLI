public class SpecialEditionBook extends BookDecorator {
    public SpecialEditionBook(Book book) {
        super(book);
    }

    @Override
    public void displayBook() {
        decoratedBook.displayBook();
        System.out.println("Feature: 🌟 Special Edition");
    }
}
