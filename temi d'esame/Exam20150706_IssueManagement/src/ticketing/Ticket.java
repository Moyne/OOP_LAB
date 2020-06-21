package ticketing;

/**
 * Class representing the ticket linked to an issue or malfunction.
 * 
 * The ticket is characterized by a severity and a state.
 */
public class Ticket {
    private int id;
    private String username;
    private Component component;
    private String description;
    private Severity severity;
    private State state;
    private String assignedTo;
    private String solution;
    /**
     * Enumeration of possible severity levels for the tickets.
     * 
     * Note: the natural order corresponds to the order of declaration
     */
    public enum Severity { Blocking, Critical, Major, Minor, Cosmetic };
    
    /**
     * Enumeration of the possible valid states for a ticket
     */
    public static enum State { Open, Assigned, Closed }
    
    public Ticket(int id,String username, Component x, String description, Severity severity) {
		this.id=id;
		this.component=x;
		this.description=description;
		this.severity=severity;
		this.state=State.Open;
	}

	public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }
    
    public Severity getSeverity() {
        return severity;
    }

    public String getAuthor(){
        return username;
    }
    
    public String getComponent(){
        return component.getName();
    }
    
    public State getState(){
        return state;
    }
    
    public String getSolutionDescription() throws TicketException {
        if(!state.equals(State.Closed)) throw new TicketException();
        return solution;
    }

	public void assignTicket(String username) {
		this.state=State.Assigned;
		this.assignedTo=username;
	}

	public boolean isAssigned() {
		return state.equals(State.Assigned);
	}

	public void close(String description) {
		this.state=State.Closed;
		this.solution=description;
	}
	public String getMant() {
		return assignedTo;
	}
	public boolean isClosed() {
		return state.equals(State.Closed);
	}
}
