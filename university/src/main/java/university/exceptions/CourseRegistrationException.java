package university.exceptions;

/** Thrown when a student cannot register for a course (e.g., credit limit exceeded) */
public class CourseRegistrationException extends RuntimeException {
    public CourseRegistrationException(String reason) {
        super("Course registration failed: " + reason);
    }
}
