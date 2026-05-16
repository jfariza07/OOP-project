package university.interfaces;

import university.model.ResearchPaper;
import university.model.ResearchProject;

import java.util.Comparator;
import java.util.List;

/**
 * Researcher is an interface.
 * Any User (Teacher, Student, or standalone Employee) can implement it.
 * Professors ALWAYS implement it; others may optionally implement it.
 */
public interface Researcher {

    int getHIndex();

    List<ResearchPaper> getResearchPapers();

    List<ResearchProject> getResearchProjects();

    void addPaper(ResearchPaper paper);

    void joinProject(ResearchProject project);

    /**
     * Prints research papers sorted by the given comparator.
     * Strategy Pattern — the sorting strategy is injected at runtime.
     */
    default void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sorted = new java.util.ArrayList<>(getResearchPapers());
        sorted.sort(comparator);
        sorted.forEach(System.out::println);
    }
}
