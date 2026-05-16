package university.model;

import java.util.*;

/**
 * A university course. Can have more than 1 instructor.
 */
public class Course implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private final String courseId;
    private String name;
    private int    credits;
    private String targetMajor;   // which major this course is for
    private int    targetYear;    // which year of study

    private final List<Teacher>  instructors = new ArrayList<>();
    private final List<Student>  students    = new ArrayList<>();
    private final List<Lesson>   lessons     = new ArrayList<>();

    public Course(String courseId, String name, int credits, String targetMajor, int targetYear) {
        this.courseId    = courseId;
        this.name        = name;
        this.credits     = credits;
        this.targetMajor = targetMajor;
        this.targetYear  = targetYear;
    }

    public void addInstructor(Teacher t)  { if (!instructors.contains(t)) instructors.add(t); }
    public void addStudent(Student s)     { if (!students.contains(s))    students.add(s); }
    public void removeStudent(Student s)  { students.remove(s); }
    public void addLesson(Lesson l)       { lessons.add(l); }

    public String          getCourseId()    { return courseId; }
    public String          getName()        { return name; }
    public int             getCredits()     { return credits; }
    public String          getTargetMajor() { return targetMajor; }
    public int             getTargetYear()  { return targetYear; }
    public List<Teacher>   getInstructors() { return Collections.unmodifiableList(instructors); }
    public List<Student>   getStudents()    { return Collections.unmodifiableList(students); }
    public List<Lesson>    getLessons()     { return Collections.unmodifiableList(lessons); }

    public void setName(String name)           { this.name = name; }
    public void setCredits(int credits)        { this.credits = credits; }
    public void setTargetMajor(String major)   { this.targetMajor = major; }
    public void setTargetYear(int year)        { this.targetYear = year; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof Course)) return false;
        return courseId.equals(((Course)o).courseId);
    }
    @Override public int hashCode() { return courseId.hashCode(); }
    @Override public String toString() {
        return String.format("Course{id=%s, name='%s', credits=%d, instructors=%d, students=%d}",
                courseId, name, credits, instructors.size(), students.size());
    }
}
