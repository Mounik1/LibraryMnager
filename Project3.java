package thirdProject;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class Book implements Serializable {
    private String title;
    private String author;
    private int yearPublished;
    private String genre;

    public Book(String title, String author, int yearPublished, String genre) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Year Published: " + yearPublished + ", Genre: " + genre;
    }
}

public class Project3 {
    private ArrayList<Book> books;
    private static final String FILE_PATH = "C:\\Google\\Book";

    public Project3() {
        books = new ArrayList<>();
        loadLibrary();
    }

    public void addBook(Book book) {
        books.add(book);
        saveLibrary();
    }

    public void removeBook(String titleOrAuthor) {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTitle().equalsIgnoreCase(titleOrAuthor) || book.getAuthor().equalsIgnoreCase(titleOrAuthor)) {
                iterator.remove();
            }
        }
        saveLibrary();
    }

    public ArrayList<Book> searchBooks(String keyword) {
        ArrayList<Book> results = new ArrayList<>(); 
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) || book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) || String.valueOf(book.getYearPublished()).contains(keyword.toLowerCase()) || book.getGenre().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public void listAllBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void saveLibrary() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLibrary() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            books = (ArrayList<Book>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            
            books = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        Project3 libraryManager = new Project3();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add a Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. Search for a Book");
            System.out.println("4. List All Books");
            System.out.println("5. Exit");
            System.out.println("------------------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: {
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter year published: ");
                    int yearPublished = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    libraryManager.addBook(new Book(title, author, yearPublished, genre));
                    System.out.println("Book added successfully.");
                    System.out.println("------------------------------------------");
                    break;
                }
                case 2: {
                    System.out.print("Enter Book title or Author name to remove: ");
                    String titleOrAuthorToRemove = scanner.nextLine();
                    libraryManager.removeBook(titleOrAuthorToRemove);
                    System.out.println("Book removed successfully.");
                    System.out.println("------------------------------------------");
                    break;
                }
                case 3: {
                    System.out.print("Enter search keyword: ");
                    String keyword = scanner.nextLine();
                    ArrayList<Book> searchResults = libraryManager.searchBooks(keyword);
                    if (searchResults.isEmpty()) {
                        System.out.println("No matching books found.");
                        System.out.println("------------------------------------------");
                    } else {
                        System.out.println("Search results:");
                        for (Book book : searchResults) {
                            System.out.println(book);
                            System.out.println("------------------------------------------");
                        }
                    }
                    break;
                }
                case 4: {
                    System.out.println("All Books:");
                    libraryManager.listAllBooks();
                    System.out.println("------------------------------------------");
                    break;
                }
                case 5: {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default: {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }
        }
    }
}