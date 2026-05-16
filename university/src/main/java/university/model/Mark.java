package university.model;

import java.io.Serializable;

/**
 * Mark for one course: 1st attestation + 2nd attestation + final exam.
 * Total is weighted: att1 (30%) + att2 (30%) + final (40%).
 * Pass threshold: 50.
 */
public class Mark implements Serializable, Comparable<Mark> {
    private static final long serialVersionUID = 1L;

    private final Course course;
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;

    public Mark(Course course, double firstAttestation, double secondAttestation, double finalExam) {
        this.course             = course;
        this.firstAttestation   = clamp(firstAttestation);
        this.secondAttestation  = clamp(secondAttestation);
        this.finalExam          = clamp(finalExam);
    }

    private double clamp(double v) {
        if (v < 0 || v > 100) throw new IllegalArgumentException("Mark must be 0-100, got: " + v);
        return v;
    }

    /** Weighted total out of 100. */
    public double computeTotal() {
        return firstAttestation * 0.30 + secondAttestation * 0.30 + finalExam * 0.40;
    }

    public String getLetterGrade() {
        double t = computeTotal();
        if (t >= 90) return "A";
        if (t >= 80) return "B";
        if (t >= 70) return "C";
        if (t >= 60) return "D";
        if (t >= 50) return "E";
        return "F";
    }

    public boolean isPassed() { return computeTotal() >= 50.0; }

    public Course getCourse()              { return course; }
    public double getFirstAttestation()    { return firstAttestation; }
    public double getSecondAttestation()   { return secondAttestation; }
    public double getFinalExam()           { return finalExam; }

    @Override
    public int compareTo(Mark other) {
        return Double.compare(this.computeTotal(), other.computeTotal());
    }

    @Override
    public String toString() {
        return String.format("Mark{course=%s, att1=%.1f, att2=%.1f, final=%.1f, total=%.1f (%s)}",
                course.getName(), firstAttestation, secondAttestation, finalExam,
                computeTotal(), getLetterGrade());
    }
}
