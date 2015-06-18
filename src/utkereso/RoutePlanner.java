package utkereso;
/**
*
* @author Cheng
*/
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;


public class RoutePlanner {
	private RoadNetwork roadNetwork;
	private BasicVisualizationServer<MapNode, Road> visualizer;
	private JButton shortestRouteButton;
	private JButton highwayRouteButton;
	private JTextArea highwayRouteTextArea;
	private JTextArea shortestRouteTextArea;
	private Panel mapPanel;
	private StaticLayout<MapNode, Road> roadNetworkLayout;
	
	public void setRoadNetwork(RoadNetwork network) {
		this.roadNetwork = network;
	}
	
	public Point2D.Double getNodeCoordinates(MapNode node) {
		return new Point2D.Double( roadNetworkLayout.getX(node), roadNetworkLayout.getY(node) );
	}
	
	public MapNode getNodeAtCoordinates(Integer x, Integer y) {
		Point2D.Double clickPoint= new Point2D.Double( x, y );
		for (MapNode city: roadNetwork.getCities()) {
			Point2D.Double nodePoint = new Point2D.Double( roadNetworkLayout.getX(city), roadNetworkLayout.getY(city) );
			if ( nodePoint.distance(clickPoint) < 60.0 ) {
				return city;
			}
		}
		
		for (MapNode junction: roadNetwork.getJunctions()) {
			Point2D.Double nodePoint = new Point2D.Double( roadNetworkLayout.getX(junction), roadNetworkLayout.getY(junction) );
			if ( nodePoint.distance(clickPoint) < 60.0 ) {
				return junction;
			}
		}
		
		return null;
	}
	
	public void displayRoutes(Route shortestRoute, List<Route> highwayRoute, String city1, String city2) {
		List<Road> path = shortestRoute.getPath();
		Double length = shortestRoute.getLength();
	    
	    String shortestRouteText =  "The shortest path from " + city1 + " to " + city2 + " is:\n" +
	    							path.toString() + "\n" +
	    							"and the length of the path is: \n" +
	    							length;
	    shortestRouteTextArea.setText(shortestRouteText);

	    
	    double highwayDistance = highwayRoute.get(0).getLength().doubleValue()+highwayRoute.get(1).getLength().doubleValue()+highwayRoute.get(2).getLength().doubleValue();
		String highwayRouteText = "The shortest path from " + city1 + " to " + city2 + "\n" +
									"using highways is:\n" +
									highwayRoute.get(0).toString()+highwayRoute.get(1).toString()+highwayRoute.get(2).toString() + "\n" +
									"and the length of the path is: \n" +
									highwayDistance;
	    
	    highwayRouteTextArea.setText(highwayRouteText);
	}
	
	public void clearRouteTextareas() {
		shortestRouteTextArea.setText("Select Two Cities");
	    highwayRouteTextArea.setText("Select Two Cities");
	}

	public void renderMapWithoutRoute() {
		Transformer<Road, Paint> colorTransformer = new Transformer<Road, Paint>(){

			@Override
			public Paint transform(Road road) {
				return Color.BLACK;
			}};
			
	    visualizer.getRenderContext().setEdgeDrawPaintTransformer(colorTransformer);
	    visualizer.repaint();
	}

	public void renderShortestRoute(Route shortestRoute) {
		List<Road> path = shortestRoute.getPath();
		Transformer<Road, Paint> simpleRouteTransformer = new SimpleRoadToColorTransformer(path);
	    visualizer.getRenderContext().setEdgeDrawPaintTransformer(simpleRouteTransformer);
	    visualizer.repaint();
	}
	
	public void renderHighwayRoute(List<Route> highwayRoute) {
	    Transformer<Road, Paint> highwayRouteTransformer = new HighwayRouteToColorTransformer(highwayRoute.get(0).getPath(), highwayRoute.get(2).getPath(), highwayRoute.get(1).getPath());
	    visualizer.getRenderContext().setEdgeDrawPaintTransformer(highwayRouteTransformer);
	    visualizer.repaint();
	}

