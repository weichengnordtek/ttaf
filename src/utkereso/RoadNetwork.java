package utkereso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

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
	    highwayJunctions = junctions;
	    roads = buildRoadList();
	    
	    network = buildNetwork();
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
	
	public Route findShortestRoute(String city1, String city2) {

	    Transformer<Road, Double> wtTransformer = new Transformer<Road,Double>() {
	        public Double transform(Road link) {
	        return link.weight;
	        }
	    };
	    DijkstraShortestPath<MapNode,Road> pathFinder = new DijkstraShortestPath<MapNode, Road>(network, wtTransformer);
	    List<Road> path = pathFinder.getPath(getCityByName(city1), getCityByName(city2));
	    Double dist = pathFinder.getDistance(getCityByName(city1), getCityByName(city2)).doubleValue();
	    return new Route(path, dist);
	}
	
	public List<Route> findHighwayRoute(String city1, String city2) {
	    //Startbol -> legközelebbi autopalya 
	    Bejaras Floyd =  new Bejaras(network,getCityByName(city1),highwayJunctions);
	    List<Road> startpointToHighway =Floyd.getShortestPath();
	    Route startRoute = new Route(startpointToHighway, Floyd.getDistance());
	    
	    //Vegpontbol -> legközelebbi autopalya
	    Bejaras Floyd2 =  new Bejaras(network,getCityByName(city2),highwayJunctions);
	    List<Road> endpointTohighway =Floyd2.getShortestPath();
	    Route endRoute = new Route(endpointTohighway, Floyd2.getDistance());
	    
	    Route highwayRoute = findShortestRoute(city1, city2);
	    
	    ArrayList<Route> routes = new ArrayList<Route>(3);
	    routes.add(startRoute);
	    routes.add(endRoute);
	    routes.add(highwayRoute);
	    
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
	
		junctions.add(new MapNode("m1","u"));
		junctions.add(new MapNode("m2","u"));
		junctions.add(new MapNode("m3","u"));
		junctions.add(new MapNode("m4","u"));
		junctions.add(new MapNode("m5","u"));
		junctions.add(new MapNode("m6","u"));
		junctions.add(new MapNode("m7","u"));

		return junctions;
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
