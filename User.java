public class User implements java.io.Serializable{
    public String login;
    public String password;

    public boolean authenticate(String login, String password) {
        return this.login.equals(login) && this.password.equals(password);
    }
}