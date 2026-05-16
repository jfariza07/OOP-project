package university.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Research paper modelled after IEEE-style fields.
 * Fields chosen from https://ieeexplore.ieee.org/document/9766691
 */
public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private static final long serialVersionUID = 1L;

    private final String     doi;
    private String           title;
    private List<String>     authors;
    private String           journal;
    private int              citations;
    private int              pages;          // article length proxy
    private LocalDate        datePublished;
    private List<String>     keywords;
    private int              volume;
    private String           abstractText;

    public ResearchPaper(String doi, String title, List<String> authors, String journal,
                         int citations, int pages, LocalDate datePublished,
                         List<String> keywords, int volume, String abstractText) {
        this.doi           = doi;
        this.title         = title;
        this.authors       = List.copyOf(authors);
        this.journal       = journal;
        this.citations     = citations;
        this.pages         = pages;
        this.datePublished = datePublished;
        this.keywords      = List.copyOf(keywords);
        this.volume        = volume;
        this.abstractText  = abstractText;
    }

    // ── Comparable: default order by citations desc ───────────
    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations); // descending
    }

    // ── Getters ───────────────────────────────────────────────
    public String     getDoi()           { return doi; }
    public String     getTitle()         { return title; }
    public List<String> getAuthors()     { return authors; }
    public String     getJournal()       { return journal; }
    public int        getCitations()     { return citations; }
    public int        getPages()         { return pages; }
    public LocalDate  getDatePublished() { return datePublished; }
    public List<String> getKeywords()    { return keywords; }
    public int        getVolume()        { return volume; }
    public String     getAbstractText()  { return abstractText; }

    public void setCitations(int c) { this.citations = c; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResearchPaper p)) return false;
        return Objects.equals(doi, p.doi);
    }
    @Override public int hashCode() { return Objects.hash(doi); }

    @Override
    public String toString() {
        return String.format("Paper{doi='%s', title='%s', citations=%d, pages=%d, date=%s, journal='%s'}",
                doi, title, citations, pages, datePublished, journal);
    }
}
