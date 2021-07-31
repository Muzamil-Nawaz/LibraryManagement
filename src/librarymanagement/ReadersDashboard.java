package librarymanagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import models.Reader;


/**
 * This class refers to readers dashboard frame with reader operations
 *
 * @author 
 */
public class ReadersDashboard extends JFrame implements ActionListener{

    
    ReadersDashboard(){
        
        // Setting title for readers dashboard
        this.setTitle("Readers' Dashboard");
        // Setting size for dashboard
        this.setSize(600,700);
        
        // Adding welcome label to frame, setting bounds and changing font family and size.
        JLabel welcome = new JLabel("Welcome to dashboard, Reader");
        welcome.setBounds(100,50,300,40);
        welcome.setFont(new Font("Times New Roman",Font.BOLD,20));
        this.add(welcome);
        
        // Adding button to be used for borrowing a book, setting bounds, setting white color, adding action listener, and adding to the frame
        JButton borrowBook = new JButton("Borrow a book");
        borrowBook.setBounds(230, 150, 200, 30);
        borrowBook.setBackground(Color.white);
        borrowBook.addActionListener(this);
        this.add(borrowBook);
        
        
        // Adding button to be used to see borrowed books, setting bounds, setting white color, adding action listener, and adding to the frame
        JButton seeBorrowedBooks = new JButton("See borrowed books");
        seeBorrowedBooks.setBounds(230, 250, 200, 30);
        seeBorrowedBooks.setBackground(Color.white);
                seeBorrowedBooks.addActionListener(this);

        this.add(seeBorrowedBooks);
        
        
        
        // Adding button to be used for returning a borrowed book, setting bounds, setting white color, adding action listener, and adding to the frame
        JButton returnBook = new JButton("Return a book");
        returnBook.setBounds(230, 350, 200, 30);
        returnBook.setBackground(Color.white);
                returnBook.addActionListener(this);

        this.add(returnBook);
        
        // Adding button to be used for seeing/updating readers' profile, setting bounds, setting white color, adding action listener, and adding to the frame
        JButton seeProfile = new JButton("See/Update profile");
        seeProfile.setBounds(230, 450, 200, 30);
        seeProfile.setBackground(Color.white);
                seeProfile.addActionListener(this);

        this.add(seeProfile);
        
        
        
        
        
        // Adding button to be used for logging reader out, setting bounds, setting white color, adding action listener, and adding to the frame
        JButton logout = new JButton("Log out");
        logout.setBounds(230, 550, 200, 30);
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
        
        // If borrow a book is clicked
        if (ae.getActionCommand().equals("Borrow a book")) {
            // hide this frame
            this.setVisible(false);
            // Move to BorrowBookForm
            new BorrowBookForm();
            
        }
        // If See borrowed books is clicked      
        else if (ae.getActionCommand().equals("See borrowed books")) {
            // hide this frame            
            this.setVisible(false);
            // Move to SeeBorrowedBooksForm
            new SeeBorrowedBooksForm();
        }
        // If Return a book is clicked
        else if(ae.getActionCommand().equals("Return a book")){
          // Take ISBN as input for book to be removed
          String m = JOptionPane.showInputDialog("Enter book ISBN you want to delete?");
           
            
            // Return the book
            boolean bookReturned = returnBook(m);
            // if operation is successfull
            if(bookReturned){
                // SHow message
                JOptionPane.showMessageDialog(null, "Book with ISBN "+m+" returned successfully.");
            }
            else{
                JOptionPane.showMessageDialog(null, "Some problem occured while returning book, try again.");

            }
        }
        
        // If See/Update profile button is click
        else if(ae.getActionCommand().equals("See/Update profile")){
            try {
                
                // Create reader reference
                Reader r = new Reader();
                
                // get database connection
                Connection con = DbProvider.makeConnection();
                Statement st = con.createStatement();
                
                // Get readers' data with id of logged in user
                ResultSet rs = st.executeQuery("select * from readers where id = "+SignIN.readerID);
                // Populate above reader's reference with data retrieved by query
                if(rs.next()){
                    r.setEmail(rs.getString("email"));
                    r.setName(rs.getString("name"));
                    r.setPassword(rs.getString("password"));
                    // hide this frame
                    this.setVisible(false);
                    // go to UpdateProfile form with reader's current data reference
                    new UpdateProfile(r);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReadersDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        // If Log out button is clicked, hide this frame and go to Log in form
        else if(ae.getActionCommand().equals("Log out")){
            this.setVisible(false);
            new SignIN();
        }
    }
    
    /**
     * This method is used for returning the book operation
     * @param isbn refers to the ISBN of book to be returned
     * @return true if operation was successful, false otherwise
     */
    private boolean returnBook(String isbn) {
        try {
            
                            // get database connection
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();
            
            // Getting data for borrowed books with given ISBN and logged in reader's id
            ResultSet rs = st.executeQuery("select * from borrowed_books where isbn='"+isbn+"' and reader_id="+SignIN.readerID+";");
            
            // If query was successful
            if(rs.next()){
                String name = rs.getString("name");
                int pages = rs.getInt("pages");
                float price = rs.getFloat("price");
                Statement st2 = con.createStatement();
                
                // Insert the borrowed book back to the available stock
                st2.execute("insert into books values('" + isbn + "','" + name + "'," + pages + "," + price+" )");

            }
            Statement st3 = con.createStatement();
            // Remove the borrowed book from the borrowed books table
            st3.execute("delete from borrowed_books where isbn='"+isbn+"' and reader_id="+SignIN.readerID);
            
        } catch (SQLException ex) {
            return false;
        }

        return true;
    }
  
  
    
}

