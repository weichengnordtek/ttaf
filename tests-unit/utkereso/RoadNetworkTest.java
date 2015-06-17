package utkereso;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoadNetworkTest {

	private static RoadNetwork roadNetwork;
	
	@Before
	public void setUp() throws Exception {
		roadNetwork = new RoadNetwork();
	}

	@Test
	public void testEmptyRoute() {	
		Route expectedEmptyRoute = new Route(new LinkedList<Road>(), 0.0);
		Double length = 0.0;
		Route emptyRoute = roadNetwork.findShortestRoute("Budapest", "Budapest");

		assertEquals(length, emptyRoute.getLength());
		assertEquals(expectedEmptyRoute.getPath(), emptyRoute.getPath());
	}

	@Test
	public void testEmptyHighwayRoute() {	
		Route expectedEmptyRoute = new Route(new LinkedList<Road>(), 0.0);
		Double length = 0.0;
		List<Route> emptyRoute = roadNetwork.findHighwayRoute("Budapest", "Budapest");

		assertEquals(length, emptyRoute.get(1).getLength());
		assertEquals(expectedEmptyRoute.getPath(), emptyRoute.get(1).getPath());
		
		assertEquals(emptyRoute.get(0).getPath(), emptyRoute.get(2).getPath());
	}

}
