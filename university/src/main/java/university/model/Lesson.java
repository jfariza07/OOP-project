package university.model;

import university.enums.LessonType;

import java.io.Serializable;
import java.time.LocalDate;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private LessonType type;
    private LocalDate  date;
    private String     topic;
    private int        durationMinutes;

    public Lesson(LessonType type, LocalDate date, String topic, int durationMinutes) {
        this.type            = type;
        this.date            = date;
        this.topic           = topic;
        this.durationMinutes = durationMinutes;
    }

    public LessonType getType()            { return type; }
    public LocalDate  getDate()            { return date; }
    public String     getTopic()           { return topic; }
    public int        getDurationMinutes() { return durationMinutes; }

    @Override
    public String toString() {
        return String.format("Lesson{%s | %s | %s | %d min}", type, date, topic, durationMinutes);
    }
}
