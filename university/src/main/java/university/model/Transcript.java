package university.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Student    student;
    private final List<Mark> marks;
    private final LocalDate  generatedDate;

    public Transcript(Student student, List<Mark> marks) {
        this.student       = student;
        this.marks         = List.copyOf(marks);
        this.generatedDate = LocalDate.now();
    }

    public double getGPA() {
        return marks.stream()
                    .mapToDouble(Mark::computeTotal)
                    .average()
                    .orElse(0.0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TRANSCRIPT ===\n");
        sb.append("Student : ").append(student.getLogin()).append("\n");
        sb.append("Year    : ").append(student.getYear()).append("\n");
        sb.append("Major   : ").append(student.getMajor()).append("\n");
        sb.append("Date    : ").append(generatedDate).append("\n");
        sb.append("GPA     : ").append(String.format("%.2f", getGPA())).append("\n");
        sb.append("------------------\n");
        marks.forEach(m -> sb.append(m).append("\n"));
        sb.append("==================\n");
        return sb.toString();
    }
}
