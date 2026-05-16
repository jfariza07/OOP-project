package university.comparators;

import university.model.ResearchPaper;

import java.util.Comparator;

/**
 * Strategy Pattern — injectable comparators for sorting ResearchPapers.
 * Pass any of these into researcher.printPapers(comparator).
 */
public final class ResearchPaperComparators {

    private ResearchPaperComparators() {}

    /** Sort by date published — newest first. */
    public static final Comparator<ResearchPaper> BY_DATE_DESC =
            Comparator.comparing(ResearchPaper::getDatePublished).reversed();

    /** Sort by date published — oldest first. */
    public static final Comparator<ResearchPaper> BY_DATE_ASC =
            Comparator.comparing(ResearchPaper::getDatePublished);

    /** Sort by citations — most cited first. */
    public static final Comparator<ResearchPaper> BY_CITATIONS_DESC =
            Comparator.comparingInt(ResearchPaper::getCitations).reversed();

    /** Sort by citations — least cited first. */
    public static final Comparator<ResearchPaper> BY_CITATIONS_ASC =
            Comparator.comparingInt(ResearchPaper::getCitations);

    /** Sort by article length (pages) — longest first. */
    public static final Comparator<ResearchPaper> BY_LENGTH_DESC =
            Comparator.comparingInt(ResearchPaper::getPages).reversed();

    /** Sort by article length (pages) — shortest first. */
    public static final Comparator<ResearchPaper> BY_LENGTH_ASC =
            Comparator.comparingInt(ResearchPaper::getPages);
}
