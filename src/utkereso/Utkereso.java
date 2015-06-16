package utkereso;
/**
*
* @author Cheng
*/
import java.util.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
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
public class Utkereso {
  private static RoadNetwork roadNetwork;

public static void main(String[] args) {
    roadNetwork = new RoadNetwork();
    
    String city1 = "Bukarest";
    String city2 = "Berlin";
    
    Route shortestRoute = roadNetwork.findShortestRoute(city1, city2);
    List<Road> mod1 = shortestRoute.getPath();
    Number dist = shortestRoute.getLength();
    
    //Kiiratas
    System.out.println("-----Módszer 1-----");
    System.out.println("The shortest path from " + city1 + " to " + city2 + " is:");
    System.out.println(mod1.toString());
    System.out.println("and the length of the path is: " );
    System.out.println(dist);
    
    List<Route> highwayRoute = roadNetwork.findShortestRouteUsingHighway(city1, city2);
    
    //Kiiratas
    System.out.println("-----Módszer 2-----");
    System.out.println("The shortest path from " + "Bukarest" + " to " + "Berlin" + " is:");
    System.out.println(highwayRoute.get(0).toString()+highwayRoute.get(1).toString()+highwayRoute.get(2).toString());
    System.out.println("and the length of the path is: ");
    System.out.println(highwayRoute.get(0).getLength().doubleValue()+highwayRoute.get(1).getLength().doubleValue()+highwayRoute.get(2).getLength().doubleValue());
 
    
    //MEGJELENÍTÉS
    Layout<MapNode, Road> layout = new ISOMLayout<MapNode, Road>(roadNetwork.getNetworkGraph());
    layout.setSize(new Dimension(1000,500));
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
            Rectangle2D rect = new Rectangle2D.Double(-65,-30,65,30);
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
    //LEGRÖVIDEBB ÚT szinezés
    //Módszer 1 LEGRÖVIDEBB ÚT SZINEZÉS (teljes út)
    Transformer<Road, Paint> short1Paint = new SimpleRoadToColorTransformer(mod1);
    //vv.getRenderContext().setEdgeDrawPaintTransformer(short1Paint);
    //Módszer 2 tranzittáblával legrövidebb út szinezés
    Transformer<Road, Paint> short2Paint = new HighwayRouteToColorTransformer(highwayRoute.get(0).getPath(), highwayRoute.get(1).getPath(), highwayRoute.get(2).getPath());
    visualizer.getRenderContext().setEdgeDrawPaintTransformer(short2Paint);
    
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(visualizer);
    frame.pack();
    frame.setVisible(true);     
  }
}