package utkereso;
/**
*
* @author Cheng
*/
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.util.VertexShapeFactory;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import org.apache.commons.collections15.Transformer;
public class Utkereso implements java.util.Observer {
  private RoadNetwork roadNetwork;
private BasicVisualizationServer<MapNode, Road> visualizer;
private JButton shortestRouteButton;
private JButton highwayRouteButton;
private JTextArea highwayRouteTextArea;
private JTextArea shortestRouteTextArea;

public void setRoadNetwork(RoadNetwork network) {
	this.roadNetwork = new RoadNetwork();
//    String city1 = "Bukarest";
//    String city2 = "Berlin";
//    
//    Route shortestRoute = roadNetwork.findShortestRoute(city1, city2);
//    List<Road> mod1 = shortestRoute.getPath();
//    Number dist = shortestRoute.getLength();
//    
//    List<Route> highwayRoute = roadNetwork.findShortestRouteUsingHighway(city1, city2);
//    
//    String shortestRouteText =	"-----Módszer 1-----\n" +
//    							"The shortest path from " + city1 + " to " + city2 + " is:\n" +
//    							mod1.toString() + "\n" +
//    							"and the length of the path is: \n" +
//    							dist;
//    
//    String highwayRouteText =	"-----Módszer 2-----\n" +
//    							"The shortest path from " + city1 + " to " + city2 + " is:\n" +
//    							highwayRoute.get(0).toString()+highwayRoute.get(1).toString()+highwayRoute.get(2).toString() + "\n" +
//    							"and the length of the path is: \n" +
//    							highwayRoute.get(0).getLength().doubleValue()+highwayRoute.get(1).getLength().doubleValue()+highwayRoute.get(2).getLength().doubleValue();	
}

public void show() {

	
	//Layout<MapNode, Road> roadNetworkLayout = new ISOMLayout<MapNode, Road>(roadNetwork.getNetworkGraph());
	Layout<MapNode, Road> roadNetworkLayout = new StaticLayout<MapNode, Road>(roadNetwork.getNetworkGraph());

    roadNetworkLayout.setSize(new Dimension(1000,500));
    visualizer = getVisualizer(roadNetworkLayout);

    //Cities's locations
    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Budapest"), new Point2D.Double(600.0, 150.0));
    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Berlin"), new Point2D.Double(150.0,50.0));	
    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Bukarest"), new Point2D.Double(1000.0,550.0));
    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Megallo1"), new Point2D.Double(750.0,550.0));
    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Megallo2"), new Point2D.Double(450.0,550.0));
    
    //Road locations
    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m1"), new Point2D.Double(250.0,370.0));
    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m3"), new Point2D.Double(430.0,120.0));
    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m4"), new Point2D.Double(430.0,420.0));
    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m2"), new Point2D.Double(630.0,370.0));
    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m5"), new Point2D.Double(930.0,370.0));
    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m6"), new Point2D.Double(930.0,170.0));
    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m7"), new Point2D.Double(750.0,200.0));
    
    
    Panel shortestRoutePanel = createShortestRoutePanel("shortestRouteText");
    Panel highwayRoutePanel = createHighwayRoutePanel("highwayRouteText");
    
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    BorderLayout borderLayout = new BorderLayout();
    borderLayout.setHgap(10);
    borderLayout.setVgap(10);
    frame.setLayout(borderLayout);
    frame.add(visualizer, BorderLayout.NORTH);
    frame.add(shortestRoutePanel, BorderLayout.WEST);
    frame.add(highwayRoutePanel, BorderLayout.EAST);
    frame.pack();
    frame.setVisible(true);     
  }

private Panel createShortestRoutePanel(String routeText) {
	shortestRouteTextArea = new JTextArea(routeText);
    shortestRouteTextArea.setEditable(false);
    shortestRouteTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
    
    shortestRouteButton = new JButton("Show");
    shortestRouteButton.setName("ShortestRouteButton");

	Panel routePanel = new Panel();
	routePanel.setLayout(new BorderLayout());
	routePanel.add(shortestRouteTextArea, BorderLayout.NORTH);
	routePanel.add(shortestRouteButton, BorderLayout.SOUTH);
	return routePanel;
}

private Panel createHighwayRoutePanel(String routeText) {
	highwayRouteTextArea = new JTextArea(routeText);
    highwayRouteTextArea.setEditable(false);
    highwayRouteTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
    
    highwayRouteButton = new JButton("Show");
    highwayRouteButton.setName("HighwayRouteButton");

	Panel routePanel = new Panel();
	routePanel.setLayout(new BorderLayout());
	routePanel.add(highwayRouteTextArea, BorderLayout.NORTH);
	routePanel.add(highwayRouteButton, BorderLayout.SOUTH);
	return routePanel;
}

