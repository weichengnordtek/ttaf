package utkereso;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Route {

	private List<Road> path;
	private Double length;

	public Route(List<Road> path, Double length) {
		this.path = path;
		this.length = length;
	}
	
	public String toString() {
		return path.toString();
	}
	
	public List<Road> getPath() {
		return path;
	}
	
	public Double getLength() {
		return length;
	}
	
	public Route reverse() {
		List<Road> reversedRoadList = (List<Road>) ((LinkedList<Road>) path).clone();
		Collections.reverse(reversedRoadList);
		return new Route(reversedRoadList, length);
	}
	
}
