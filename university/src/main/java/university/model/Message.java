package university.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final User          sender;
    protected final User          receiver;
    protected final String        content;
    protected final LocalDateTime timestamp;

    public Message(User sender, User receiver, String content) {
        this.sender    = sender;
        this.receiver  = receiver;
        this.content   = content;
        this.timestamp = LocalDateTime.now();
    }

    public User          getSender()    { return sender; }
    public User          getReceiver()  { return receiver; }
    public String        getContent()   { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message m)) return false;
        return Objects.equals(sender, m.sender)
            && Objects.equals(receiver, m.receiver)
            && Objects.equals(timestamp, m.timestamp);
    }

    @Override public int hashCode() { return Objects.hash(sender, receiver, timestamp); }

    @Override
    public String toString() {
        return String.format("[%s] %s → %s: %s", timestamp, sender.getLogin(), receiver.getLogin(), content);
    }
}
