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
  public static void main(String[] args) {
    UndirectedSparseGraph g = new UndirectedSparseGraph();
    //Város csomópontok
    MyNode v1 = new MyNode("Bukarest","v"); 
    MyNode v2 = new MyNode("Budapest","v");
    MyNode v3 = new MyNode("Berlin","v");
    MyNode v4 = new MyNode("Megallo1","v");
    MyNode v5 = new MyNode("Megallo2","v");
    //Autópálya csomópontok
    MyNode u1 = new MyNode("m1","u");
    MyNode u2 = new MyNode("m2","u");
    MyNode u3 = new MyNode("m3","u");
    MyNode u4 = new MyNode("m4","u");
    MyNode u5 = new MyNode("m5","u");
    MyNode u6 = new MyNode("m6","u");
    MyNode u7 = new MyNode("m7","u");
    List<MyNode> Tranzit = new ArrayList<MyNode>();
    Tranzit.add(u1);
    Tranzit.add(u2);
    Tranzit.add(u3);
    Tranzit.add(u4);
    Tranzit.add(u5);
    Tranzit.add(u6);
    Tranzit.add(u7);
   //Élek, útvonalak
    MyLink e1= new MyLink("ut1",3);
    MyLink e2= new MyLink("ut2",2);
    MyLink e3=new MyLink("ut3",1);
    MyLink e4=new MyLink("ut4",5);
    MyLink e5=new MyLink("ut5",6);
    MyLink e6=new MyLink("ut6",7);
    MyLink e7=new MyLink("ut7",8);
    MyLink e8=new MyLink("ut8",9);
    MyLink e9=new MyLink("ut9",1);
    MyLink e10=new MyLink("ut10",2);
    MyLink e11=new MyLink("ut11",2);
    MyLink e12=new MyLink("ut12",2);
    MyLink e13=new MyLink("ut13",1);
    MyLink e14=new MyLink("ut14",1);
    
    g.addEdge(e1, v1, v4);
    g.addEdge(e2, u1, u2);
    g.addEdge(e3, u4, u1);
    g.addEdge(e4, u5, u2);
    g.addEdge(e5, u3, u2);
    g.addEdge(e6, u1, u3);
    g.addEdge(e7, u2, u4);
    g.addEdge(e8, u5, v3);
    g.addEdge(e9, u5, u6);
    g.addEdge(e10, u6, u7);
    g.addEdge(e11, u7, v2);
    g.addEdge(e14, u6, v2);
    g.addEdge(e12, v4,v5);
    g.addEdge(e13, v5,u1);

    //Módszer 1:  Teljes útvonal heurisztikus 
    Transformer<MyLink, Double> wtTransformer = new Transformer<MyLink,Double>() {
        public Double transform(MyLink link) {
        return link.weight;
        }
    };
    DijkstraShortestPath<MyNode,MyLink> alg = new DijkstraShortestPath(g,
    wtTransformer);
    final List<MyLink> mod1 = alg.getPath(v1, v3);
    Number dist = alg.getDistance(v1, v3);
    
    //Kiiratas
    System.out.println("-----Módszer 1-----");
    System.out.println("The shortest path from" + v1 + " to " + v3 + " is:");
    System.out.println(mod1.toString());
    System.out.println("and the length of the path is: " + dist);
    
    //Módszer 2 
    //Startbol -> legközelebbi autopalya 
    Bejaras Floyd =  new Bejaras(g,v1,Tranzit);
    final List<MyLink> mod2_a=Floyd.getShortestPath();
    System.out.println(Floyd.getShortestPath());
    System.out.println(Floyd.getDistance());
    System.out.println(Floyd.getVegpont());
    
    //Vegpontbol -> legközelebbi autopalya
    Bejaras Floyd2 =  new Bejaras(g,v3,Tranzit);
    final List<MyLink> mod2_c=Floyd2.getShortestPath();
    System.out.println(Floyd2.getShortestPath());
    System.out.println(Floyd2.getDistance());
    System.out.println(Floyd2.getVegpont());
    
    //Autopalya közötti legrövidebb út
    final List<MyLink> Floyd3 = alg.getPath(Floyd.getVegpont(), Floyd2.getVegpont());
    Number dist3 = alg.getDistance(Floyd.getVegpont(), Floyd2.getVegpont());
    
    //Kiiratas
    System.out.println("-----Módszer 1-----");
    System.out.println("The shortest path from" + v1 + " to " + v3 + " is:");
    System.out.println(Floyd.getShortestPath()+Floyd3.toString()+Floyd2.getShortestPath());
    System.out.println("Total Length" + v1 + " to " + v3 + " is:");
    System.out.println(Floyd.getDistance()+dist3.doubleValue()+Floyd2.getDistance());
 
    
    //MEGJELENÍTÉS
    Layout<MyNode, MyLink> layout = new ISOMLayout(g);
    layout.setSize(new Dimension(1000,500));
    BasicVisualizationServer<MyNode,MyLink> vv = new BasicVisualizationServer<MyNode,MyLink>(layout);
    vv.setPreferredSize(new Dimension(1200,600));       
    Transformer<MyNode,Paint> vertexPaint = new Transformer<MyNode,Paint>() {
        public Paint transform(MyNode i) {
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
   Transformer<MyNode,Shape> vertexSize = new Transformer<MyNode,Shape>(){
        public Shape transform(MyNode i){
            Rectangle2D rect = new Rectangle2D.Double(-65,-30,65,30);
            return rect;
        }
    };
    Transformer<MyLink, Paint> edgePaint = new Transformer<MyLink, Paint>() {
        public Paint transform(MyLink s) {
            return Color.BLACK;
        }
    };
    Transformer<MyLink, Stroke> edgeStroke = new Transformer<MyLink, Stroke>() {
        float dash[] = { 5.0f };
        public Stroke transform(MyLink s) {
            return new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        }
    };
    Transformer<MyLink, Font> edgeFont = new Transformer<MyLink, Font>() {
        public Font transform(MyLink f) {
                Font font = new Font("Times New Roman", Font.PLAIN, 8);
                return font;
        }
    };
     Transformer<MyLink, String> edgeLabel = new Transformer<MyLink, String>() {
        public String transform(MyLink f) {
                return f.toString()+" :"+String.valueOf(f.weight);
        }
    };
    vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
    vv.getRenderContext().setVertexShapeTransformer(vertexSize);
    vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
    vv.getRenderContext().setEdgeLabelTransformer(edgeLabel);
    vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
    vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);        
    //LEGRÖVIDEBB ÚT szinezés
    //Módszer 1 LEGRÖVIDEBB ÚT SZINEZÉS (teljes út)
    Transformer<MyLink, Paint> short1Paint = new Transformer<MyLink, Paint>() {
        public Paint transform(MyLink s) {
            if(mod1.contains(s))
            {
                return Color.RED;
            }
            else
            {
                return Color.BLACK;
            }
        }
    };
    //vv.getRenderContext().setEdgeDrawPaintTransformer(short1Paint);
    //Módszer 2 tranzittáblával legrövidebb út szinezés
    Transformer<MyLink, Paint> short2Paint = new Transformer<MyLink, Paint>() {
        public Paint transform(MyLink s) {
            System.setProperty("zold", "0X96d72d");
            System.setProperty("sarga", "0Xffd700");
            if(mod2_a.contains(s))
            {
                return Color.getColor("zold");
            }
            else if(mod2_c.contains(s))
            {
                return Color.getColor("zold");
            }
            else if(Floyd3.contains(s))
            {
                return Color.RED;
            }
            else
            {
                return Color.BLACK;
            }
        }
    };
    vv.getRenderContext().setEdgeDrawPaintTransformer(short2Paint);
    
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(vv);
    frame.pack();
    frame.setVisible(true);     
  }
}