	public void renderShortestRoute(String city1, String city2) {
		Route shortestRoute = roadNetwork.findShortestRoute("Bukarest", "Berlin");
		List<Road> path = shortestRoute.getPath();
		Double length = shortestRoute.getLength();
		Transformer<Road, Paint> short1Paint = new SimpleRoadToColorTransformer(path);
	    visualizer.getRenderContext().setEdgeDrawPaintTransformer(short1Paint);
	    visualizer.repaint();
	    
	    String shortestRouteText =	"-----Módszer 1-----\n" +
	    							"The shortest path from " + city1 + " to " + city2 + " is:\n" +
	    							path.toString() + "\n" +
	    							"and the length of the path is: \n" +
	    							length;
	    shortestRouteTextArea.setText(shortestRouteText);
	}
	
	public void renderHighwayRoute(String city1, String city2) {
		List<Route> routeList = roadNetwork.findHighwayRoute(city1, city2);
	    Transformer<Road, Paint> short2Paint = new HighwayRouteToColorTransformer(routeList.get(0).getPath(), routeList.get(1).getPath(), routeList.get(2).getPath());
	    visualizer.getRenderContext().setEdgeDrawPaintTransformer(short2Paint);
	    visualizer.repaint();
	    
	    String highwayRouteText =	"-----Módszer 2-----\n" +
		"The shortest path from " + city1 + " to " + city2 + " is:\n" +
		routeList.get(0).toString()+routeList.get(1).toString()+routeList.get(2).toString() + "\n" +
		"and the length of the path is: \n" +
		routeList.get(0).getLength().doubleValue()+routeList.get(1).getLength().doubleValue()+routeList.get(2).getLength().doubleValue();	
	    
	    highwayRouteTextArea.setText(highwayRouteText);

	}

	private BasicVisualizationServer<MapNode, Road> getVisualizer(Layout<MapNode, Road> layout) {
		BasicVisualizationServer<MapNode,Road> visualizer = new BasicVisualizationServer<MapNode,Road>(layout);
	    visualizer.setPreferredSize(new Dimension(1200,600));       
	    Transformer<MapNode,Paint> vertexPaint = new Transformer<MapNode,Paint>() {
	        public Paint transform(MapNode i) {
	            if(i.getType()=="u")
	            {
	                System.setProperty("libafos", "0Xd4edfc");
	                return Color.getColor("libafos");
	            }
	            else
	            {
	                System.setProperty("rozsaszin", "0Xef3298");
	                return Color.getColor("rozsaszin");
	            }
	        }
	    };  
	   Transformer<MapNode,Shape> vertexSize = new Transformer<MapNode,Shape>(){
	        public Shape transform(MapNode i){
	            //Rectangle2D rect = new Rectangle2D.Double(-65,-30,65,30);
	            Ellipse2D rect = new Ellipse2D.Double(-35, -35, 60, 60);
	            return rect;
	        }
	    };
	    Transformer<Road, Paint> edgePaint = new Transformer<Road, Paint>() {
	        public Paint transform(Road s) {
	            return Color.BLACK;
	        }
	    };
	    Transformer<Road, Stroke> edgeStroke = new Transformer<Road, Stroke>() {
	        float dash[] = { 5.0f };
	        public Stroke transform(Road s) {
	            return new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
	                    BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
	        }
	    };
	    Transformer<Road, Font> edgeFont = new Transformer<Road, Font>() {
	        public Font transform(Road f) {
	                Font font = new Font("Times New Roman", Font.PLAIN, 8);
	                return font;
	        }
	    };
	     Transformer<Road, String> edgeLabel = new Transformer<Road, String>() {
	        public String transform(Road f) {
	                return f.toString()+" :"+String.valueOf(f.weight);
	        }
	    };
	    
	    visualizer.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
	    visualizer.getRenderContext().setVertexShapeTransformer(vertexSize);
	    visualizer.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
	    visualizer.getRenderContext().setEdgeLabelTransformer(edgeLabel);
	    visualizer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	    visualizer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); 
		return visualizer;
	}

	@Override
	public void update(Observable observable, Object object) {
		System.out.println ("View      : Observable is " + observable.getClass() + ", object passed is " + object.getClass());
		
	}
	
	public void addController(Controller controller) {
	    shortestRouteButton.addActionListener(controller);
	    highwayRouteButton.addActionListener(controller);
	}
}