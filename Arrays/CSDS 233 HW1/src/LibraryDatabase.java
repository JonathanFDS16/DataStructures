public class LibraryDatabase {

    private Book[] array;

    private int capacity;

    private int size;

    /**
     * Creates an instance of the library
     * @param capacity The capacity of the library
     */
    public LibraryDatabase(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException();
        this.capacity = capacity;
        array = new Book[capacity];

    }

    /**
     * Add a book to the library
     * @param book the book to be added in the database
     */
    public void add(Book book) {
        long isbn = Long.parseLong(book.getIsbn());

        //If array is empty it will add the book in the first index
        if (array[0] == null) {
            array[0] = book;
            size += 1;
        }
        else {
            //If array is full, this creates a bigger array to fit the next book
            if (array[getCapacity() - 1] != null) {
                Book[] newArray = new Book[getCapacity() + 1];          //New array with bigger capacity
                for (int i = 0; i < getCapacity(); i++) {               //Copy each element of an array to another
                    newArray[i] = array[i];
                }
                setCapacity(getCapacity() + 1);                         //Set a new capacity for the array.
                array = newArray;                                       //Makes the library the newArray.
            }

            //For loop compares the isbn of the books to place them in order.
            for (int i = getCapacity() - 1; i >= 0 ; i--) {
                if (array[i] != null) {                                 //If current index have a book
                    if (isbn < Long.parseLong(array[i].getIsbn())) {    //If this.isbn is less than current index
                            array[i + 1] = array[i];                    //Copy current to i + 1
                            array[i] = book;                            //Make current equal to book
                    }
                    else if (array[i + 1] == null) {                    //If next space is empty place book there
                        array[i + 1] = book;
                    }
                }
            }
            size += 1;                                                  //Add 1 to the size of the library
        }
    }

    /**
     * Removes a book with same ISBN from the library.
     * @param isbn The ISBN of the book to be removed
     * @return The book removed or null if no book was removed with the given ISBN.
     */
    public Book remove(String isbn) {
        Book save = null;
        for (int i = 0; i < getCapacity(); i++) {                       //Loop through every element in library
            if (array[i] != null && array[i].getIsbn().equals(isbn)) {  //If current have a book and same ISBN's
                save = array[i];                                        //Save the book
                array[i] = null;                                        //Empty the space
                size -= 1;                                              //Subtract one from the size
            }
            else if (i != 0 && array[i - 1] == null) {                  //If space behind is empty
                array[i - 1] = array[i];                                //Move i - 1 spaces
                array[i] = null;
            }
        }
        return save;                                                    //Return the removed book
    }

    /**
     * Returns the size of the library.
     * @return Return the amount of books stores in the library.
     */
    public int size() {
        return size;
    }

    /**
     * Selects randomly a book from the library.
     * @return A random book from the database.
     */
    public Book randomSelection() {
        if (size() == 0)                             //If there is no books on the library
            return null;
        int random = (int)(Math.random() * size());  //Gets an int depending on how many books are in the library.
        return array[random];                           //Returns the random book
    }

    /**
     * Find a book by its given title.
     * @param title The title of the book to be found.
     * @return the Book with the given title or null if there is no match in the database.
     */
    public Book findByTitle(String title) {
        for (int i = 0; i < getCapacity() - 1; i++) {
            if (array[i].getTitle().equals(title))
                return array[i];
        }
        return null;
    }

    /**
     * Find a book by its ISBN
     * @param isbn The given ISBN
     * @return Return the book with the given ISBN or null if there is no match.
     */
    public Book findByISBN(String isbn) {
        int begin = 0;
        int end = getCapacity() - 1;
        while (begin <= end) {
            int mp = (begin + end) / 2;
            if (array[mp].getIsbn().equals(isbn)) {
                return array[mp];
            } else if (array[mp].getIsbn().compareTo(isbn) > 0) {
                end = mp - 1;
            } else {
                begin = mp + 1;
            }
        }
        return null;
    }

    /**
     * Return an array with all books from a given author.
     * @param author The author
     * @return An array of books from the given author.
     */
    public Book[] getAllByAuthor(String author) {
        Book[] books = new Book[getCapacity()];
        for (int i = 0, j = 0; i < getCapacity(); i++) {
            if (array[i].getAuthor().equals(author))
                books[j++] = array[i];
        }
        return books;
    }

    /**
     * Removes books that have repeated ISBN.
     */
    public void removeDuplicates() {
        Book[] noDuplicates = new Book[getCapacity()];
        for (int i = 0, j = 0; i < getCapacity(); i++) {
            //If current and next have same ISBN
            if (i != getCapacity() - 1 && array[i].getIsbn().equals(array[i + 1].getIsbn())) {
                size -= 1;                      //Subtract 1 from size
            }
            else {
                noDuplicates[j++] = array[i];   //Only adds the ones that are not duplicate
            }
        }
        array = noDuplicates;                   //Sets the library without duplicates
    }

    /**
     * Return the Library array.
     * @return An array with all books of the library sorted.
     */
    public Book[] toArray() {
        return array;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
