package Client;

public class User{
    private String username;
    private String IP;
    private int port;
    
    public User(String username, String IP, int port) {
        this.username = username;
        this.IP = IP;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }

    public String toString() {
        return "User [Username=" + username + ", Port=" + port + ", IPaddress=" + IP + "]";
    }
}

