import java.util.ArrayList;
import java.util.List;

public class Admin extends User implements java.io.Serializable{
    private List<String> logs = new ArrayList<>();

    public void addUser(User u) {
        System.out.println("Admin added user: " + u.login);
        logs.add("Added user " + u.login);
    }

    public void removeUser(User u) {
        System.out.println("Admin removed user: " + u.login);
        logs.add("Removed user " + u.login);
    }

    public void viewLogs() {
        System.out.println("--- System Logs ---");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}