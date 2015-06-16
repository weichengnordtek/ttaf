package utkereso;

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
	
}
