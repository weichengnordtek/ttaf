package utkereso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

public class Controller implements ActionListener, MouseListener {

	private Router view;
	private RoadNetwork model;
	private LinkedList<MapNode> selectedNodes = new LinkedList<MapNode>();
	private Route shortestRoute;
	private List<Route> highwayRoute;

	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent source = (JComponent)e.getSource();
		String startCityName = selectedNodes.get(0).getName();
		String endCityName = selectedNodes.get(1).getName();
		if (source.getName().equalsIgnoreCase("ShortestRouteButton")) {
			view.renderShortestRoute(shortestRoute);
		}
		else if (source.getName().equalsIgnoreCase("HighwayRouteButton")) {
			view.renderHighwayRoute(highwayRoute);
		}
	}
	
	public void addView(Router v){
		this.view = v;
		view.setRoadNetwork(model);
	}
	
	public void addModel(RoadNetwork m){
		this.model = m;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MapNode clickedNode = view.getNodeAtCoordinates(e.getX(), e.getY());
		System.out.println("Node at pos: " + e.getX() + ", " + e.getY() + ": " + clickedNode);
		
		if (clickedNode != null && clickedNode.type == "v") {
			if (selectedNodes.size() < 1) {
				selectedNodes.add(clickedNode);
			}
			else if (selectedNodes.size() == 1) {
				selectedNodes.add(clickedNode);
				String startCityName = selectedNodes.get(0).getName();
				String endCityName = clickedNode.getName();
				shortestRoute = model.findShortestRoute(startCityName, endCityName);
				highwayRoute = model.findHighwayRoute(startCityName, endCityName);
				view.displayRoutes(shortestRoute, highwayRoute, startCityName, endCityName);
			}
			else if (selectedNodes.size() == 2) {
				selectedNodes.clear();
				selectedNodes.add(clickedNode);
				view.clearRouteTextareas();
				view.renderMapWithoutRoute();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

}
