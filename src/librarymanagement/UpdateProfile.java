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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import models.Reader;

/**
 * This class is used for form to update or see reader's profile
 *
 * @author
 */
public class UpdateProfile extends JFrame implements ActionListener {

    // Input fields for name and email data
    JTextField nametf, emailtf;
    // Input field for password data
    JPasswordField passwordtf;

    UpdateProfile(Reader reader) {

        // Setting title for form
        this.setTitle("See/Update profile ");
        // Setting size for form
        this.setSize(500, 500);

        // Adding welcome label, setting bounds, setting font family and size, adding to the frame
        JLabel welcome = new JLabel("Update  profile...");
        welcome.setBounds(30, 30, 400, 40);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
        this.add(welcome);

        // Adding label for displaying username, setting bounds, adding to frame
        JLabel name = new JLabel("Username:");
        name.setBounds(100, 100, 100, 30);
        this.add(name);

        // Adding input field for displaying username, setting bounds, adding to frame
        nametf = new JTextField(reader.getName());
        nametf.setBounds(200, 105, 200, 20);
        this.add(nametf);

        // Adding label for displaying password, setting bounds, adding to frame
        JLabel password = new JLabel("Password:");
        password.setBounds(100, 160, 100, 30);
        this.add(password);

        // Adding input field for displaying password, setting bounds, adding to frame
        passwordtf = new JPasswordField(reader.getPassword());
        passwordtf.setBounds(200, 165, 200, 20);
        this.add(passwordtf);

        // Adding label for displaying email, setting bounds, adding to frame
        JLabel email = new JLabel("Email:");
        email.setBounds(100, 230, 100, 30);
        this.add(email);

        // Adding input field for displaying email, setting bounds, adding to frame
        emailtf = new JTextField(String.valueOf(reader.getEmail()));
        emailtf.setBounds(200, 235, 200, 20);
        this.add(emailtf);

        // Adding button to be used for updating data, setting bounds, adding action listener, and adding to frame
        JButton update = new JButton("Update");
        update.setBounds(150, 350, 100, 30);
        update.addActionListener(this);
        this.add(update);

        // Adding button to be used for returning to dashboard, setting bounds, adding action listener, and adding to frame
        JButton back = new JButton("Back to dashboard");
        back.setBounds(280, 350, 200, 30);
        back.addActionListener(this);
        this.add(back);

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

        // If Update button is clicked
        if (ae.getActionCommand().equals("Update")) {

            // Create reader reference
            Reader b = new Reader();
            // Populate readers data from provided from user
            b.setName(nametf.getText());
            b.setEmail(emailtf.getText());
            b.setPassword(passwordtf.getText());

            // Update reader in the database
            updateReader(b);

        } // If back to dashboard is clicked
        else if (ae.getActionCommand().equals("Back to dashboard")) {
            // Hide this frame,and move back to dashboard frame
            this.setVisible(false);
            new ReadersDashboard();
        }
    }

    /**
     * This method is used to update readers' data in the database
     *
     * @param r referring to the reader to be updated
     */
    public void updateReader(Reader r) {
        try {

            // Getting database connection
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            // Executing update query to update reader's data with update data provided in inputs
            st.execute("update  readers set name='" + r.getName() + "', email='" + r.getEmail() + "',password='" + r.getPassword() + "' where id='" + SignIN.readerID + "';");
            JOptionPane.showMessageDialog(null, "Profile updated successfully");

        } catch (SQLException ex) {
            Logger.getLogger(UpdateProfile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
