package university.service;

import university.comparators.ResearchPaperComparators;
import university.interfaces.Researcher;
import university.model.ResearchPaper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles university-wide research operations:
 * - Print all papers sorted by various criteria.
 * - Find top-cited researcher of a school or year.
 */
public class ResearchService {

    private final DataStore store = DataStore.getInstance();

    /** Collect all Researchers in the system. */
    private List<Researcher> getAllResearchers() {
        return store.getAllUsers().stream()
                    .filter(u -> u instanceof Researcher)
                    .map(u -> (Researcher) u)
                    .collect(Collectors.toList());
    }

    /** Print every research paper in the university, sorted by the given comparator. */
    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> all = getAllResearchers().stream()
                .flatMap(r -> r.getResearchPapers().stream())
                .distinct()
                .sorted(comparator)
                .toList();

        System.out.println("=== ALL RESEARCH PAPERS (sorted) ===");
        all.forEach(System.out::println);
        System.out.println("Total: " + all.size());
    }

    /** Print papers sorted by date published. */
    public void printAllPapersByDate() {
        printAllPapers(ResearchPaperComparators.BY_DATE_DESC);
    }

    /** Print papers sorted by citations. */
    public void printAllPapersByCitations() {
        printAllPapers(ResearchPaperComparators.BY_CITATIONS_DESC);
    }

    /** Print papers sorted by length (pages). */
    public void printAllPapersByLength() {
        printAllPapers(ResearchPaperComparators.BY_LENGTH_DESC);
    }

    /**
     * Top-cited researcher of a specific school (department filter).
     * @param department department/school name to filter by
     */
    public Optional<Researcher> getTopResearcherOfSchool(String department) {
        return store.getAllUsers().stream()
                .filter(u -> u instanceof Researcher)
                .filter(u -> {
                    // Teachers and Employees have department info
                    if (u instanceof university.model.Employee e)
                        return e.getDepartment().equalsIgnoreCase(department);
                    return false;
                })
                .map(u -> (Researcher) u)
                .max(Comparator.comparingInt(Researcher::getHIndex));
    }

    /**
     * Top-cited researcher of a given year (by total citations of their papers).
     * @param year publication year to filter papers
     */
    public Optional<Researcher> getTopResearcherOfYear(int year) {
        return getAllResearchers().stream()
                .max(Comparator.comparingInt(r ->
                    r.getResearchPapers().stream()
                     .filter(p -> p.getDatePublished().getYear() == year)
                     .mapToInt(ResearchPaper::getCitations)
                     .sum()
                ));
    }

    /** Print top-cited researcher of the given year with their stats. */
    public void printTopResearcherOfYear(int year) {
        getTopResearcherOfYear(year).ifPresentOrElse(
            r -> {
                int totalCitations = r.getResearchPapers().stream()
                        .filter(p -> p.getDatePublished().getYear() == year)
                        .mapToInt(ResearchPaper::getCitations).sum();
                System.out.printf("Top Researcher of %d: %s | hIndex=%d | Citations in %d=%d%n",
                        year, r, r.getHIndex(), year, totalCitations);
            },
            () -> System.out.println("No researchers found for year " + year)
        );
    }
}
