package models;

/**
 * This class represents reference of book
 *
 * @author
 */
public class Book {

    // Required attributes for Book to be used in the system
    String isbn;
    String name;
    float price;
    int numOfPages;

    public Book() {

    }

    // Getters and setters to setting and getting values of object's attributes
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    @Override
    public String toString() {
        return "Book{" + "isbn=" + isbn + ", name=" + name + ", price=" + price + ", numOfPages=" + numOfPages + '}';
    }

}
