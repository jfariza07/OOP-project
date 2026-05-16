package university.model;

import university.exceptions.NonResearcherException;
import university.interfaces.Researcher;

import java.io.Serializable;
import java.util.*;

/**
 * A research project with a topic, participants (must be Researchers), and published papers.
 * If a non-Researcher tries to join → throws NonResearcherException.
 */
public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String topic;
    private final List<Researcher>    participants    = new ArrayList<>();
    private final List<ResearchPaper> publishedPapers = new ArrayList<>();

    public ResearchProject(String topic) {
        this.topic = topic;
    }

    /**
     * Add a participant. The object must implement Researcher.
     * If not → NonResearcherException.
     */
    public void addParticipant(Object candidate) {
        if (!(candidate instanceof Researcher r))
            throw new NonResearcherException(candidate.toString());
        if (!participants.contains(r))
            participants.add(r);
    }

    public void publishPaper(ResearchPaper paper) {
        publishedPapers.add(paper);
    }

    public String            getTopic()           { return topic; }
    public List<Researcher>  getParticipants()    { return Collections.unmodifiableList(participants); }
    public List<ResearchPaper> getPublishedPapers(){ return Collections.unmodifiableList(publishedPapers); }

    @Override
    public String toString() {
        return String.format("ResearchProject{topic='%s', participants=%d, papers=%d}",
                topic, participants.size(), publishedPapers.size());
    }
}
