package university.model;

import university.enums.Role;
import university.exceptions.CourseRegistrationException;
import university.exceptions.MaxAttemptsException;
import university.interfaces.Researcher;

import java.util.*;

/**
 * Bachelor student.
 * - Max 21 credits.
 * - Max 3 failed attempts per course.
 * - 4th-year students have a research supervisor (must be Researcher, hIndex >= 3).
 * - Can optionally implement Researcher.
 */
public class Student extends User {
    private static final int MAX_CREDITS  = 21;
    private static final int MAX_ATTEMPTS = 3;

    private String studentId;
    private double gpa;
    private int    credits;
    private int    year;          // 1-4
    private String major;

    private final List<Course>  registeredCourses = new ArrayList<>();
    private final List<Mark>    marks             = new ArrayList<>();
    private final Map<String, Integer> failAttempts = new HashMap<>(); // courseId → fail count

    private Researcher supervisor; // only for 4th-year

    private final List<Integer> teacherRatings = new ArrayList<>(); // ratings given by this student

    public Student(String login, String password, String email,
                   String studentId, int year, String major) {
        super(login, password, email, Role.STUDENT);
        this.studentId = studentId;
        this.year      = year;
        this.major     = major;
        this.credits   = 0;
        this.gpa       = 0.0;
    }

    // ── Course Registration ───────────────────────────────────
    /**
     * Register for a course. Throws if credit limit would be exceeded
     * or if the student has already failed it MAX_ATTEMPTS times.
     */
    public void registerCourse(Course course) {
        int fails = failAttempts.getOrDefault(course.getCourseId(), 0);
        if (fails >= MAX_ATTEMPTS)
            throw new MaxAttemptsException(login, course.getName());
        if (credits + course.getCredits() > MAX_CREDITS)
            throw new CourseRegistrationException(
                "Adding " + course.getCredits() + " credits would exceed the 21-credit limit."
            );
        registeredCourses.add(course);
        credits += course.getCredits();
        course.addStudent(this);
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            credits -= course.getCredits();
            course.removeStudent(this);
        }
    }

    // ── Marks ────────────────────────────────────────────────
    public void addMark(Mark mark) {
        marks.add(mark);
        if (mark.computeTotal() < 50.0) {                          // fail threshold
            String cid = mark.getCourse().getCourseId();
            failAttempts.merge(cid, 1, Integer::sum);
        }
        recalcGpa();
    }

    private void recalcGpa() {
        if (marks.isEmpty()) { gpa = 0.0; return; }
        gpa = marks.stream()
                   .mapToDouble(Mark::computeTotal)
                   .average()
                   .orElse(0.0);
    }

    // ── Rate Teacher ─────────────────────────────────────────  ★ NEW
    /**
     * Rate a teacher (1-5). Student must be registered in one of the teacher's courses.
     */
    public void rateTeacher(Teacher teacher, int rating) {
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        boolean enrolled = registeredCourses.stream()
                .anyMatch(c -> c.getInstructors().contains(teacher));
        if (!enrolled)
            throw new IllegalStateException("You can only rate a teacher of a course you are enrolled in.");
        teacher.receiveRating(rating);
        teacherRatings.add(rating);
    }

    // ── Transcript ────────────────────────────────────────────
    public Transcript getTranscript() {
        return new Transcript(this, marks);
    }

    // ── Supervisor (4th year) ─────────────────────────────────
    public void setSupervisor(Researcher supervisor) {
        if (year != 4)
            throw new IllegalStateException("Only 4th-year students require a supervisor.");
        if (supervisor.getHIndex() < 3)
            throw new university.exceptions.LowHIndexException(supervisor.getHIndex());
        this.supervisor = supervisor;
    }

    // ── Getters / Setters ─────────────────────────────────────
    public String         getStudentId()         { return studentId; }
    public double         getGpa()               { return gpa; }
    public int            getCredits()           { return credits; }
    public int            getYear()              { return year; }
    public String         getMajor()             { return major; }
    public List<Course>   getRegisteredCourses() { return Collections.unmodifiableList(registeredCourses); }
    public List<Mark>     getMarks()             { return Collections.unmodifiableList(marks); }
    public Researcher     getSupervisor()        { return supervisor; }

    public void setYear(int year)     { this.year = year; }
    public void setMajor(String m)    { this.major = m; }

    @Override
    public String toString() {
        return String.format("Student{id=%s, name=%s, year=%d, gpa=%.2f, credits=%d}",
                studentId, login, year, gpa, credits);
    }
}
