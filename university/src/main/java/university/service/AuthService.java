package university.service;

import university.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * SINGLETON PATTERN — one global authentication service.
 * Every user accesses the system only through this class.
 * Separated from User model per teacher's feedback (authentication class).
 */
public class AuthService {

    // ── Singleton ─────────────────────────────────────────────
    private static AuthService instance;

    private AuthService() {}

    public static AuthService getInstance() {
        if (instance == null) {
            synchronized (AuthService.class) {
                if (instance == null) instance = new AuthService();
            }
        }
        return instance;
    }

    // ── State ─────────────────────────────────────────────────
    private User currentUser = null;
    private final List<String> authLog = new ArrayList<>();

    /**
     * Authenticate a user against the DataStore.
     * Returns the matched User or throws if credentials are wrong.
     */
    public User login(String login, String password) {
        DataStore store = DataStore.getInstance();
        User user = store.getAllUsers().stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid login or password."));
        currentUser = user;
        String logEntry = java.time.LocalDateTime.now() + " | LOGIN  | " + login;
        authLog.add(logEntry);
        System.out.println("[Auth] " + logEntry);
        return user;
    }

    public void logout() {
        if (currentUser != null) {
            String logEntry = java.time.LocalDateTime.now() + " | LOGOUT | " + currentUser.getLogin();
            authLog.add(logEntry);
            System.out.println("[Auth] " + logEntry);
        }
        currentUser = null;
    }

    public User getCurrentUser() { return currentUser; }

    public boolean isLoggedIn() { return currentUser != null; }

    public List<String> getAuthLog() { return java.util.Collections.unmodifiableList(authLog); }

    /** Guard — throws if no user is logged in. */
    public void requireLogin() {
        if (!isLoggedIn())
            throw new IllegalStateException("Access denied: please log in first.");
    }

    /** Guard — throws if logged-in user does not have the required role. */
    public void requireRole(university.enums.Role role) {
        requireLogin();
        if (currentUser.getRole() != role)
            throw new SecurityException("Access denied: requires role " + role);
    }
}
