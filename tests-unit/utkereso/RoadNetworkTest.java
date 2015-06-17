package utkereso;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RoadNetworkTest {

	private static RoadNetwork roadNetwork;
	
	@Before
	public void setUp() throws Exception {
		roadNetwork = new RoadNetwork();
	}

	@Test
	public void testEmptyRoute() {	
		Route emptyRoute = roadNetwork.findShortestRoute("Budapest", "Budapest");

		assertEquals(new Double(0.0), emptyRoute.getLength());
		assertEquals(new LinkedList<Road>(), emptyRoute.getPath());
	}

	@Test
	public void testFindShortestRouteNeighbouringCities() {	
		Route route = roadNetwork.findShortestRoute("Belgrad", "Bukarest");
		
		assertEquals(1, route.getPath().size());
	}

	@Test
	public void testEmptyHighwayRoute() {
		List<Route> route = roadNetwork.findHighwayRoute("Budapest", "Budapest");

		assertEquals(new Double(0.0), route.get(1).getLength());
		assertEquals(new LinkedList<Road>(), route.get(1).getPath());
		
		assertEquals(route.get(0).reverse().getPath(), route.get(2).getPath());
	}

}
