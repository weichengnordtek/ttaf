package utkereso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

public class RoadNetwork {

	private UndirectedSparseGraph<MapNode, Road> network;
	private LinkedList<MapNode> cities;
	private ArrayList<MapNode> junctions;
	private ArrayList<MapNode> highwayJunctions;
	private LinkedList<Road> roads;

	public RoadNetwork() {
	    network = new UndirectedSparseGraph<MapNode, Road>();
	    cities = buildCityList();
	    junctions = buildJunctionList();
	    highwayJunctions = buildHighwayJunctionList();
	    roads = buildRoadList();
	    
	    network = buildNetwork();
	}

	public LinkedList<MapNode> getCities() {
		return cities;
	}

	protected void setCities(LinkedList<MapNode> cities) {
		this.cities = cities;
	}
	
	public ArrayList<MapNode> getJunctions() {
		return junctions;
	}

	protected void setJunctions(ArrayList<MapNode> junctions) {
		this.junctions = junctions;
	}

	public ArrayList<MapNode> getHighwayJunctions() {
		return highwayJunctions;
	}

	protected void setHighwayJunctions(ArrayList<MapNode> highwayJunctions) {
		this.highwayJunctions = highwayJunctions;
	}

	public LinkedList<Road> getRoads() {
		return roads;
	}

	protected void setRoads(LinkedList<Road> roads) {
		this.roads = roads;
	}

	public UndirectedSparseGraph<MapNode, Road> getNetworkGraph() {
		return network;
	}
	
	public MapNode getCityByName(String name){
		Iterator<MapNode> iterator = cities.iterator();
		while(iterator.hasNext()) {
			MapNode city = iterator.next();
			if (city.getName().equalsIgnoreCase(name)) {
				return city;
			}
		};
		return null;
	} 
	
	public MapNode getJunctionByName(String name){
		Iterator<MapNode> iterator = junctions.iterator();
		while(iterator.hasNext()) {
			MapNode junction = iterator.next();
			if (junction.getName().equalsIgnoreCase(name)) {
				return junction;
			}
		};
		return null;
	} 
	
	public MapNode getNodeByName(String name){
		MapNode foundCity = getCityByName(name);
		if (foundCity == null) {
			return getJunctionByName(name);
		}
		return foundCity;
	} 
	
	public Route findShortestRoute(String startNodeName, String endNodeName) {

	    Transformer<Road, Double> wtTransformer = new Transformer<Road,Double>() {
	        public Double transform(Road link) {
	        return link.weight;
	        }
	    };
	    DijkstraShortestPath<MapNode,Road> pathFinder = new DijkstraShortestPath<MapNode, Road>(network, wtTransformer);
	    List<Road> path = pathFinder.getPath(getNodeByName(startNodeName), getNodeByName(endNodeName));
	    Double dist = pathFinder.getDistance(getNodeByName(startNodeName), getNodeByName(endNodeName)).doubleValue();
	    return new Route(path, dist);
	}
	
	public Route findShortestRouteOnHighway(String startNodeName, String endNodeName) {

	    Transformer<Road, Double> wtTransformer = new Transformer<Road,Double>() {
	        public Double transform(Road link) {
	        	Pair<MapNode> endPoints = network.getEndpoints(link);
	        	if (endPoints.getFirst().getType().equals("h") && endPoints.getSecond().getType().equals("h")) {
	        		return link.weight;
	        	}
	        	else {
	        		return Double.MAX_VALUE;
	        	}
	        }
	    };
	    DijkstraShortestPath<MapNode,Road> pathFinder = new DijkstraShortestPath<MapNode, Road>(network, wtTransformer);
	    List<Road> path = pathFinder.getPath(getNodeByName(startNodeName), getNodeByName(endNodeName));
	    Double dist = pathFinder.getDistance(getNodeByName(startNodeName), getNodeByName(endNodeName)).doubleValue();
	    return new Route(path, dist);
	}
	
