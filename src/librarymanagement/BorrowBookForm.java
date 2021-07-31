package librarymanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import models.Book;

/**
 * This form is to be used for borrowing a book
 *
 * @author
 */
public class BorrowBookForm extends JFrame implements ActionListener {

    // Field input for book to be borrowed
    JTextField isbntf;

    BorrowBookForm() {

        // List to display books in stock
        ArrayList<Book> books = getBooks();

        // Coloumns names to be displayed in table of available books
        String column[] = {"ISBN", "NAME", "PAGES", "PRICE"};

        // Model used for operations of table
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        // Setting up model for table
        JTable table = new JTable(tableModel);

        // Displaying data of available books in table
        for (int i = 0; i < books.size(); i++) {
            // Setting object array with each book's data
            Object[] bookDate = {books.get(i).getIsbn(), books.get(i).getName(), books.get(i).getNumOfPages(), books.get(i).getPrice()};
            // Adding that book object to the table
            tableModel.addRow(bookDate);
        }
        table.setBounds(30, 40, 800, 500);

        // Label for ISBN
        JLabel isbn = new JLabel("Enter ISBN of book you wanna borrow:");
        isbn.setBounds(100, 500, 250, 20);
        this.add(isbn);

        // Setting up input field for book ISBN
        isbntf = new JTextField();
        isbntf.setBounds(350, 500, 200, 20);
        this.add(isbntf);

        // Borrow button for performing borrow operation on given book
        JButton borrow = new JButton("Borrow");
        borrow.setBounds(570, 500, 100, 30);
        borrow.addActionListener(this);
        this.add(borrow);

        // Button to get back to readers dashboard
        JButton backToDashboard = new JButton("Back to dashboard");
        backToDashboard.setBounds(200, 600, 200, 30);
        backToDashboard.addActionListener(this);
        this.add(backToDashboard);

        // Adding scroll pane to the table
        JScrollPane sp = new JScrollPane(table);
        this.add(sp);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLocationRelativeTo(null);
        this.setTitle("Borrow a book form");
        this.setSize(800, 700);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        BorrowBookForm b = new BorrowBookForm();
    }

    /**
     * This method is used to retrieve all the books details from the database
     *
     * @return an ArrayList of books containing all the books instances
     * retrieved from database
     */
    public ArrayList<Book> getBooks() {
        ArrayList books = null;
        try {
            books = new ArrayList<>();

            // Getting database connection
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            // Getting all book details from databse
            ResultSet rs = st.executeQuery("select * from books");

            // Adding books in to arraylist one by one
            while (rs.next()) {
                Book a = new Book();
                a.setIsbn(rs.getString("isbn"));
                a.setName(rs.getString("name"));
                a.setNumOfPages(rs.getInt("pages"));
                a.setPrice(rs.getFloat("price"));

                books.add(a);

            }
        } catch (SQLException ex) {
            return books;
        }
        return books;
    }

    /**
     * Method for handling action events taken in frame
     *
     * @param ae referring current frame
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        // If back to dashboard is clicked, go back to ReadersDashboard frame and hide this frame
        if (ae.getActionCommand().equals("Back to dashboard")) {
            this.setVisible(false);
            new ReadersDashboard();
        } // If Borrow button is clicked
        else if (ae.getActionCommand().equals("Borrow")) {

            // If ISBN is empty, ask user to enter ISBN first
            if (isbntf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter isbn id first.");
            } else {
                // Perform book borrowing operation
                boolean bookBorrowed = addBorrowedBook(isbntf.getText());

                // if book borrowed successfully
                if (bookBorrowed) {
                    // Show message
                    JOptionPane.showMessageDialog(null, "Book borrowed successfully.");
                    // Reload the frame 
                    this.setVisible(false);
                    new BorrowBookForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Some problem occored, while borrowing book");

                }
            }

        }
    }

    /**
     * Add borrowed book into borrowed book stock
     *
     * @param isbn to be used for book to be added
     * @return true if operation is successful, false otherwise
     */
    private boolean addBorrowedBook(String isbn) {
        try {

            // Getting databse connection
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            // Getting books' data with given ISBN
            ResultSet rs = st.executeQuery("select * from books where isbn = '" + isbn + "'");
            if (rs.next()) {
                String name = rs.getString("name");
                int pages = rs.getInt("pages");
                float price = rs.getFloat("price");

                Statement st2 = con.createStatement();

                // Insert intt borrowed stock 
                st2.execute("insert into borrowed_books values('" + isbn + "','" + name + "'," + pages + "," + price + "," + SignIN.readerID + ")");

                Statement st3 = con.createStatement();
                // Remove the borrowed book from available stock
                st3.execute("delete from books where isbn = '" + isbn + "'");
                return true;
            }
        } catch (SQLException ex) {

            Logger.getLogger(BorrowBookForm.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }

}
