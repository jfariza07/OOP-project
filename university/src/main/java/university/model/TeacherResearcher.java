package university.model;

import university.enums.TeacherTitle;
import university.interfaces.Researcher;

import java.util.*;

/**
 * DECORATOR PATTERN — wraps a Teacher and adds Researcher capabilities.
 * Used for:
 *   - All Professors (mandatory).
 *   - Non-professor teachers who happen to be researchers (optional).
 */
public class TeacherResearcher extends Teacher implements Researcher {
    private int hIndex;
    private final List<ResearchPaper>   papers   = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();

    public TeacherResearcher(Teacher base, int hIndex) {
        super(base.getLogin(), base.getPassword(), base.getEmail(),
              base.getEmployeeId(), base.getDepartment(), base.getTitle());
        // Copy courses from base
        base.getCourses().forEach(this::addCourse);
        this.hIndex = hIndex;
        // Professors must always be researchers — validated by factory/service
        if (base.getTitle() == TeacherTitle.PROFESSOR && hIndex < 0)
            throw new IllegalArgumentException("Professor hIndex cannot be negative.");
    }

    // ── Researcher interface ──────────────────────────────────
    @Override public int                    getHIndex()          { return hIndex; }
    @Override public List<ResearchPaper>    getResearchPapers()  { return Collections.unmodifiableList(papers); }
    @Override public List<ResearchProject>  getResearchProjects(){ return Collections.unmodifiableList(projects); }

    @Override
    public void addPaper(ResearchPaper paper) { papers.add(paper); }

    @Override
    public void joinProject(ResearchProject project) {
        project.addParticipant(this);
        projects.add(project);
    }

    public void setHIndex(int h) { this.hIndex = h; }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Researcher, hIndex=%d, papers=%d]",
                hIndex, papers.size());
    }
}
