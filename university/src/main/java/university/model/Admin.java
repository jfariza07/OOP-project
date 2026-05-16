package university.model;

import university.enums.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Admin — manages users and views system logs.
 */
public class Admin extends Employee {
    private final List<String> logs = new ArrayList<>();

    public Admin(String login, String password, String email,
                 String employeeId, String department) {
        super(login, password, email, employeeId, department, Role.ADMIN);
    }

    public void logAction(String action) {
        String entry = java.time.LocalDateTime.now() + " | " + action;
        logs.add(entry);
        System.out.println("[LOG] " + entry);
    }

    public List<String> viewLogs() { return Collections.unmodifiableList(logs); }

    public void addUser(User user) {
        logAction("ADD USER: " + user.getLogin());
        // Actual storage handled by DataStore
    }

    public void removeUser(String userId) {
        logAction("REMOVE USER id=" + userId);
    }

    public void updateUser(User user) {
        logAction("UPDATE USER: " + user.getLogin());
    }

    @Override
    public String toString() {
        return String.format("Admin{id=%s, name=%s, dept=%s}", employeeId, login, department);
    }
}
