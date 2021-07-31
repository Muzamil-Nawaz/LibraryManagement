package librarymanagement;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.Book;

/**
 *
 * @author This form is used to add a new book or update exisiting book
 */
public class AddBook extends JFrame implements ActionListener {

    // These fields represensts ISBN, Name, number of pages and price of book taken as input
    JTextField isbntf, nametf, numOfPagestf, pricetf;

    AddBook(Book book) {
        // If passed reference of book is null, it means this form is being used for adding new book
        if (book == null) {

            // setting up title for frame to be used for adding a book
            this.setTitle("Add new book ");
            // setting up size for frame to be used for adding a book
            this.setSize(500, 500);

            //Adding a welcoming label 
            JLabel welcome = new JLabel("Add new book...");
            // Setting bounds for label where it needs to be placed
            welcome.setBounds(30, 30, 400, 40);
            // Setting up cuutom font family, and size for welcome label post
            welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
            // Adding welcome label to frame
            this.add(welcome);

            //Adding a ISBN label, setting bounds and adding it to frame
            JLabel isbn = new JLabel("ISBN:");
            isbn.setBounds(100, 100, 100, 30);
            this.add(isbn);

            //Adding a ISBN input field, setting bounds and adding it to frame
            isbntf = new JTextField();
            isbntf.setBounds(200, 105, 200, 20);
            this.add(isbntf);

            //Adding a Name label, setting bounds and adding it to frame
            JLabel name = new JLabel("Book Name:");
            name.setBounds(100, 160, 100, 30);
            this.add(name);

            //Adding a Name input field, setting bounds and adding it to frame
            nametf = new JTextField();
            nametf.setBounds(200, 165, 200, 20);
            this.add(nametf);

            //Adding a No: of pages label, setting bounds and adding it to frame
            JLabel numOfPages = new JLabel("No: of pages:");
            numOfPages.setBounds(100, 230, 100, 30);
            this.add(numOfPages);

            //Adding a No: of pages input field, setting bounds and adding it to frame
            numOfPagestf = new JTextField();
            numOfPagestf.setBounds(200, 235, 200, 20);
            this.add(numOfPagestf);

            //Adding a Price label, setting bounds and adding it to frame
            JLabel price = new JLabel("Price");
            price.setBounds(100, 300, 100, 30);
            this.add(price);

            //Adding a Price input field, setting bounds and adding it to frame
            pricetf = new JTextField();
            pricetf.setBounds(200, 305, 200, 20);
            this.add(pricetf);

            //Adding Add button to be added to , setting bounds and adding it to frame
            JButton add = new JButton("Add");
            add.setBounds(150, 350, 100, 30);
            add.addActionListener(this);
            this.add(add);

        } 
                // If passed reference of book is not null, it means this form is being used for updating existing book
        else {

            // setting up title for frame to be used for updating a book
            this.setTitle("Update a book ");
            this.setSize(500, 500);
            
                        //Adding a welcoming label 
            JLabel welcome = new JLabel("Update  book...");
                        // Setting bounds for label where it needs to be placed
            welcome.setBounds(30, 30, 400, 40);       
            // Setting up cuutom font family, and size for welcome label post
            welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
            // Adding welcome label to frame
            this.add(welcome);

            
                        //Adding a ISBN label, setting bounds and adding it to frame
            JLabel isbn = new JLabel("ISBN:");
            isbn.setBounds(100, 100, 100, 30);
            this.add(isbn);

                        //Adding a ISBN input field, setting bounds and adding it to frame and populating with data of passed book reference
            isbntf = new JTextField(book.getIsbn());
            isbntf.setBounds(200, 105, 200, 20);
            isbntf.setEnabled(false);
            this.add(isbntf);

                        //Adding a Name label, setting bounds and adding it to frame
            JLabel name = new JLabel("Book Name:");
            name.setBounds(100, 160, 100, 30);
            this.add(name);

                                    //Adding a Name input field, setting bounds and adding it to frame and populating with data of passed book reference
            nametf = new JTextField(book.getName());
            nametf.setBounds(200, 165, 200, 20);
            this.add(nametf);

                        //Adding a No: of pages label, setting bounds and adding it to frame
            JLabel numOfPages = new JLabel("No: of pages:");
            numOfPages.setBounds(100, 230, 100, 30);
            this.add(numOfPages);

                                    //Adding a No: of pages input field, setting bounds and adding it to frame and populating with data of passed book reference
            numOfPagestf = new JTextField(String.valueOf(book.getNumOfPages()));
            numOfPagestf.setBounds(200, 235, 200, 20);
            this.add(numOfPagestf);

                        //Adding a Price label, setting bounds and adding it to frame
            JLabel price = new JLabel("Price");
            price.setBounds(100, 300, 100, 30);
            this.add(price);

            
                                    //Adding a Price input field, setting bounds and adding it to frame and populating with data of passed book reference
            pricetf = new JTextField(String.valueOf(book.getPrice()));
            pricetf.setBounds(200, 305, 200, 20);
            this.add(pricetf);

            
                        //Adding Update button to be added to , setting bounds and adding it to frame
            JButton update = new JButton("Update");
            update.setBounds(150, 350, 100, 30);
            update.addActionListener(this);
            this.add(update);

        }

        
                    //Adding a button to return to dashboard , setting bounds and adding it to frame
        JButton exit = new JButton("Back to dashboard");
        exit.setBounds(280, 350, 200, 30);
        exit.addActionListener(this);
        this.add(exit);

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
     * @param ae This refers to current action event called by frame
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        // If add button is pressed
        if (ae.getActionCommand().equals("Add")) {

            // Creating a Book reference
            Book b = new Book();
            
            // Taking data from input fields and setting them up in book reference
            b.setIsbn(isbntf.getText());
            b.setName(nametf.getText());
            b.setNumOfPages(Integer.parseInt(numOfPagestf.getText()));
            b.setPrice(Float.parseFloat(pricetf.getText()));
            
            // Adding book to database
            addBook(b);

        }
                // If update button is pressed
        else if (ae.getActionCommand().equals("Update")) {
                        // Creating a Book reference
            Book b = new Book();
            
                        // Taking updated data from input fields and setting them up in book reference
            b.setIsbn(isbntf.getText());
            b.setName(nametf.getText());
            b.setNumOfPages(Integer.parseInt(numOfPagestf.getText()));
            b.setPrice(Float.parseFloat(pricetf.getText()));
            
            // Updating book data in the database
            updateBook(b);

        } 
        // If back to dashboard is clicked, lead user to admin dashboard and hide this frame
        else if (ae.getActionCommand().equals("Back to dashboard")) {
            this.setVisible(false);
            new AdminDashboard();
        }
    }

