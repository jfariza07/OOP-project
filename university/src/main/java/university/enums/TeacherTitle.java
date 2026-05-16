package university.enums;

public enum TeacherTitle {
    TUTOR, LECTURER, SENIOR_LECTURER, PROFESSOR;

    public boolean isProfessor() {
        return this == PROFESSOR;
    }
}
