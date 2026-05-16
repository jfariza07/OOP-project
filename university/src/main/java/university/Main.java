package university;

import university.comparators.ResearchPaperComparators;
import university.enums.LessonType;
import university.enums.ManagerType;
import university.enums.TeacherTitle;
import university.factory.UserFactory;
import university.model.*;
import university.service.AuthService;
import university.service.DataStore;
import university.service.ResearchService;

import java.time.LocalDate;
import java.util.List;

/**
 * Main demo — shows the full system working end to end.
 * Run this to verify all functionality before the final demo.
 */
public class Main {

    public static void main(String[] args) {

        DataStore  store  = DataStore.getInstance();
        AuthService auth  = AuthService.getInstance();
        ResearchService researchService = new ResearchService();

        // ── 1. Create users via Factory ───────────────────────
        System.out.println("═══════ CREATING USERS ═══════");

        // Professor → automatically becomes TeacherResearcher
        Teacher profSmith = UserFactory.createTeacher(
            "smith", "pass123", "smith@uni.kz",
            "T001", "Computer Science", TeacherTitle.PROFESSOR, 7
        );

        // Non-professor teacher (Lecturer) who is also a researcher
        Teacher lecturerDoe = UserFactory.createTeacherResearcher(
            "doe", "pass456", "doe@uni.kz",
            "T002", "Math", TeacherTitle.LECTURER, 4
        );

        // Regular tutor (not a researcher)
        Teacher tutorAli = UserFactory.createTeacher(
            "ali", "pass789", "ali@uni.kz",
            "T003", "Physics", TeacherTitle.TUTOR, 0
        );

        // Students
        Student studentAnya = UserFactory.createStudent(
            "anya", "stud1", "anya@uni.kz", "S001", 2, "CS"
        );
        Student student4th = UserFactory.createStudent(
            "bekzat", "stud2", "bekzat@uni.kz", "S002", 4, "CS"
        );

        // 4th year → assign supervisor (must be Researcher with hIndex >= 3)
        TeacherResearcher prof = (TeacherResearcher) profSmith;
        student4th.setSupervisor(prof);
        System.out.println("Supervisor assigned: " + profSmith.getLogin());

        // Admin & Manager
        Admin   admin   = UserFactory.createAdmin("admin", "admin123", "admin@uni.kz", "A001", "IT");
        Manager manager = UserFactory.createManager("mgr", "mgr123", "mgr@uni.kz", "M001", "Registry", ManagerType.OR);

        // Register all users in the store
        for (User u : List.of(profSmith, lecturerDoe, tutorAli, studentAnya, student4th, admin, manager))
            store.addUser(u);

        // ── 2. Create courses ─────────────────────────────────
        System.out.println("\n═══════ COURSES ═══════");
        Course oop  = new Course("CS101", "Object-Oriented Programming", 5, "CS", 2);
        Course math = new Course("MA101", "Calculus", 4, "CS", 1);
        store.addCourse(oop);
        store.addCourse(math);

        // Manager assigns teachers (course can have multiple instructors)
        manager.assignTeacher(profSmith, oop);
        manager.assignTeacher(lecturerDoe, oop);
        manager.assignTeacher(tutorAli, math);

        // Add lessons
        oop.addLesson(new Lesson(LessonType.LECTURE, LocalDate.now(), "Inheritance & Polymorphism", 90));
        oop.addLesson(new Lesson(LessonType.PRACTICE, LocalDate.now().plusDays(2), "Lab: Design Patterns", 90));

        // ── 3. Authentication ─────────────────────────────────
        System.out.println("\n═══════ AUTH ═══════");
        auth.login("anya", "stud1");
        System.out.println("Logged in as: " + auth.getCurrentUser());

        // ── 4. Student registers for course ───────────────────
        System.out.println("\n═══════ COURSE REGISTRATION ═══════");
        manager.approveRegistration(studentAnya, oop);
        manager.approveRegistration(student4th, oop);
        System.out.println("Credits used: " + studentAnya.getCredits() + "/21");

        // ── 5. Teacher puts marks ─────────────────────────────
        System.out.println("\n═══════ MARKS ═══════");
        auth.logout();
        auth.login("smith", "pass123");

        Mark markAnya = new Mark(oop, 85, 90, 88);
        profSmith.putMark(studentAnya, oop, markAnya);

        Mark markBekzat = new Mark(oop, 40, 45, 30);   // failing mark
        profSmith.putMark(student4th, oop, markBekzat);

        System.out.println("Anya's mark   : " + markAnya);
        System.out.println("Bekzat's mark : " + markBekzat);

        // ── 6. Student rates teacher ★NEW ─────────────────────
        System.out.println("\n═══════ RATE TEACHER ═══════");
        auth.logout();
        auth.login("anya", "stud1");
        studentAnya.rateTeacher(profSmith, 5);
        studentAnya.rateTeacher((Teacher) lecturerDoe, 4);
        System.out.println("Smith rating  : " + profSmith.getAverageRating());
        System.out.println("Doe rating    : " + ((Teacher) lecturerDoe).getAverageRating());

        // ── 7. Transcript ─────────────────────────────────────
        System.out.println("\n═══════ TRANSCRIPT ═══════");
        System.out.println(studentAnya.getTranscript());

        // ── 8. Research ★NEW ─────────────────────────────────
        System.out.println("\n═══════ RESEARCH ═══════");

        ResearchPaper paper1 = new ResearchPaper(
            "10.1109/TPAMI.2021.1", "Deep Learning Survey",
            List.of("Smith", "Doe"), "IEEE TPAMI",
            450, 28, LocalDate.of(2022, 3, 10),
            List.of("deep learning","neural networks"), 44, "A comprehensive survey..."
        );
        ResearchPaper paper2 = new ResearchPaper(
            "10.1109/TPAMI.2021.2", "Graph Neural Networks",
            List.of("Smith"), "IEEE TPAMI",
            120, 14, LocalDate.of(2023, 7, 5),
            List.of("GNN","graphs"), 46, "Graph-based approaches..."
        );

        TeacherResearcher trSmith = (TeacherResearcher) profSmith;
        trSmith.addPaper(paper1);
        trSmith.addPaper(paper2);

        System.out.println("Papers by citations:");
        trSmith.printPapers(ResearchPaperComparators.BY_CITATIONS_DESC);

        System.out.println("\nPapers by date:");
        trSmith.printPapers(ResearchPaperComparators.BY_DATE_DESC);

        System.out.println("\nPapers by length:");
        trSmith.printPapers(ResearchPaperComparators.BY_LENGTH_DESC);

        // Research Project
        ResearchProject project = new ResearchProject("AI in Education");
        store.addProject(project);
        trSmith.joinProject(project);
        project.publishPaper(paper1);
        System.out.println("\n" + project);

        // Try adding non-researcher to project — should throw
        System.out.println("\nAttempting to add non-researcher (tutor) to project:");
        try {
            project.addParticipant(tutorAli);
        } catch (university.exceptions.NonResearcherException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // University-wide research stats
        System.out.println("\n── All papers by citations (university-wide) ──");
        researchService.printAllPapersByCitations();

        System.out.println("\n── Top researcher of year 2022 ──");
        researchService.printTopResearcherOfYear(2022);

        // ── 9. Low hIndex supervisor exception ───────────────
        System.out.println("\n═══════ LOW H-INDEX EXCEPTION ═══════");
        try {
            student4th.setSupervisor((TeacherResearcher) lecturerDoe); // hIndex=4 is fine
            System.out.println("Supervisor with hIndex=4 accepted.");

            // Now try with hIndex < 3
            StudentResearcher lowHRes = UserFactory.createStudentResearcher(
                "lowres", "x", "x@uni.kz", "S999", 3, "CS", 1
            );
            student4th.setSupervisor(lowHRes); // should throw
        } catch (university.exceptions.LowHIndexException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // ── 10. Report ────────────────────────────────────────
        System.out.println("\n═══════ ACADEMIC REPORT ═══════");
        auth.logout();
        auth.login("mgr", "mgr123");
        Report report = manager.generateReport(store.getStudents());
        System.out.println(report);

        // ── 11. Admin logs ────────────────────────────────────
        System.out.println("\n═══════ ADMIN LOGS ═══════");
        auth.logout();
        auth.login("admin", "admin123");
        admin.logAction("Viewed all user records");
        admin.viewLogs().forEach(System.out::println);

        // ── 12. Messaging ─────────────────────────────────────
        System.out.println("\n═══════ MESSAGING ═══════");
        Message msg = profSmith.sendMessage(studentAnya, "Please review lecture notes.");
        System.out.println(msg);
        Complaint complaint = lecturerDoe.sendComplaint(admin, "Grading Issue", "My grade sheet was altered.");
        System.out.println(complaint);

        // ── 13. Save data ─────────────────────────────────────
        System.out.println("\n═══════ SAVING DATA ═══════");
        auth.logout();
        store.save();

        System.out.println("\n✔ All systems functional. Ready for demo.");
    }
}
