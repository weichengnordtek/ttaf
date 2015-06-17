package utkereso;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class PathFinderTest {

	private RoadNetwork roadNetwork;

	@Before
	public void setUp() throws Exception {
		roadNetwork = new RoadNetwork();
	}

	@Test
	public void testPathFinder() {
		UndirectedSparseGraph<MapNode, Road> networkGraph = roadNetwork.getNetworkGraph();
		MapNode budapest = roadNetwork.getCityByName("Budapest");
		ArrayList<MapNode> highwayJunctions = roadNetwork.getHighwayJunctions();
		
		PathFinder pathFinder = new PathFinder(networkGraph, budapest, highwayJunctions);
		
		Route shortestRouteToEndpoint = roadNetwork.findShortestRoute("Budapest", pathFinder.getEndPoint().getName());
		assertEquals(shortestRouteToEndpoint.getLength(), pathFinder.getDistance());
		assertEquals(shortestRouteToEndpoint.getPath(), pathFinder.getShortestPath());
	}

}
