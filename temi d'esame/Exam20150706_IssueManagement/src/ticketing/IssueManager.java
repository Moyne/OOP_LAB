package ticketing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class IssueManager {
	private Map<String,Set<UserClass>> users=new HashMap<>();
	private Map<String,Component> components=new HashMap<>();
	private Map<Integer,Ticket> tickets=new TreeMap<>();

    /**
     * Eumeration of valid user classes
     */
    public static enum UserClass {
        /** user able to report an issue and create a corresponding ticket **/
        Reporter, 
        /** user that can be assigned to handle a ticket **/
        Maintainer }
    
    /**
     * Creates a new user
     * 
     * @param username name of the user
     * @param classes user classes
     * @throws TicketException if the username has already been created or if no user class has been specified
     */
    public void createUser(String username, UserClass... classes) throws TicketException {
        if(classes.length==0 || classes==null || users.containsKey(username)) throw new TicketException();
        users.put(username, Set.of(classes));
    }

    /**
     * Creates a new user
     * 
     * @param username name of the user
     * @param classes user classes
     * @throws TicketException if the username has already been created or if no user class has been specified
     */
    public void createUser(String username, Set<UserClass> classes) throws TicketException {
    	if(classes.size()==0 || classes==null || users.containsKey(username)) throw new TicketException();
        users.put(username, classes);
    }
   
    /**
     * Retrieves the user classes for a given user
     * 
     * @param username name of the user
     * @return the set of user classes the user belongs to
     */
    public Set<UserClass> getUserClasses(String username){
        return users.get(username);
    }
    
    /**
     * Creates a new component
     * 
     * @param name unique name of the new component
     * @throws TicketException if a component with the same name already exists
     */
    public void defineComponent(String name) throws TicketException {
        if(components.containsKey(name)) throw new TicketException();
        components.put(name, new Component(name));
    }
    
    /**
     * Creates a new sub-component as a child of an existing parent component
     * 
     * @param name unique name of the new component
     * @param parentPath path of the parent component
     * @throws TicketException if the the parent component does not exist or 
     *                          if a sub-component of the same parent exists with the same name
     */
    public void defineSubComponent(String name, String parentPath) throws TicketException {
        List<String> path=List.of(parentPath.split("/"));
        if(!components.containsKey(path.get(1))) throw new TicketException();
        Component x=components.get(path.get(1));
        for(int i=2;i<path.size();i++) {
        	if(x.hasSub(path.get(i))) {
        		x=x.getSub(path.get(i));
        	}
        	else throw new TicketException();
        }
	if(x.hasSub(name)) throw new TicketException();
        x.addSub(name);
    }
    
    /**
     * Retrieves the sub-components of an existing component
     * 
     * @param path the path of the parent
     * @return set of children sub-components
     */
    public Set<String> getSubComponents(String path){
    	List<String> pathR=List.of(path.split("/"));
        if(!components.containsKey(pathR.get(1))) return null;
        Component x=components.get(pathR.get(1));
        for(int i=2;i<pathR.size();i++) {
        	if(x.hasSub(pathR.get(i))) {
        		x=x.getSub(pathR.get(i));
        	}
        	else return null;
        }
        return x.getSub();
    }

    /**
     * Retrieves the parent component
     * 
     * @param path the path of the parent
     * @return name of the parent
     */
    public String getParentComponent(String path){
    	if(path.lastIndexOf('/')==0) return null;
    	else return path.substring(0,path.lastIndexOf('/'));
    }

    /**
     * Opens a new ticket to report an issue/malfunction
     * 
     * @param username name of the reporting user
     * @param componentPath path of the component or sub-component
     * @param description description of the malfunction
     * @param severity severity level
     * 
     * @return unique id of the new ticket
     * 
     * @throws TicketException if the user name is not valid, the path does not correspond to a defined component, 
     *                          or the user does not belong to the Reporter {@link IssueManager.UserClass}.
     */
    public int openTicket(String username, String componentPath, String description, Ticket.Severity severity) throws TicketException {
    	if(!users.containsKey(username)) throw new TicketException();
    	if(!users.get(username).contains(UserClass.Reporter)) throw new TicketException();
    	List<String> path=List.of(componentPath.split("/"));
        if(!components.containsKey(path.get(1))) throw new TicketException();
        Component x=components.get(path.get(1));
        for(int i=2;i<path.size();i++) {
        	if(x.hasSub(path.get(i))) {
        		x=x.getSub(path.get(i));
        	}
        	else throw new TicketException();
        }
        int i=tickets.size()+1;
        tickets.put(i, new Ticket(i,username,x,description,severity));
        return i;
    }
    
    /**
     * Returns a ticket object given its id
     * 
     * @param ticketId id of the tickets
     * @return the corresponding ticket object
     */
    public Ticket getTicket(int ticketId){
        if(!tickets.containsKey(ticketId)) return null;
        return tickets.get(ticketId);
    }
    
    /**
     * Returns all the existing tickets sorted by severity
     * 
     * @return list of ticket objects
     */
    public List<Ticket> getAllTickets(){
        return tickets.values().stream().sorted((e1,e2)->e1.getSeverity().compareTo(e2.getSeverity())).collect(Collectors.toList());
    }
    
    /**
     * Assign a maintainer to an open ticket
     * 
     * @param ticketId  id of the ticket
     * @param username  name of the maintainer
     * @throws TicketException if the ticket is in state <i>Closed</i>, the ticket id or the username
     *                          are not valid, or the user does not belong to the <i>Maintainer</i> user class
     */
    public void assingTicket(int ticketId, String username) throws TicketException {
        if(!users.containsKey(username) || !tickets.containsKey(ticketId)) throw new TicketException();
        if(!users.get(username).contains(UserClass.Maintainer) || tickets.get(ticketId).isAssigned() || tickets.get(ticketId).isClosed()) throw new TicketException();
        tickets.get(ticketId).assignTicket(username);
    }

    /**
     * Closes a ticket
     * 
     * @param ticketId id of the ticket
     * @param description description of how the issue was handled and solved
     * @throws TicketException if the ticket is not in state <i>Assigned</i>
     */
    public void closeTicket(int ticketId, String description) throws TicketException {
        if(!tickets.containsKey(ticketId)) throw new TicketException();
        if(!tickets.get(ticketId).isAssigned()) throw new TicketException();
        tickets.get(ticketId).close(description);
    }

    /**
     * returns a sorted map (keys sorted in natural order) with the number of  
     * tickets per Severity, considering only the tickets with the specific state.
     *  
     * @param state state of the tickets to be counted, all tickets are counted if <i>null</i>
     * @return a map with the severity and the corresponding count 
     */
    public SortedMap<Ticket.Severity,Long> countBySeverityOfState(Ticket.State state){
        if(state==null) return tickets.values().stream().collect(Collectors.groupingBy(e->e.getSeverity(), TreeMap::new, Collectors.counting()));
        return tickets.values().stream().filter(e->e.getState().equals(state)).collect(Collectors.groupingBy(e->e.getSeverity(), TreeMap::new, Collectors.counting()));
    }

    /**
     * Find the top maintainers in terms of closed tickets.
     * 
     * The elements are strings formatted as <code>"username:###"</code> where <code>username</code> 
     * is the user name and <code>###</code> is the number of closed tickets. 
     * The list is sorter by descending number of closed tickets and then by username.

     * @return A list of strings with the top maintainers.
     */
    public List<String> topMaintainers(){
        return users.entrySet().stream().filter(e->e.getValue().contains(UserClass.Maintainer)).sorted((e1,e2)->{
        	Long z=tickets.values().stream().filter(d->d.isClosed()).filter(d->d.getMant().equals(e2.getKey())).count()-
        			tickets.values().stream().filter(d->d.isClosed()).filter(d->d.getMant().equals(e1.getKey())).count();
        	if(z==0) return e1.getKey().compareTo(e2.getKey());
        	return z.intValue();
        }).map(e->e.getKey()+":"+tickets.values().stream().filter(d->d.isClosed()).filter(d->d.getMant().equals(e.getKey())).count()).collect(Collectors.toList());
    }

}
