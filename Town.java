
/**
 * /**
 * Class to represent a town, the node of the graph.
 * @author Ha T Dao
 */
public class Town implements Comparable<Town>{
    
    //Town's name
    private String name;
    
    /**
     * Constructor - Requires town's name.
     * @param name town's name
     */
    public Town(String name) {
        this.name = name;
    }
    
    /**
     * Copy constructor
     * @param templateTown an instance of Town
     */
    public Town(Town templateTown) {
        this(templateTown.name);
    }
    
    /**
     * Returns the town's name
     * @return town's name
     */
    public String getName() {
        return name;
    }
        
    /**
	 * Compare to method
	 * @return town's name
	 */
    @Override
    public int compareTo(Town o) {
        return this.name.compareTo(o.name);
    }
    
    /**
	 * Equivalency check
	 * @return true if the town names are equal, false if not
	 */
    @Override
    public boolean equals(Object obj) {
        Town town = (Town) obj;
        return this.name.compareTo(town.name) == 0;
    }
    
    /**
     * Hash code for town
     * @return the hash code for the name of the town
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    /**
     * String representation of town
     * @return the town name
     */
    @Override
    public String toString(){
        return name;
    }
}