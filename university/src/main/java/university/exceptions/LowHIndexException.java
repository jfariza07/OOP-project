package university.exceptions;

/** Thrown when a supervisor candidate has hIndex < 3 */
public class LowHIndexException extends RuntimeException {
    private final int hIndex;
    public LowHIndexException(int hIndex) {
        super("Supervisor hIndex is " + hIndex + " — minimum required is 3.");
        this.hIndex = hIndex;
    }
    public int getHIndex() { return hIndex; }
}
