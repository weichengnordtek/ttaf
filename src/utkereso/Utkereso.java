package utkereso;
import java.util.*;
import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.*;

public class Utkereso {
    
  public static void main(String[] args) {
      
      
      
    class MyNode {
        String name;
        String type;// good coding practice would have this as private
        public MyNode(String name,String type) {
            this.name = name;
            this.type = type;
        }
        public String toString() { // Always a good idea for debuging
             return "V: "+name; // JUNG2 makes good use of these.
        }
     }
     class MyLink {
        String name;;
        double weight; // should be private for good practice
        public MyLink(String name, double weight) {
           this.name = name;
           this.weight = weight;
        }
        public String toString() { // Always good for debugging
           return "E :"+name;
        }
     }
   
    UndirectedSparseGraph g = new UndirectedSparseGraph();
    
    //Város csomópontok
    MyNode v1 = new MyNode("Bukarest","v");
    MyNode v2 = new MyNode("Budapest","v");
    MyNode v3 = new MyNode("Berlin","v");
    
    //Autópálya csomópontok
    MyNode u1 = new MyNode("m1","u");
    MyNode u2 = new MyNode("m2","u");
    MyNode u3 = new MyNode("m3","u");
    MyNode u4 = new MyNode("m4","u");
    MyNode u5 = new MyNode("m5","u");
    MyNode u6 = new MyNode("m6","u");
    MyNode u7 = new MyNode("m7","u");
    
    //Útvonalak
    g.addEdge(new MyLink("ut1",3), v1, u1);
    g.addEdge(new MyLink("ut2",2), u1, u2);
    g.addEdge(new MyLink("ut3",1), u4, u1);
    g.addEdge(new MyLink("ut4",5), u5, u2);
    g.addEdge(new MyLink("ut5",6), u3, u2);
    g.addEdge(new MyLink("ut6",7), u1, u3);
    g.addEdge(new MyLink("ut7",8), u2, u4);
    g.addEdge(new MyLink("ut8",9), u5, v3);
    g.addEdge(new MyLink("ut9",1), u5, u6);
    g.addEdge(new MyLink("ut10",2), u6, u7);
    g.addEdge(new MyLink("ut11",2), u7, v2);
    //Algoritmusok 
    //legközelebbi autópálya csomópont
    
    //Módszer 1: heurisztikus algoritmussal a megadott kezdő és végpontokat 
    //a tranzit tábla legközelebbi pontjához navigálja, majd onnantól az előzetesen eltárolt útvonalat adja vissza.
    /*
       procedure BFS(G,v):
2      create a queue Q
3      enqueue v onto Q
4      mark v
5      while Q is not empty:
6          t ← Q.dequeue()
7          if t is what we are looking for:
8              return t
9          for all edges e in G.adjacentEdges(t) do
12             o ← G.adjacentVertex(t,e)
13             if o is not marked:
14                  mark o
15                  enqueue o onto Q
16      return null
    */
    
    //Módszer 2:  Teljes útvonal heurisztikus 
   
    /*
    DijkstraShortestPath<MyNode,MyLink> alg = new DijkstraShortestPath(g);
    List<MyLink> l = alg.getPath(v1, v2);
    System.out.println("The shortest unweighted path from" + v1 +
   " to " + v2 + " is:");
    System.out.println(l.toString());
    */
    Transformer<MyLink, Double> wtTransformer = new Transformer<MyLink,Double>() {
        public Double transform(MyLink link) {
        return link.weight;
        }
    };
    DijkstraShortestPath<MyNode,MyLink> alg = new DijkstraShortestPath(g,
    wtTransformer);
    List<MyLink> l = alg.getPath(v1, v2);
    Number dist = alg.getDistance(v1, v2);
    System.out.println("The shortest path from" + v1 + " to " + v2 + " is:");
    System.out.println(l.toString());
    System.out.println("and the length of the path is: " + dist);

    
    //Összehasonlítás
    
    
    

    //Megjelenítés
    VisualizationImageServer vs =
      new VisualizationImageServer(
        new CircleLayout(g), new Dimension(1200, 600));
    JFrame frame = new JFrame();
    frame.getContentPane().add(vs);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}