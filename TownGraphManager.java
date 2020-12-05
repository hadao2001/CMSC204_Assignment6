
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Manager class for a graph of type TownGraph. 
 * @author Ha T Dao
 */
public class TownGraphManager implements TownGraphManagerInterface {
    
    /**
     * This graph is the map representation
     */
    private Graph graph = new Graph();

    /**
	 * This method populates the graph with all of the towns and roads from a file.
	 * @param selectedFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
    public void populateTownGraph(File selectedFile) 
            throws FileNotFoundException, IOException {
        Scanner readInput = new Scanner(selectedFile);
        String text = "";
        while (readInput.hasNextLine()) {
            text += readInput.nextLine() + " ";
        }
        readInput.close();
        
        String[] roads = text.split(" ");
        String[][] roadsInfo = new String[roads.length][];
        
        for (int i = 0; i < roadsInfo.length; i++) {
            
            roadsInfo[i] = new String[4];
            roadsInfo[i][0] = roads[i].split(";")[0].split(",")[0];
            roadsInfo[i][1] = roads[i].split(";")[0].split(",")[1];
            roadsInfo[i][2] = roads[i].split(";")[1];
            roadsInfo[i][3] = roads[i].split(";")[2];
            
            addTown(roadsInfo[i][2]);
            addTown(roadsInfo[i][3]);
            addRoad(roadsInfo[i][2], roadsInfo[i][3], 
                    Integer.parseInt(roadsInfo[i][1]), roadsInfo[i][0]);
        }
    }
    
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        return graph.shortestPath(new Town(town1), new Town(town2));
    }
    
    /**
	 * Adds a road with 2 towns and a road name
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @param roadName name of road
	 * @return true if the road was added successfully
	 */
	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		
		try {
			Town townA = new Town(town1);
			Town townB = new Town(town2);
			graph.addEdge(townA, townB, weight, roadName);
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}

	/**
	 * Returns the name of the road that both towns are connected through
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return name of road if town 1 and town2 are in the same road, returns null if not
	 */
	@Override
	public String getRoad(String town1, String town2) {

		Town townA, townB;
		townA = new Town(town1);
		townB = new Town(town2);

		return graph.getEdge(townA, townB).getName();
	}

    /**
	 * Adds a town to the graph
	 * @param v the town's name  (lastname, firstname)
	 * @return true if the town was successfully added, false if not
	 */
	@Override
	public boolean addTown(String v) {
		
		try {
			graph.addVertex(new Town(v));
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}

	/**
	 * Gets a town with a given name
	 * @param name the town's name 
	 * @return the Town specified by the name, or null if town does not exist
	 */
	
	@Override
	public Town getTown(String name) {
		Town townA;
		townA = new Town(name);
		for (Town t: graph.vertexSet()) {
			if (t.equals(townA)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Determines if a town is already in the graph
	 * @param v the town's name 
	 * @return true if the town is in the graph, false if not
	 */
	
    @Override
    public boolean containsTown(String v) {
        return graph.containsVertex(getTown(v));
    }

    /**
	 * Determines if a road is in the graph
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return true if the road is in the graph, false if not
	 */
    @Override
	public boolean containsRoadConnection(String town1, String town2) {

    	Town townA, townB;
		townA = new Town(town1);
		townB = new Town(town2);

		return graph.containsEdge(townA, townB);
    }

    /**
	 * Creates an arraylist of all road titles in sorted order by road name
	 * @return an arraylist of all road titles in sorted order by road name
	 */
	
    @Override
    public ArrayList<String> allRoads() {
        ArrayList<String> roads = new ArrayList<>();
        for (Road r : graph.edgeSet()) {
            roads.add(r.getName());
        }
        Collections.sort(roads);
        return roads;
    }

    /**
	 * Deletes a road from the graph
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @param roadName the road name
	 * @return true if the road was successfully deleted, false if not
	 */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String road) {
        int weight = 0;
        for (Road r : graph.edgeSet()) {
            if (r.getName().equals(getRoad(town1, town2))) {
                weight = r.getWeight();
            }
        }
        return graph.removeEdge(new Town(town1), 
                new Town(town2), weight, road) != null;
    }

    /**
	 * Deletes a town from the graph
	 * @param v name of town (lastname, firstname)
	 * @return true if the town was successfully deleted, false if not
	 */
    @Override
	public boolean deleteTown(String v) {
		Town townA;
		townA = new Town(v);
		return graph.removeVertex(townA);
	}

	/**
	 * Creates an arraylist of all towns in alphabetical order (last name, first name)
	 * @return an arraylist of all towns in alphabetical order (last name, first name)
	 */
    @Override
    public ArrayList<String> allTowns() {
        ArrayList<String> towns = new ArrayList<>();
        for (Town t : graph.vertexSet()) {
            towns.add(t.getName());
        }
        Collections.sort(towns);
        return towns;
    }
}