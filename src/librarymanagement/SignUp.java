/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * This is used as Registeration form for the readers
 *
 * @author
 */
public class SignUp extends JFrame implements ActionListener {

    // input fields referring username and email of reader
    JTextField usernametf, emailtf;
    // Password input field referring password of reader.
    JPasswordField passwordtf;

    SignUp() {

        // Setting up frame 
        this.setTitle("Registeration form");
        // Setting up frame size
        this.setSize(500, 500);

        // Adding welcome label,setting up bounds, setting font, and adding to the frame
        JLabel welcome = new JLabel("Welcome to Library's registeration section...");
        welcome.setBounds(30, 30, 400, 40);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));

        this.add(welcome);

        // Adding username label, setting bounds, adding to the frame
        JLabel username = new JLabel("Username:");
        username.setBounds(100, 100, 100, 30);
        this.add(username);

        // Adding username input field, setting bounds, adding to the frame
        usernametf = new JTextField();
        usernametf.setBounds(200, 105, 200, 20);
        this.add(usernametf);

        // Adding password label, setting bounds, adding to the frame
        JLabel password = new JLabel("Password:");
        password.setBounds(100, 160, 100, 30);
        this.add(password);

        // Adding password input field, setting bounds, adding to the frame
        passwordtf = new JPasswordField();
        passwordtf.setBounds(200, 165, 200, 20);
        this.add(passwordtf);

        // Adding email label, setting bounds, adding to the frame
        JLabel email = new JLabel("Email:");
        email.setBounds(100, 230, 100, 30);
        this.add(email);

        // Adding email input field, setting bounds, adding to the frame
        emailtf = new JTextField();
        emailtf.setBounds(200, 235, 200, 20);
        this.add(emailtf);

        // Adding button for registeration, setting bounds, adding action listener and adding to frame
        JButton register = new JButton("Register");
        register.setBounds(150, 350, 100, 30);
        register.addActionListener(this);
        this.add(register);

        // Adding button for exitting, setting bounds, adding action listener and adding to frame
        JButton exit = new JButton("Exit");
        exit.setBounds(280, 350, 100, 30);
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
     *
     * @param ae This refers to current action event called by frame
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        // If Register button is clicked
        if (ae.getActionCommand().equals("Register")) {
            // add new reader to the databse with provided data
            this.addReader(usernametf.getText(), passwordtf.getText(), emailtf.getText());

            JOptionPane.showMessageDialog(null, "Registered");
        } // If Exit button is clicked
        else if (ae.getActionCommand().equals("Exit")) {
            JOptionPane.showMessageDialog(null, "Thank you for using our system :)");
            System.exit(0);

        }
    }

    /**
     * This method is used to add reader with provided data to the database
     *
     * @param name referring to inputted name from the user
     * @param password referring to inputted password from the user
     * @param email referring to inputted email from the user
     */
    public void addReader(String name, String password, String email) {
        try {

            // Getting database connection reference
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            // Inserting readers data to the database
            st.execute("insert into readers(name,password,email) values('" + name + "','" + password + "','" + email + "');");
            JOptionPane.showMessageDialog(null, "Registeration successfull");
            this.setVisible(false);
            // After registeration, moving on to Login form
            new SignIN();

        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
