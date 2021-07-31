package librarymanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Book;


/**
 * This form is used for displaying borrowed books
 * @author ADMIN
 */
public class SeeBorrowedBooksForm extends JFrame implements ActionListener{

    SeeBorrowedBooksForm() {

       
        // List to store borrowed books
        ArrayList<Book> books = getBooks();

        System.out.println(books);
        // Column names in table
        String column[] = {"ISBN", "NAME", "PAGES", "PRICE"};

        // Model to perform table operations upon
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        

        // Adding model to the table
        JTable table = new JTable(tableModel);

        // Populating table with book references from the list
        for (int i = 0; i < books.size(); i++) {
            Object[] bookDate = {books.get(i).getIsbn(), books.get(i).getName(), books.get(i).getNumOfPages(), books.get(i).getPrice()};
            tableModel.addRow(bookDate);
        }
        table.setBounds(30, 40, 700, 400);
        
        
        // Setting up button to be used to return to the dashboard, setting bounds, adding click listener, and adding to the frame
        JButton backToDashboard = new JButton("Back to dashboard");
        backToDashboard.setBounds(200, 400, 200, 30);
        backToDashboard.addActionListener(this);
        this.add(backToDashboard);
        
        
        // Adding scroll pane to the table
        JScrollPane sp = new JScrollPane(table);
        this.add(sp);
        
        this.setSize(800, 500);
        
        // Setting default close operation for frame to be closed completely when frame is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting visibility of frame 
        this.setVisible(true);
        // Making frame to unable resizability
        this.setResizable(false);
        // Making frame to display at center
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is used to retrieve all the books details from the
     * database
     *
     * @return an ArrayList of Book containing all the books
     * instances retrieved from database
     */
    public ArrayList<Book> getBooks() {
        ArrayList books = null;
        try {
            // list to store books
            books = new ArrayList<>();
            // Getting connection reference
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();
            
            // Getting all borrowed_books details from database with current logged in reader's id
            ResultSet rs = st.executeQuery("select * from borrowed_books where reader_id="+SignIN.readerID);

            // If operation is successful
            while (rs.next()) {
                
                // Create book reference
                Book a = new Book();
                
                // Populate book reference
                a.setIsbn(rs.getString("isbn"));
                a.setName(rs.getString("name"));
                a.setNumOfPages(rs.getInt("pages"));
                a.setPrice(rs.getFloat("price"));
                
                // Add book to the list
                books.add(a);

            }
        } catch (SQLException ex) {
            return books;
        }
        return books;
    }

    
    
    /**
     * This method is used to listen to all action events caused in this frame
     *
     * @param ae This refers to current action event called by frame
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        // If Back to dashboard is clicked, go back to Dashboard.
        if(ae.getActionCommand().equals("Back to dashboard")){
                this.setVisible(false);
                new ReadersDashboard();
        }
    }

}
