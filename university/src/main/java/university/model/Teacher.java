package university.model;

import university.enums.Role;
import university.enums.TeacherTitle;
import university.interfaces.Researcher;

import java.util.*;

/**
 * Teacher — extends Employee.
 * Professors ALWAYS implement Researcher.
 * Other teachers MAY implement Researcher via TeacherResearcher subclass.
 */
public class Teacher extends Employee {
    private TeacherTitle title;
    private final List<Course> courses = new ArrayList<>();
    private final List<Integer> ratingsReceived = new ArrayList<>();

    public Teacher(String login, String password, String email,
                   String employeeId, String department, TeacherTitle title) {
        super(login, password, email, employeeId, department, Role.TEACHER);
        this.title = title;
    }

    // ── Course management ─────────────────────────────────────
    public void manageCourse(Course course) {
        if (!courses.contains(course))
            throw new IllegalArgumentException("You are not an instructor of this course.");
    }

    public void putMark(Student student, Course course, Mark mark) {
        if (!courses.contains(course))
            throw new IllegalStateException("Cannot mark students of a course you do not teach.");
        student.addMark(mark);
    }

    public List<Student> viewStudents() {
        return courses.stream()
                      .flatMap(c -> c.getStudents().stream())
                      .distinct()
                      .toList();
    }

    // ── Rating ────────────────────────────────────────────────
    public void receiveRating(int rating) { ratingsReceived.add(rating); }

    public double getAverageRating() {
        return ratingsReceived.stream()
                              .mapToInt(Integer::intValue)
                              .average()
                              .orElse(0.0);
    }

    // ── Getters / Setters ─────────────────────────────────────
    public TeacherTitle  getTitle()   { return title; }
    public List<Course>  getCourses() { return Collections.unmodifiableList(courses); }
    public void          addCourse(Course c)  { courses.add(c); }
    public void          removeCourse(Course c) { courses.remove(c); }
    public void          setTitle(TeacherTitle t) { this.title = t; }

    /** Professors are always researchers — convenience check */
    public boolean isProfessor() { return title.isProfessor(); }

    @Override
    public String toString() {
        return String.format("Teacher{id=%s, name=%s, title=%s, dept=%s, rating=%.1f}",
                employeeId, login, title, department, getAverageRating());
    }
}
