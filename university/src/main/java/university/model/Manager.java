package university.model;

import university.enums.ManagerType;
import university.enums.Role;

import java.util.*;

/**
 * Manager — approves registrations, assigns teachers, generates reports, manages news.
 */
public class Manager extends Employee {
    private ManagerType managerType;
    private final List<String> news = new ArrayList<>();

    public Manager(String login, String password, String email,
                   String employeeId, String department, ManagerType managerType) {
        super(login, password, email, employeeId, department, Role.MANAGER);
        this.managerType = managerType;
    }

    /** Approve a student's course registration request. */
    public void approveRegistration(Student student, Course course) {
        student.registerCourse(course);
        System.out.println("[Manager] Approved registration: " + student.getLogin() + " → " + course.getName());
    }

    /** Assign a teacher to a course. A course can have more than one instructor. */
    public void assignTeacher(Teacher teacher, Course course) {
        course.addInstructor(teacher);
        teacher.addCourse(course);
        System.out.println("[Manager] Assigned " + teacher.getLogin() + " to " + course.getName());
    }

    /** Add a course to the system (specifying major & year it is intended for). */
    public void addCourse(Course course) {
        System.out.println("[Manager] Course added: " + course.getName()
                + " | For: " + course.getTargetMajor() + " year " + course.getTargetYear());
    }

    /** Generate a simple statistics report on academic performance. */
    public Report generateReport(List<Student> students) {
        return new Report(students);
    }

    public void addNews(String newsItem) { news.add(newsItem); }
    public void removeNews(int index)    { news.remove(index); }
    public List<String> getNews()        { return Collections.unmodifiableList(news); }

    /** View students sorted by GPA descending. */
    public List<Student> viewStudentsByGpa(List<Student> students) {
        return students.stream()
                       .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                       .toList();
    }

    /** View students sorted alphabetically by login. */
    public List<Student> viewStudentsAlphabetically(List<Student> students) {
        return students.stream()
                       .sorted(Comparator.comparing(Student::getLogin))
                       .toList();
    }

    /** View teachers sorted alphabetically. */
    public List<Teacher> viewTeachersAlphabetically(List<Teacher> teachers) {
        return teachers.stream()
                       .sorted(Comparator.comparing(Teacher::getLogin))
                       .toList();
    }

    public ManagerType getManagerType()         { return managerType; }
    public void setManagerType(ManagerType t)   { this.managerType = t; }

    @Override
    public String toString() {
        return String.format("Manager{id=%s, name=%s, type=%s}", employeeId, login, managerType);
    }
}
