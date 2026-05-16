package university.exceptions;

/** Thrown when a non-Researcher tries to join a ResearchProject */
public class NonResearcherException extends RuntimeException {
    public NonResearcherException(String name) {
        super(name + " is not a Researcher and cannot join a ResearchProject.");
    }
}
