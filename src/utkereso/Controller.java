package utkereso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

public class Controller implements ActionListener {

	private Utkereso view;
	private RoadNetwork model;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JComponent source = (JComponent)e.getSource();
		System.out.println ("actionPerformed " + source.getName());
		if (source.getName().equalsIgnoreCase("ShortestRouteButton")) {
			view.renderShortestRoute("Bukarest", "Berlin");
		}
		else if (source.getName().equalsIgnoreCase("HighwayRouteButton")) {
			view.renderHighwayRoute("Bukarest", "Berlin");
		}
		else if (source.getName().equalsIgnoreCase("HighwayRouteButton")) {
			view.renderHighwayRoute("Bukarest", "Berlin");
		}
	}
	
	public void addView(Utkereso v){
		this.view = v;
		view.setRoadNetwork(model);
	}
	
	public void addModel(RoadNetwork m){
		this.model = m;
	}

}
