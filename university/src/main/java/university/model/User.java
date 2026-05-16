package university.model;

import university.enums.Role;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract base for every user in the system.
 * Authentication is handled by AuthService (Singleton), not here.
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final String id;
    protected String login;
    protected String password;   // stored hashed in real impl
    protected String email;
    protected Role role;

    protected User(String login, String password, String email, Role role) {
        this.id       = UUID.randomUUID().toString();
        this.login    = login;
        this.password = password;
        this.email    = email;
        this.role     = role;
    }

    // ── Getters ──────────────────────────────────────────────
    public String getId()       { return id; }
    public String getLogin()    { return login; }
    public String getPassword() { return password; }
    public String getEmail()    { return email; }
    public Role   getRole()     { return role; }

    // ── Setters ──────────────────────────────────────────────
    public void setLogin(String login)       { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email)       { this.email = email; }

    // ── Object overrides ─────────────────────────────────────
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return Objects.equals(id, ((User) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", role, login, email);
    }
}
