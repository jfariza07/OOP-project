package university.service;

import university.model.*;

import java.io.*;
import java.util.*;

/**
 * SINGLETON PATTERN — one central data store for the entire system.
 * Handles serialization (save/load to disk).
 */
public class DataStore implements Serializable {
    private static final long   serialVersionUID = 1L;
    private static final String FILE_PATH        = "university_data.ser";

    // ── Singleton ─────────────────────────────────────────────
    private static DataStore instance;

    private DataStore() {}

    public static DataStore getInstance() {
        if (instance == null) {
            synchronized (DataStore.class) {
                if (instance == null) instance = new DataStore();
            }
        }
        return instance;
    }

    // ── Data collections ──────────────────────────────────────
    private final List<User>            users    = new ArrayList<>();
    private final List<Course>          courses  = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();
    private final List<String>          news     = new ArrayList<>();

    // ── Users ─────────────────────────────────────────────────
    public void addUser(User u)          { users.add(u); }
    public void removeUser(String id)    { users.removeIf(u -> u.getId().equals(id)); }
    public List<User> getAllUsers()      { return Collections.unmodifiableList(users); }

    public Optional<User> findUserById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public List<Student>  getStudents() {
        return users.stream().filter(u -> u instanceof Student).map(u -> (Student)u).toList();
    }
    public List<Teacher>  getTeachers() {
        return users.stream().filter(u -> u instanceof Teacher).map(u -> (Teacher)u).toList();
    }

    // ── Courses ───────────────────────────────────────────────
    public void   addCourse(Course c)      { courses.add(c); }
    public void   removeCourse(String id)  { courses.removeIf(c -> c.getCourseId().equals(id)); }
    public List<Course> getCourses()       { return Collections.unmodifiableList(courses); }

    // ── Research Projects ─────────────────────────────────────
    public void   addProject(ResearchProject p)   { projects.add(p); }
    public List<ResearchProject> getProjects()    { return Collections.unmodifiableList(projects); }

    // ── News ──────────────────────────────────────────────────
    public void   addNews(String item)    { news.add(item); }
    public void   removeNews(int index)   { news.remove(index); }
    public List<String> getNews()         { return Collections.unmodifiableList(news); }

    // ── Serialization ─────────────────────────────────────────
    /** Save all data to disk. */
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(this);
            System.out.println("[DataStore] Data saved to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("[DataStore] Save failed: " + e.getMessage());
        }
    }

    /** Load data from disk; replaces current instance. */
    public static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            DataStore loaded = (DataStore) ois.readObject();
            instance = loaded;
            System.out.println("[DataStore] Data loaded from " + FILE_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("[DataStore] No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[DataStore] Load failed: " + e.getMessage());
        }
    }
}
