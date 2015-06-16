package utkereso;

import java.util.List;

public class Route {

	private List<Road> path;
	private Number length;

	public Route(List<Road> path, Number length) {
		this.path = path;
		this.length = length;
	}
	
	public String toString() {
		return path.toString();
	}
	
	public List<Road> getPath() {
		return path;
	}
	
	public Number getLength() {
		return length;
	}
	
}
