package university.model;

import university.enums.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base for all employees (Teacher, Admin, Manager).
 * Any employee can send messages and complaints to other employees.
 */
public abstract class Employee extends User {
    protected String employeeId;
    protected String department;
    private final List<Message> sentMessages = new ArrayList<>();

    protected Employee(String login, String password, String email,
                       String employeeId, String department, Role role) {
        super(login, password, email, role);
        this.employeeId  = employeeId;
        this.department  = department;
    }

    /** Send a plain message to any other User. */
    public Message sendMessage(User receiver, String content) {
        Message msg = new Message(this, receiver, content);
        sentMessages.add(msg);
        return msg;
    }

    /** Send a complaint (signed document that can be viewed by Dean/Rector). */
    public Complaint sendComplaint(User receiver, String subject, String content) {
        Complaint complaint = new Complaint(this, receiver, subject, content);
        sentMessages.add(complaint);
        return complaint;
    }

    public String       getEmployeeId()  { return employeeId; }
    public String       getDepartment()  { return department; }
    public List<Message> getSentMessages() { return java.util.Collections.unmodifiableList(sentMessages); }

    public void setDepartment(String d) { this.department = d; }
}
