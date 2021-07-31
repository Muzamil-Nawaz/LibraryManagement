package librarymanagement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This is to be used for Log in form operations
 *
 * @author
 */
public class SignIN extends JFrame implements ActionListener {

    // Combobox to be used for different login options to chose from
    JComboBox userTypes;
    // Input field for username data
    JTextField usernametf;
    // Input field for password data
    JPasswordField passwordtf;
    // variable for storing readers id from database
    public static int readerID;

    SignIN() {

        // Setting up title for login form
        this.setTitle("Log in form");

        //Setting size for frame
        this.setSize(500, 500);

        // Adding welcome label to the frame, setting bounds, adding fonts
        JLabel welcome = new JLabel("Welcome to Library's sign in section...");
        welcome.setBounds(30, 30, 400, 40);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
        this.add(welcome);

        // Label for login type to chose, setting bounds, adding to frame
        JLabel loginType = new JLabel("Login type:");
        loginType.setBounds(100, 100, 100, 30);
        this.add(loginType);

        // Adding combo box with login options, setting bounds, adding different options, adding to frame
        userTypes = new JComboBox();
        userTypes.setBounds(200, 105, 200, 30);
        userTypes.addItem("Admin");
        userTypes.addItem("Reader");
        this.add(userTypes);

        // Adding username label, setting bounds, adding to the frame
        JLabel username = new JLabel("Username:");
        username.setBounds(100, 160, 100, 30);
        this.add(username);

        // Adding input field for username, setting bounds, adding to component
        usernametf = new JTextField();
        usernametf.setBounds(200, 165, 200, 20);
        this.add(usernametf);

        // Adding password label, setting bounds, adding to the frame
        JLabel password = new JLabel("Password:");
        password.setBounds(100, 220, 100, 30);
        this.add(password);

        // Adding input field for password, setting bounds, adding to component
        passwordtf = new JPasswordField();
        passwordtf.setBounds(200, 225, 200, 20);
        this.add(passwordtf);

        // Adding sign in button, setting bounds, adding action listener and adding to the frame
        JButton signin = new JButton("Sign in");
        signin.setBounds(120, 300, 100, 30);
        signin.addActionListener(this);
        this.add(signin);

        // Adding sign up button, setting bounds, adding action listener and adding to the frame
        JButton signup = new JButton("Not registered? Sign up");
        signup.setBounds(250, 300, 200, 30);
        signup.addActionListener(this);
        this.add(signup);

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

        // If sign in button is clicked
        if (ae.getActionCommand().equals("Sign in")) {

            // Check if user is available with given username and password
            boolean isUser = checkUser(usernametf.getText(), passwordtf.getText(), userTypes.getSelectedItem().toString());
            // If available
            if (isUser) {
                // Empty the input fields
                usernametf.setText("");
                passwordtf.setText("");

                JOptionPane.showMessageDialog(null, "Signing in...");
                // If login type was admin
                if (userTypes.getSelectedItem().toString().equals("Admin")) {
                    // Hide this frame and move to admin dashboard
                    this.setVisible(false);
                    new AdminDashboard();
                } // If login type was Reader
                else if (userTypes.getSelectedItem().toString().equals("Reader")) {
                    // Hide this frame and move to reader dashboard

                    this.setVisible(false);

                    new ReadersDashboard();
                }
            } // IF user not availabe, display that credentials were invalid
            else {
                JOptionPane.showMessageDialog(null, "Invalid credentials, try again.");

            }
        } // If user is not registered
        else if (ae.getActionCommand().equals("Not registered? Sign up")) {
            // Hide this and move to SignUp form
            this.setVisible(false);
            new SignUp();

        }
    }

    /**
     * This method is used to check if user with provided credentials is present
     * in database is present or not
     *
     * @param username referring to username inputted by user
     * @param password referring to password inputted by user
     * @param userType referring to login type, user trying for
     * @return true if user exists, false otherwise
     */
    public boolean checkUser(String username, String password, String userType) {

        // Getting database connection
        Connection con = DbProvider.makeConnection();
        Statement stmt;
        try {

            stmt = con.createStatement();
            ResultSet rs;

            // If user is reader 
            if (userType.equals("Reader")) {
                // Look for user in readers table of database
                rs = stmt.executeQuery(" select * from readers where name='" + username + "' and password='" + password + "'");
            } //otherwise
            else {
                // Look for user in admin table of database
                rs = stmt.executeQuery(" select * from admin where name='" + username + "' and password='" + password + "'");
            }

            // When user checked successfully, save his Database id for future reference
            if (rs.next()) {
                readerID = rs.getInt("id");
                return true;

            }

        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    
    public static void main(String[] args) {
        SignIN start = new SignIN();
        
    }

}
