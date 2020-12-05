
/**
 * Represents a road/edge in a graph of cities. All roads are BIDIRECTIONAL and SYMMETRIC.
 * @author Ha T Dao
 *
 */

public class Road implements Comparable<Road>{
    
   
    //Weight of edge
    private int weight;
    //Road name
    private String name;
    //A town on the road
    private Town source;
    //Another town on the road
    private Town destination;
    
    /**
     * Constructor
     * @param source One town on the road
     * @param destination Another town on the road
     * @param weight Weight of the edge, i.e., distance from one town to the other
     * @param name Name of the road
     */
    public Road(Town source, Town destination, int weight, String name) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.name = name;
    }
    
    /**
     * Constructor with weight preset at 1
     * @param source One town on the road
     * @param destination Another town on the road
     * @param name Name of the road
     */
    public Road(Town source, Town destination, String name) {
        this.source = source;
        this.destination = destination;
        this.weight = 1;
        this.name = name;
    }
    
    public Road(Road templateRoad) {
        this(templateRoad.source, templateRoad.destination, 
                templateRoad.weight, templateRoad.name);
    }
    
    /**
	 * This method compares the road names to see if they are the same.
	 */
	@Override
	public int compareTo(Road road) {
		return this.name.compareTo(road.name);
	}
	
	/**
	 * Check if the road/edge contains the given town
	 * @param town a vertex of the graph
	 * @return true only if the edge is connected to the given vertex
	 */
	public boolean contains(Town town) {
		return source.getName().equals(town.getName()) || destination.getName().equals(town.getName());
	}
	
	/**
	 * This method returns a string with the name of the road, the 
	 * length in miles, and the source and destination towns.
	 */
	public String toString() {
		return 	name + "," + weight + "," + source + ";" + destination;
	}
	
	/**
	 * This method returns first town on the road
	 * @return first town
	 */
	public Town getSource() {
        return source;
    }
	
	/**
	 * This method returns second town
	 * @return second Town
	 */
	public Town getDestination() {
		return destination;
	}
	
	/**
	 * This method returns the weight(distance) of road
	 * @return weight 
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * This method sets the weight of road
	 * @param distance weight of road
	 */
	public void setDistance(int distance) {
		this.weight = distance;
	}
	/**
	 * This method returns name of road
	 * @return name of road
	 */
	public String getName() {
		return name;
	}
	/**
	 * This method sets name of road
	 * @param name name of road
	 */
	public void setName(String name) {
		this.name = name;
	}
}
    