import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Librarian {

    private final String username = "admin";
    private final String password = "ad1234";

    private List<Book> books;     // Composition: Librarian OWNS the books
    private Scanner scanner;

    public Librarian() {
        books = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Authentication
    public boolean login(String user, String pass) {
        return username.equals(user) && password.equals(pass);
    }

    // ADD BOOK
    public void addBook() {
        System.out.println("=== Add Book ===");
        
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        Book book = new Book(id, title, author, category, isbn);
        books.add(book);

        System.out.println("Book added successfully!");
    }

    // REMOVE BOOK
    public void removeBook() {
        System.out.print("Enter Book ID to remove: ");
        String id = scanner.nextLine();

        Book found = null;

        for (Book b : books) {
            if (b.getBookId().equals(id)) {
                found = b;
                break;
            }
        }

        if (found != null) {
            books.remove(found);
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }

    // UPDATE BOOK
    public void updateBook() {
        System.out.print("Enter Book ID to update: ");
        String id = scanner.nextLine();

        Book found = null;

        for (Book b : books) {
            if (b.getBookId().equals(id)) {
                found = b;
                break;
            }
        }

        if (found == null) {
            System.out.println("Book not found!");
            return;
        }

        System.out.print("Enter new title: ");
        found.setTitle(scanner.nextLine());

        System.out.print("Enter new category: ");
        found.setCategory(scanner.nextLine());

        System.out.print("Enter new author: ");
        found.setAuthor(scanner.nextLine());

        System.out.println("Book updated successfully!");
    }

    // DISPLAY BOOKS
    public void displayBooks() {
        System.out.println("\n=== Books in Library ===");

        if (books.isEmpty()) {
            System.out.println("No books available!");
            return;
        }

        for (Book b : books) {
            System.out.println(b);
        }
    }

    // Allow system class to see the books
    public List<Book> getBooks() {
        return books;
    }


    public void addComplexBooks(){

         System.out.println("=== Add Complex Book (Using Builder) ===");

         System.out.print("Enter Book ID: ");
         String id = scanner.nextLine();

         System.out.print("Enter Title: ");
         String title = scanner.nextLine();

         System.out.print("Enter Author: ");
         String author = scanner.nextLine();

         // Start Builder with mandatory fields
         Book.BookBuilder builder = new Book.BookBuilder(id, title, author);

         // ---- Optional Fields ----

         System.out.print("Enter Category (or leave empty): ");
         String category = scanner.nextLine();
         if (!category.isEmpty()) {
          builder.category(category);
         }

         System.out.print("Enter ISBN (or leave empty): ");
         String isbn = scanner.nextLine();
         if (!isbn.isEmpty()) {
           builder.ISBN(isbn);
         }

         System.out.print("Enter Edition (or leave empty): ");
         String edition = scanner.nextLine();
         if (!edition.isEmpty()) {
           builder.edition(edition);
         }

         // ---- Add Tags ----
         System.out.println("Add Tags? (y/n): ");
         String addTags = scanner.nextLine();

         if (addTags.equalsIgnoreCase("y")) {
           while (true) {
            System.out.print("Enter Tag (or type 'done'): ");
            String tag = scanner.nextLine();

            if (tag.equalsIgnoreCase("done")) break;

            builder.addTag(tag);
          }
        }

        // ---- Add Reviews ----
        System.out.println("Add Reviews? (y/n): ");
        String addReviews = scanner.nextLine();

        if (addReviews.equalsIgnoreCase("y")) {
          while (true) {
             System.out.print("Enter Review (or type 'done'): ");
             String review = scanner.nextLine();

             if (review.equalsIgnoreCase("done")) break;

             builder.addReview(review);
            }
        }

        // ---- Build Final Complex Book ----
        Book complexBook = builder.build();

        books.add(complexBook);
        System.out.println("Complex Book added successfully!");

    }

    public void addDecoratedBook() {
      System.out.println("=== Add Book with Features ===");

      System.out.print("Enter Book ID: ");
      String id = scanner.nextLine();

      System.out.print("Enter Title: ");
      String title = scanner.nextLine();

      System.out.print("Enter Author: ");
      String author = scanner.nextLine();

      Book book = new Book(id, title, author, "", "");

      System.out.print("Add features? (1=Featured, 2=Recommended, 3=Special Edition, 0=No): ");
      String choice = scanner.nextLine();

      switch (choice) {
          case "1":
              book = new FeaturedBook(book);
              break;
          case "2":
              book = new RecommendedBook(book);
              break;
          case "3":
              book = new SpecialEditionBook(book);
              break;
          default:
              break;
      }

     books.add(book);
     System.out.println("Decorated book added successfully!");
    }

    public void viewGeneratedReports(List<Reports> reports) {
      System.out.println("\n=== All Generated Reports ===");

      if (reports.isEmpty()) {
        System.out.println("No reports generated yet.");
        return;
      }

      int index = 1;
      for (Reports r : reports) {
        System.out.println("\nReport #" + index++);
        r.generate();   // Polymorphic call
        System.out.println("-------------------------------");
      }
    }

}




