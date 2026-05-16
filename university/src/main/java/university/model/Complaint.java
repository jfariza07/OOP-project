package university.model;

public class Complaint extends Message {
    private final String subject;
    private boolean signedByDeanOrRector = false;

    public Complaint(User sender, User receiver, String subject, String content) {
        super(sender, receiver, content);
        this.subject = subject;
    }

    public String  getSubject()              { return subject; }
    public boolean isSignedByDeanOrRector()  { return signedByDeanOrRector; }
    public void    sign()                    { this.signedByDeanOrRector = true; }

    @Override
    public String toString() {
        return String.format("[COMPLAINT | %s] %s → %s | Subject: %s | Signed: %s",
                getTimestamp(), sender.getLogin(), receiver.getLogin(), subject, signedByDeanOrRector);
    }
}