	public List<Route> findHighwayRoute(String city1, String city2) {
	    PathFinder floyd1 = new PathFinder(network,getCityByName(city1),highwayJunctions);
	    List<Road> startpointToHighway = floyd1.getShortestPath();
	    Route startRoute = new Route(startpointToHighway, floyd1.getDistance());
	    
	    PathFinder floyd2 =  new PathFinder(network,getCityByName(city2),highwayJunctions);
	    List<Road> endpointTohighway = floyd2.getShortestPath();
	    Route endRoute = new Route(endpointTohighway, floyd2.getDistance());
	    
	    Route highwayRoute = findShortestRouteOnHighway(floyd1.getVegpont().getName(), floyd2.getVegpont().getName());
	    
	    ArrayList<Route> routes = new ArrayList<Route>(3);
	    routes.add(startRoute);
	    routes.add(highwayRoute);
	    routes.add(endRoute);
	    
	    return routes;
	}

	private UndirectedSparseGraph<MapNode, Road> buildNetwork() {
		UndirectedSparseGraph<MapNode, Road> network = new UndirectedSparseGraph<MapNode, Road>();
		network.addEdge(roads.get(0), 	cities.get(0), 		cities.get(3));
	    network.addEdge(roads.get(1), 	junctions.get(0), 	junctions.get(1));
	    network.addEdge(roads.get(2), 	junctions.get(3), 	junctions.get(0));
	    network.addEdge(roads.get(3), 	junctions.get(4), 	junctions.get(1));
	    network.addEdge(roads.get(4), 	junctions.get(2), 	junctions.get(1));
	    network.addEdge(roads.get(5), 	junctions.get(0), 	junctions.get(2));
	    network.addEdge(roads.get(6), 	junctions.get(1), 	junctions.get(3));
	    network.addEdge(roads.get(7),	junctions.get(2), 	cities.get(2));
	    network.addEdge(roads.get(8), 	junctions.get(4), 	junctions.get(5));
	    network.addEdge(roads.get(9), 	junctions.get(5), 	junctions.get(6));
	    network.addEdge(roads.get(10), 	junctions.get(6), 	cities.get(1));
	    network.addEdge(roads.get(13), 	junctions.get(5), 	cities.get(1));
	    network.addEdge(roads.get(11), 	cities.get(3),		cities.get(4));
	    network.addEdge(roads.get(12), 	cities.get(4),		junctions.get(0));
	    network.addEdge(roads.get(14), 	junctions.get(6),   junctions.get(1));
	    return network;
	}
	
	private LinkedList<MapNode> buildCityList() {
		LinkedList<MapNode> cities = new LinkedList<MapNode>();
	    cities.add(new MapNode("Bukarest","v")); 
	    cities.add(new MapNode("Budapest","v"));
	    cities.add(new MapNode("Berlin","v"));
	    cities.add(new MapNode("Megallo1","v"));
	    cities.add(new MapNode("Megallo2","v"));
	    return cities;
	}
	
	private ArrayList<MapNode> buildJunctionList() {
		ArrayList<MapNode> junctions = new ArrayList<MapNode>();
	
		junctions.add(new MapNode("m1","h"));
		junctions.add(new MapNode("m2","h"));
		junctions.add(new MapNode("m3","u"));
		junctions.add(new MapNode("m4","u"));
		junctions.add(new MapNode("m5","h"));
		junctions.add(new MapNode("m6","h"));
		junctions.add(new MapNode("m7","u"));

		return junctions;
	}

	private ArrayList<MapNode> buildHighwayJunctionList() {
		ArrayList<MapNode> highwayJunctions = new ArrayList<MapNode>();
		for (MapNode junction: junctions) {
			if (junction.getType().equals("h")) {
				highwayJunctions.add(junction);
			}
		}
		return highwayJunctions;
	}
	
	private LinkedList<Road> buildRoadList() {
		LinkedList<Road> roads = new LinkedList<Road>();
		
	    roads.add(new Road("ut1",3));
	    roads.add(new Road("ut2",2));
	    roads.add(new Road("ut3",1));
	    roads.add(new Road("ut4",5));
	    roads.add(new Road("ut5",6));
	    roads.add(new Road("ut6",7));
	    roads.add(new Road("ut7",1.5));
	    roads.add(new Road("ut8",9));
	    roads.add(new Road("ut9",1));
	    roads.add(new Road("ut10",2));
	    roads.add(new Road("ut11",2));
	    roads.add(new Road("ut12",2));
	    roads.add(new Road("ut13",1));
	    roads.add(new Road("ut14",5));
	    roads.add(new Road("ut15",3));
	    
	    return roads;
	}
	
}
