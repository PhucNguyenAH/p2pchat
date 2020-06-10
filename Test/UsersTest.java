package Test;

import java.io.Serializable;

public class UsersTest implements Serializable {
    private String username;
    private String password;
    private String IPaddress;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public void setIPaddress(String IPaddress) {
        this.IPaddress = IPaddress;
    }

    @Override
    public String toString() {
        return "User [Username=" + username + ", Password=" + password + ", IPaddress=" + IPaddress + "]";
    }
}
