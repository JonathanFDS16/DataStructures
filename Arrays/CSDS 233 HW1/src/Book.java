/**
 * Class that represents a book with title, ISBN, and author
 * @author Jonathan F DaSilva
 */
public class Book {

    /**
     * Title of the book
     */
    private String title;

    /**
     * ISBN of the book
     */
    private String isbn;

    /**
     * Author of the book
     */
    private String author;

    /**
     * Creates an instance of a book
     * @param title title of the book
     * @param isbn isbn number of book
     * @param author author of the book
     */
    public Book(String title, String isbn, String author) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        if (isbn.length() != 13) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the title of the book
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the ISBN of the book
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the author of the book
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

}
