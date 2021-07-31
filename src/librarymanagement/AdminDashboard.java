package librarymanagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import models.Book;

/**
 * This class refers to admin dashboard frame with admin operations
 *
 * @author 
 */
public class AdminDashboard extends JFrame implements ActionListener {

    AdminDashboard() {

        // Setting title for dashboard frame
        this.setTitle("Admin Dashboard");
        // Setting size for frame
        this.setSize(600, 700);

        // Adding welcome label to frame, setting bounds and changing font family and size.
        JLabel welcome = new JLabel("Welcome to dashboard, Admin");
        welcome.setBounds(100, 50, 300, 40);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
        this.add(welcome);

        // Adding button to be used to viewing all books in the stock
        JButton viewBooks = new JButton("View all books");
        viewBooks.setBounds(230, 150, 150, 30);
        // setting button color to white
        viewBooks.setBackground(Color.white);
        // Adding action listener to work on button click
        viewBooks.addActionListener(this);
        this.add(viewBooks);

        // Adding button to be used to add new book in the stock, setting bounds, 
        //setting white color, adding action to listen on button click, and finally adding it to frame
        JButton addBook = new JButton("Add new book");
        addBook.setBounds(230, 250, 150, 30);
        addBook.setBackground(Color.white);
        addBook.addActionListener(this);
        this.add(addBook);

        // Adding button to be used to remove book from the stock, setting bounds, 
        //setting white color, adding action to listen on button click, and finally adding it to frame
        JButton removeBook = new JButton("Remove a book");
        removeBook.setBounds(230, 350, 150, 30);
        removeBook.setBackground(Color.white);
        removeBook.addActionListener(this);
        this.add(removeBook);

        // Adding button to be used to update book in the stock, setting bounds, 
        //setting white color, adding action to listen on button click, and finally adding it to frame
        JButton updateBook = new JButton("Update a book");
        updateBook.setBounds(230, 450, 150, 30);
        updateBook.setBackground(Color.white);
        updateBook.addActionListener(this);
        this.add(updateBook);

        // Adding button to be used for logging out the admin, setting bounds, 
        //setting white color, adding action to listen on button click, and finally adding it to frame
        JButton logout = new JButton("Log out");
        logout.setBounds(230, 550, 150, 30);
        logout.setBackground(Color.red);
        logout.setForeground(Color.white);
        logout.addActionListener(this);
        this.add(logout);

        // Setting default close operation for frame to be closed completely when frame is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting layout null to set components' bounds manually
        this.setLayout(null);
        // Setting visibility of frame 
        this.setVisible(true);
        // Making frame to unable resizability
        this.setResizable(false);
        // Making frame to display at center
        this.setLocationRelativeTo(null);

    }

    /**
     * This method is used to listen to all action events caused in this frame
     *
     * @param ae This refers to current action event called by frame
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        // If add new book button is clicked, go to AddBook frame and hide this frame
        if (ae.getActionCommand().equals("Add new book")) {
            this.setVisible(false);
            new AddBook(null);
        } // If view all books button is clicked, go to ShowAllBooks frame and hide this frame
        else if (ae.getActionCommand().equals("View all books")) {
            this.setVisible(false);
            new ShowAllBooks();
        } // If add new book button is clicked, go to AddBook frame by passing the book reference to be updated and hide this frame
        else if (ae.getActionCommand().equals("Update a book")) {
            // Take ISBN of book, admin want to update as input
            String m = JOptionPane.showInputDialog("Enter book ISBN you want to update?");

            // Get book reference with ISBN taken as input
            Book a = getBook(m);

            if (a != null) {
                new AddBook(a);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Book with ISBN " + m + " couldn't be found.");

            }

        } // If Remove a book button is clicked
        else if (ae.getActionCommand().equals("Remove a book")) {
            // Take ISBN of book, admin want to remove as input
            String m = JOptionPane.showInputDialog("Enter book ISBN you want to delete?");

            // Delete the book given ISBN
            boolean bookDeleted = deleteBook(m);
            if (bookDeleted) {
                JOptionPane.showMessageDialog(null, "Book with ISBN " + m + " deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Some problem occured while deleting book, try again.");

            }
        } // If Log out is clicked, hide dashboard and go back to Login frame
        else if (ae.getActionCommand().equals("Log out")) {
            this.setVisible(false);
            new SignIN();
        }
    }

    /**
     * Method to get the reference of book with given ISBN
     *
     * @param isbn to be used to find book
     * @return book reference with details
     */
    public Book getBook(String isbn) {
        try {

            // Getting database connection reference
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            // Getting details of book with given ISBN
            ResultSet rs = st.executeQuery("select * from books where isbn='" + isbn + "'");

            // If query is successfull
            while (rs.next()) {

                // Initialize book reference
                Book a = new Book();
                // Set up the data from databse into reference
                a.setIsbn(rs.getString("isbn"));
                a.setName(rs.getString("name"));
                a.setNumOfPages(rs.getInt("pages"));
                a.setPrice(rs.getFloat("price"));

                // Return book reference
                return a;

            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    /**
     * Method to delete the reference of book with given ISBN
     *
     * @param isbn to be used to find book
     * @return true if book deleted otherwise false
     */
    private boolean deleteBook(String isbn) {
        try {

            // Getting database reference
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            // Executing delete query with book with given ISBN
            st.execute("delete from books where isbn='" + isbn + "'");
        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

}
