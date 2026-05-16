package university.exceptions;

/** Thrown when a student has exceeded the maximum allowed course failure attempts (3) */
public class MaxAttemptsException extends RuntimeException {
    public MaxAttemptsException(String studentName, String courseName) {
        super(studentName + " has exceeded the maximum number of attempts (3) for course: " + courseName);
    }
}
