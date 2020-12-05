
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This data structure class represents of the graph
 * that represents a map with towns and roads connecting these towns. 
 * @author Ha T Dao
 */
public class Graph implements GraphInterface<Town, Road> {

    private Town destination;
    
    private int endTown;
    /**
     * Towns in the graph
     */
    private Set<Town> towns = new HashSet<>();
    
    /**
     * Roads in the graph based on the towns
     */
    private Set<Road> roads = new HashSet<>();
    
    /**
     * List of shortest path from town A to town B
     */    
    private ArrayList<String> shortestPath = new ArrayList<>();
    
    
    /**
     * Returns an edge connecting source vertex to target vertex if such
     * vertices and such edge exist in this graph. Otherwise returns
     * null. 
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @return an edge connecting source vertex to target vertex.
     */
    @Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		if (sourceVertex == null || destinationVertex == null)
			return null;

		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex))
			return null;

		for (Road r : edgesOf(sourceVertex)) {
			if (r.getDestination().equals(destinationVertex))
				return r;
		} // look through adjacent vertices

		return null;
	}//getEdge

    /**
	 * Add a road between two existing towns in the graph.
	 * @param sourceVertex source town
	 * @param destinationVertex destination town
	 * @param weight the length of the road in miles
	 * @param description the name of the road
	 * @return road the road that was created
	 * @throws IllegalArgumentException if either town does not exist in the graph
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, 
            int weight, String description) {
        
        if (sourceVertex == null || destinationVertex == null) {
            throw new NullPointerException();
        }
        
        if (!towns.contains(sourceVertex) || !towns.contains(destinationVertex)) { 
            throw new IllegalArgumentException();
        }
        
        Road road = new Road(sourceVertex, destinationVertex, weight, description);
        roads.add(road);
        
        return road;
    }

    /**
	 * Add a town to the graph.
	 * @param town the town to be added
	 * @return true if successfully added
	 */
    @Override
    public boolean addVertex(Town t) {
        
        if (t == null) {
            throw new NullPointerException();
        }
        
        if (!towns.contains(t)) {
            towns.add(t);
            return true;
        }
        
        return false;
    }

    /**
	 * Check if a road exists between two towns.
	 * @param sourceVertex the source town
	 * @param destinationVertex the destination town
	 * @return true if the road does exist, false if not
	 */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        for (Road r : roads) {
            if (r.contains(sourceVertex) && r.contains(destinationVertex)) {
                return true;
            }
        }
        return false;
    }

    /**
	 * Check if the graph contains a town.
	 * @param town the town to check for
	 * @return true if the town exists, false if not
	 */
    @Override
    public boolean containsVertex(Town t) {
        return towns.contains(t);
    }

    /**
	 * Return the set of roads in the graph.
	 * @return roads
	 */
    @Override
    public Set<Road> edgeSet() {
        return roads;
    }

    /**
	 * Get all the roads of a town in the graph.
	 * @param vertex the town to check
	 * @return the roads leading out of it
     * @throws NullPointerException if vertex is null
     */
    @Override
    public Set<Road> edgesOf(Town vertex) {
        Set<Road> edges = new HashSet<>();
        for (Road r : roads) {
            if (r.contains(vertex)) {
                edges.add(r);
            }
        }
        return edges;
    }

    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, 
            int weight, String description) {
        
        if (sourceVertex == null || destinationVertex == null || description == null) {
            throw new NullPointerException();
        }
        
        if (!towns.contains(sourceVertex) || !towns.contains(destinationVertex)) { 
            throw new IllegalArgumentException();
        }
        
        Road road = null;
        for (Road r : roads) {
            if (r.contains(sourceVertex) && r.contains(destinationVertex) &&
                    r.getWeight() == weight && r.getName().equals(description)) {
                road = r;
            }
        }
        return roads.remove(road) ? road : null;
    }

    @Override
    public boolean removeVertex(Town t) {
        return towns.remove(t);
    }

    @Override
    public Set<Town> vertexSet() {
        return towns;
    }

    /**
	 * Gets the shortest path between two points by calculating the shortest path to all point from
	 * the source vertex using dijstrka's algorithm.
	 * @param sourceVertex the source of the path
	 * @param destinationVertex the end of the path
	 * @return history the traversal order of towns in arraylist form
	 */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        destination = destinationVertex;
        dijkstraShortestPath(sourceVertex);
        String shortPath = "";
        int totalMiles = 0;
        for (int idx = 0; idx < shortestPath.size() - 1; idx++) {
            Town source = new Town(shortestPath.get(idx));
            Town dest = new Town(shortestPath.get(idx + 1));
            Road road = getEdge(source, dest);
            totalMiles += road.getWeight();
            shortPath += source + " via " + road.getName() + " to " + dest 
                    + " " + road.getWeight() + " miles;";
        }
        shortestPath.clear();
        for (String step : shortPath.split(";")) {
            shortestPath.add(step);
        }
        shortestPath.add("Total miles: " + totalMiles + " miles");
        return shortestPath;
    }

    
    /**
	 * Fill the preset dijstrka fields to paths according to dijstrka's shortest path algorithm.
	 * @param startVertex the startpoint of the algorithm
	 */
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        shortestPath.clear();
        Town[] size_all_town = new Town[towns.size()];
        int s = 0;
        for (Town t : towns) {
            size_all_town[s] = new Town(t);
            s++;
        }        
        int[][] adjacencyMatrix = new int[towns.size()][towns.size()];       
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (i == j || !containsEdge(size_all_town[i], size_all_town[j])) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    int weight = getEdge(size_all_town[i], size_all_town[j]).getWeight();
                    adjacencyMatrix[i][j] = adjacencyMatrix[j][i] = weight;
                }
            }
        }
        
        int startTown = 0;
        for (Town t : size_all_town) {
            if (!t.equals(sourceVertex)) {
                startTown++;
            } else {
                break;
            }
        }
        
        endTown = 0;
        for (Town t : size_all_town) {
            if (!t.equals(destination)) {
                endTown++;
            } else {
                break;
            }
        }
        
        int numTowns = adjacencyMatrix[0].length; 
        
        int[] smallestWeights = new int[numTowns];
        
        boolean[] added = new boolean[numTowns];
        
        for (int townIdx = 0; townIdx < numTowns; townIdx++) {
            smallestWeights[townIdx] = Integer.MAX_VALUE;
            added[townIdx] = false;
        }
        
        smallestWeights[startTown] = 0;
        
        int[] parents = new int[numTowns];
        
        parents[startTown] = -1;
        
        for (int i = 1; i < numTowns; i++) {
            int nearestTown = -1;
            int smallestWeight = Integer.MAX_VALUE;
            for (int townIdx = 0; townIdx < numTowns; townIdx++) {
                if (!added[townIdx] && 
                        smallestWeights[townIdx] < smallestWeight) {
                    nearestTown = townIdx;
                    smallestWeight = smallestWeights[townIdx];
                }
            }
            added[nearestTown] = true;
            for (int townIdx = 0; townIdx < numTowns; townIdx++) {
                int roadDist = adjacencyMatrix[nearestTown][townIdx]; 
                if (roadDist > 0 && 
                        ((smallestWeight + roadDist) < smallestWeights[townIdx])) {
                    parents[townIdx] = nearestTown;
                    smallestWeights[townIdx] = smallestWeight + roadDist;
                }
            }           
        }
        populatePathArrayList(endTown, parents); 
    }
    
    /**
     * Populate town with the order of towns to go from source to destination
     * @param currentVertex index of destination
     * @param parents indexes of towns in shortest path
     */
    private void populatePathArrayList(int currentVertex, int[] parents) {
        
        if (currentVertex == -1) { 
            return; 
        } 
        populatePathArrayList(parents[currentVertex], parents); 
        int townIdx = 0;
        for (Town t : towns) {
            if (townIdx == currentVertex) {
                shortestPath.add(t.getName()); 
            }
            townIdx++;
        }
    } 

}
