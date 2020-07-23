package evaluation;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Facade class for the research evaluation system
 *
 */
public class Evaluations {
	private Map<Integer,Integer> levels=new HashMap<>();
	private Map<String,Journal> journals=new HashMap<>();
	private List<String> disciplines=new ArrayList<>();
	private Map<String,Group> groups=new HashMap<>();
	private Map<String,Paper> papers=new HashMap<>();
    //R1
    /**
     * Define number of levels and relative points.
     * 
     * Levels are numbered from 1 on, and points must be strictly decreasing
     *  
     * @param points points for the levels
     * @throws EvaluationsException thrown if points are not decreasing
     */
    public void addPointsForLevels (int... points) throws EvaluationsException {
    	for(int i=0;i<points.length;i++) {
    		if(points[i]<2) throw new EvaluationsException();
    		if(i==0) {
    			if(levels.size()!=0 && points[i]>levels.values().stream().mapToInt(e->e).min().orElse(0)) {
    				throw new EvaluationsException();
    			}
    		}
    		else {
    			if(points[i]>points[i-1]) throw new EvaluationsException();
    		}
    	}
    	for(int x:points) {
    		int z=levels.size()+1;
    		levels.put(z, x);
    	}
    }

    /**
     * Retrieves the points for the given level.
     * 
     * @param level level for which points are required
     * @return points for the level
     */
    public int getPointsOfLevel (int level) {
        if(!levels.containsKey(level))	return -1;
        return levels.get(level);
    }

    /**
     * Add a new journal for a given disciplines and provides the corresponding level.
     * 
     * The level determines the points for the article published in the journal.
     * 
     * @param name name of the new journal
     * @param discipline reference discipline for the journal
     * @param level level for the journal.
     * @throws EvaluationsException thrown if the specified level does not exist
     */
    public void addJournal (String name, String discipline, int level) throws EvaluationsException {
    	if(!levels.containsKey(level)) throw new EvaluationsException();
    	if(!disciplines.contains(discipline)) disciplines.add(discipline);
    	journals.put(name, new Journal(name,discipline,level));
    }

    /**
     * Retrieves number of journals.
     * 
     * @return journals count
     */
    public int countJournals() {
        return journals.size();
    }

    /**
     * Retrieves all the journals for a given discipline.
     * 
     * @param discipline the required discipline
     * @return list of journals (sorted alphabetically)
     */
    public List<String> getJournalNamesOfAGivenDiscipline(String discipline) {
    	if(journals.values().stream().filter(e->e.getDiscipline().equals(discipline)).count()==0) return new ArrayList<String>();
        return journals.values().stream().filter(e->e.getDiscipline().equals(discipline)).
        		map(e->e.getName()).sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
    }

    //R2
    /**
     * Add a research group and the relative disciplines.
     * 
     * @param name name of the research group
     * @param disciplines list of disciplines
     * @throws EvaluationsException thrown in case of duplicate name
     */
    public void addGroup (String name, String... disciplines) throws EvaluationsException {
    	if(groups.containsKey(name)) throw new EvaluationsException();
    	groups.put(name, new Group(name,disciplines));
    }

    /**
     * Define the members for a previously defined research group.
     * 
     * @param groupName name of the group
     * @param memberNames list of group members
     * @throws EvaluationsException thrown if name not previously defined.
     */
    public void setMembers (String groupName, String... memberNames) throws EvaluationsException {
    	if(!groups.containsKey(groupName)) throw new EvaluationsException();
    	groups.get(groupName).setMembers(memberNames);
    }

    /**
     * Return list of members of a group.
     * The list is sorted alphabetically.
     * 
     * @param groupName name of the group
     * @return list of members
     */
    public List<String >getMembers(String groupName){
        if(!groups.containsKey(groupName)) return new ArrayList<String>();
        return groups.get(groupName).getMembersOrdered();
    }

    /**
     * Retrieves the group names working on a given discipline
     * 
     * @param discipline the discipline of interest
     * @return list of group names sorted alphabetically
     */
    public List<String> getGroupNamesOfAGivenDiscipline(String discipline) {
    	if(!disciplines.contains(discipline)) return new ArrayList<>();
        return groups.values().stream().filter(e->e.hasInterest(discipline)).map(e->e.getName()).sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
    }

    //R3
    /**
     * Add a new journal articles, with a given title and the list of authors.
     * 
     * The journal must have been previously defined.
     * 
     * The authors (at least one) are members of research groups.
     * 
     * @param title title of the article
     * @param journalName name of the journal
     * @param authorNames list of authors
     * @throws EvaluationsException thrown if journal not defined or no author provided
     */
    public void addPaper (String title, String journalName, String... authorNames) throws EvaluationsException {
    	if(!journals.containsKey(journalName) || authorNames.length==0) throw new EvaluationsException();
    	papers.put(title, new Paper(title,journalName,authorNames));
    }



    /**
     * Retrieves the titles of the articles authored by a member of a research group
     * 
     * @param memberName name of the group member
     * @return list of titles sorted alphabetically
     */
    public List<String> getTitlesOfAGivenAuthor (String memberName) {
        return papers.values().stream().filter(e->e.hasAuthor(memberName)).map(e->e.getTitle())
        		.sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
    }


    //R4
    /**
     * Returns the points for a given group member.
     * 
     * Points are collected for each article the member authored.
     * The points are those corresponding to the level of the
     * journal where the article is published, divided by
     * the total number of authors.
     * 
     * The total points are eventually rounded to the closest integer.
     * 
     * @param memberName name of the group member
     * @return total points
     */
    public int getPointsOfAGivenAuthor (String memberName) {
        return (int) Math.round(papers.values().stream().filter(e->e.hasAuthor(memberName)).
        mapToDouble(e->levels.get(journals.get(e.getJournal()).getLevel())/
        		(e.getNauthors())).sum());
    }

    /**
     * Computes the total points collected by all members of all groups
     *  
     * @return the total points
     */
    public int evaluate() {
    	return (int) Math.round(papers.values().stream().
    	        mapToDouble(e->levels.get(journals.get(e.getJournal()).getLevel())).sum());
    }


    //R5 Statistiche
    /**
     * For each group return the total points collected
     * by all the members of each research group.
     * 
     * Group names are sorted alphabetically.
     * 
     * @return the map associating group name to points
     */
    public SortedMap<String, Integer> pointsForGroup() {
        return groups.values().stream().collect(Collectors.groupingBy
        		(e->e.getName(), TreeMap::new, Collectors.summingInt
        				(e->e.getMembers().stream().mapToInt(d->this.getPointsOfAGivenAuthor(d))
        						.sum())));
    }

    /**
     * For each amount of points returns a list of
     * the authors (group members) that achieved such score.
     * 
     * Points are sorted in decreasing order.
     * 
     * @return the map linking the number of point to the list of authors
     */
    public SortedMap<Integer, List<String>> getAuthorNamesPerPoints () {
        return groups.values().stream().flatMap(e->e.getMembers().stream()).filter
        		(e->this.getPointsOfAGivenAuthor(e)!=0).collect(Collectors.groupingBy
        				(e->this.getPointsOfAGivenAuthor(e), ()->{return new TreeMap<Integer,List<String>>((e1,e2)->e2-e1);},
    			Collector.of(ArrayList::new,(List<String> a,String b)->a.add(b),(a,b)->{a.addAll(b);return a;},
    					a->{a.sort((e1,e2)->e1.compareTo(e2));return a;})));
    }


}