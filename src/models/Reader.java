package models;

/**
 * This class represents reference of Reader
 *
 * @author
 */
public class Reader {

    // Required attributes for Reader to be used in the system
    String name, password, email;

    public Reader() {

    }

    // Getters and setters to setting and getting values of object's attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
