package university.model;

import university.interfaces.Researcher;

import java.util.*;

/**
 * A bachelor student who is also a Researcher.
 * Demonstrates that Students CAN be researchers (Decorator pattern applied to Student).
 */
public class StudentResearcher extends Student implements Researcher {
    private int hIndex;
    private final List<ResearchPaper>   papers   = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();

    public StudentResearcher(Student base, int hIndex) {
        super(base.getLogin(), base.getPassword(), base.getEmail(),
              base.getStudentId(), base.getYear(), base.getMajor());
        this.hIndex = hIndex;
    }

    @Override public int                   getHIndex()          { return hIndex; }
    @Override public List<ResearchPaper>   getResearchPapers()  { return Collections.unmodifiableList(papers); }
    @Override public List<ResearchProject> getResearchProjects(){ return Collections.unmodifiableList(projects); }

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
        return super.toString() + String.format(" [StudentResearcher, hIndex=%d]", hIndex);
    }
}