	public void show() {
		roadNetworkLayout = new StaticLayout<MapNode, Road>(roadNetwork.getNetworkGraph());
	
	    roadNetworkLayout.setSize(new Dimension(900,500));
	    visualizer = getVisualizer(roadNetworkLayout);
	
	    //Cities's locations
	    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Budapest"), new Point2D.Double(600.0, 150.0));
	    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Berlin"), new Point2D.Double(150.0,50.0));	
	    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Bukarest"), new Point2D.Double(800.0,550.0));
	    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Belgrad"), new Point2D.Double(600.0,550.0));
	    roadNetworkLayout.setLocation(roadNetwork.getCityByName("Szeged"), new Point2D.Double(250.0,550.0));
	    
	    //Road locations
	    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m1"), new Point2D.Double(200.0,300.0));
	    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m3"), new Point2D.Double(430.0,120.0));
	    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m4"), new Point2D.Double(430.0,420.0));
	    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m2"), new Point2D.Double(630.0,370.0));
	    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m5"), new Point2D.Double(830.0,370.0));
	    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m6"), new Point2D.Double(830.0,170.0));
	    roadNetworkLayout.setLocation(roadNetwork.getJunctionByName("m7"), new Point2D.Double(700.0,250.0));
	    
	    
	    Panel shortestRoutePanel = createShortestRoutePanel("Select two cities");
	    Panel highwayRoutePanel = createHighwayRoutePanel("Select two cities");
	    Panel routeDescriptionsPanel = new Panel(new BorderLayout());
	    routeDescriptionsPanel.add(shortestRoutePanel, BorderLayout.NORTH);
	    routeDescriptionsPanel.add(highwayRoutePanel, BorderLayout.SOUTH);
	    mapPanel = new Panel();
	    mapPanel.add(visualizer);
	    
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    BorderLayout borderLayout = new BorderLayout();
	    borderLayout.setHgap(10);
	    borderLayout.setVgap(10);
	    frame.setLayout(borderLayout);
	    frame.add(mapPanel, BorderLayout.WEST);
	    frame.add(routeDescriptionsPanel, BorderLayout.EAST);
	    frame.pack();
	    frame.setVisible(true);     
	}

	private Panel createShortestRoutePanel(String routeText) {
		shortestRouteTextArea = new JTextArea(routeText);
	    shortestRouteTextArea.setEditable(false);
	    shortestRouteTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
	    shortestRouteTextArea.setPreferredSize(new Dimension(450,250));
	    
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
	    highwayRouteTextArea.setPreferredSize(new Dimension(450,250));
	    
	    highwayRouteButton = new JButton("Show");
	    highwayRouteButton.setName("HighwayRouteButton");
	
		Panel routePanel = new Panel();
		routePanel.setLayout(new BorderLayout());
		routePanel.add(highwayRouteTextArea, BorderLayout.NORTH);
		routePanel.add(highwayRouteButton, BorderLayout.SOUTH);
		return routePanel;
	}

	private BasicVisualizationServer<MapNode, Road> getVisualizer(Layout<MapNode, Road> layout) {
		BasicVisualizationServer<MapNode,Road> visualizer = new BasicVisualizationServer<MapNode,Road>(layout);
	    visualizer.setPreferredSize(new Dimension(900,600));       
	    Transformer<MapNode,Paint> vertexPaint = new Transformer<MapNode,Paint>() {
	        public Paint transform(MapNode mapNode) {
	            if(mapNode.getType().equals("u"))
	            {
	                System.setProperty("libafos", "0Xd4edfc");
	                return Color.getColor("libafos");
	            }
	            else if(mapNode.getType().equals("h"))
	            {
	                return Color.GREEN;
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
	    visualizer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MapNode>());
	    visualizer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); 
		return visualizer;
	}
	
	public void addController(Controller controller) {
	    shortestRouteButton.addActionListener(controller);
	    highwayRouteButton.addActionListener(controller);
		mapPanel.addMouseListener(controller);
	}
}