    /**
     * Method for adding new book to database
     * @param b refering book reference needed to be added to database
     */
    public void addBook(Book b) {
        try {
            // Getting database connection object from DbProvider class
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();
            
            // Executing insert query for book values to be added to database
            st.execute("insert into books values('" + b.getIsbn() + "','" + b.getName() + "'," + b.getNumOfPages() + "," + b.getPrice() + ");");
            
            // Show the message if book added successfully
            JOptionPane.showMessageDialog(null, "Book added successfully");
            
            // Emptying the input fields after data is successfully added
            isbntf.setText("");
            nametf.setText("");
            numOfPagestf.setText("");
            pricetf.setText("");

        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
     /**
     * Method for updating  book's data to database
     * @param b refering book reference needed to be updated to database
     */
    public void updateBook(Book b) {
        try {
            
                        // Getting database connection object from DbProvider class
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();
            
                        // Executing update query for book values to be updated to database
            st.execute("update  books set name='" + b.getName() + "', pages=" + b.getNumOfPages() + ",price=" + b.getPrice() + " where isbn='" + b.getIsbn() + "';");
            
                        // Show the message if book added successfully
            JOptionPane.showMessageDialog(null, "Book updated successfully");
            
                        // Emptying the input fields after data is successfully updated
            isbntf.setText("");
            nametf.setText("");
            numOfPagestf.setText("");
            pricetf.setText("");

        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   
}
