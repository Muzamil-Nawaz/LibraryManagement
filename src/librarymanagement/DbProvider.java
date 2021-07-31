package librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to provide the database connection instance, to perform
 * the operations on the system database
 *
 * 
 */
public class DbProvider {

    /**
     * A private constructor to prevent initialization of the class outside
     */
    private DbProvider() {

    }
    static Connection con = null;

    /**
     * This method uses a singleton design pattern to restrict system in making
     * more than one instance of the database connection and making it possible
     * to use same instance through out
     *
     * @return
     */
    public static Connection makeConnection(){
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                System.out.println("Driver Not Loading");
            } catch (InstantiationException ex) {
                Logger.getLogger(DbProvider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DbProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("connected");

            try {
                // Replace the port with your mysql server port
                String url = "jdbc:mysql://localhost:3306/library-management";
                // Replace this with username of your MySQL server
                String uname = "root";
                // Replace this with password of your MySQL server 
                String pwd = "";
                con = DriverManager.getConnection(url, uname, pwd);

            } catch (SQLException e) {
                 e.printStackTrace();
            }
        }
        return con;
    }
}
