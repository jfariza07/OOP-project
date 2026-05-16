package university.factory;

import university.enums.ManagerType;
import university.enums.TeacherTitle;
import university.model.*;

/**
 * FACTORY PATTERN — centralises object creation for all User subtypes.
 * Enforces the rule: Professors are ALWAYS created as TeacherResearchers.
 */
public class UserFactory {

    private UserFactory() {}

    public static Student createStudent(String login, String password, String email,
                                        String studentId, int year, String major) {
        return new Student(login, password, email, studentId, year, major);
    }

    public static StudentResearcher createStudentResearcher(String login, String password,
                                                             String email, String studentId,
                                                             int year, String major, int hIndex) {
        Student base = new Student(login, password, email, studentId, year, major);
        return new StudentResearcher(base, hIndex);
    }

    /**
     * Creates a Teacher. If title is PROFESSOR, automatically wraps in TeacherResearcher.
     */
    public static Teacher createTeacher(String login, String password, String email,
                                        String employeeId, String department,
                                        TeacherTitle title, int hIndex) {
        Teacher base = new Teacher(login, password, email, employeeId, department, title);
        if (title.isProfessor()) {
            // Professors ALWAYS implement Researcher
            return new TeacherResearcher(base, hIndex);
        }
        return base;
    }

    /**
     * Explicitly create a non-professor teacher who is also a Researcher.
     */
    public static TeacherResearcher createTeacherResearcher(String login, String password,
                                                             String email, String employeeId,
                                                             String department, TeacherTitle title,
                                                             int hIndex) {
        Teacher base = new Teacher(login, password, email, employeeId, department, title);
        return new TeacherResearcher(base, hIndex);
    }

    public static Admin createAdmin(String login, String password, String email,
                                    String employeeId, String department) {
        return new Admin(login, password, email, employeeId, department);
    }

    public static Manager createManager(String login, String password, String email,
                                        String employeeId, String department, ManagerType type) {
        return new Manager(login, password, email, employeeId, department, type);
    }
}
