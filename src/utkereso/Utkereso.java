/**
 *
 * @author Meev
 */
package utkereso;
import java.util.*;
import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import org.apache.commons.collections15.Transformer;

public class Utkereso {

    
  public static void main(String[] args) {
      
    UndirectedSparseGraph g = new UndirectedSparseGraph();
    //Város csomópontok
    MyNode v1 = new MyNode("Bukarest","v"); 
    MyNode v2 = new MyNode("Budapest","v");
    MyNode v3 = new MyNode("Berlin","v");
    MyNode v4 = new MyNode("Megallo1","v");
    MyNode v5 = new MyNode("Megallo1","v");
    //Autópálya csomópontok
    MyNode u1 = new MyNode("m1","u");
    MyNode u2 = new MyNode("m2","u");
    MyNode u3 = new MyNode("m3","u");
    MyNode u4 = new MyNode("m4","u");
    MyNode u5 = new MyNode("m5","u");
    MyNode u6 = new MyNode("m6","u");
    MyNode u7 = new MyNode("m7","u");
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
    
    g.addEdge(e12, v4,v5);
    g.addEdge(e13, v5,u1);
    //Algoritmusok 

    //Módszer 2:  Teljes útvonal heurisztikus 
    Transformer<MyLink, Double> wtTransformer = new Transformer<MyLink,Double>() {
        public Double transform(MyLink link) {
        return link.weight;
        }
    };
    DijkstraShortestPath<MyNode,MyLink> alg = new DijkstraShortestPath(g,
    wtTransformer);
    List<MyLink> l = alg.getPath(v1, v2);
    Number dist = alg.getDistance(v1, v2);
      System.out.println("-----Módszer 2-----");
    System.out.println("The shortest path from " + v1 + " to " + v2 + " is:");
    System.out.println(l.toString());
    System.out.println("and the length of the path is: " + dist);
    
    //Módszer 1: heurisztikus algoritmussal a megadott kezdő és végpontokat 
    //a tranzit tábla legközelebbi pontjához navigálja, majd onnantól az előzetesen eltárolt útvonalat adja vissza.
    //BFS Implementáció
    BreadthFirstIterator I = new BreadthFirstIterator(g, v1);
    Queue<MyNode> lista = new LinkedList<MyNode>();
    int db =0;   //Ez lehet h nem kell
    boolean lb = true;
    MyNode last = null;
    do
    {
        
       MyNode temp=I.next();
       lista.add(temp);
       if(temp.getType()=="v")
       {
           db++;
       }
       else
       {
           lb = false;
       }
       last=temp;
    }while(I.hasNext()&&lb);
    System.out.println("-----Módszer 1-----");
    //Start és Autópálya közötti legközelebbi útvonal és távolság
    List<MyLink> k = alg.getPath(v1, last);
    Number dist1 = alg.getDistance(v1, last);
    System.out.println("The shortest path from " + v1 + " to " + last + " is:");
    System.out.println(k.toString());
    System.out.println("and the length of the path is: " + dist1);
    BreadthFirstIterator I1 = new BreadthFirstIterator(g, v2);
    Queue<MyNode> lista1 = new LinkedList<MyNode>();
    int db1 =0;
    boolean lb1 = true;
    MyNode last2 = null;
    do
    {
       MyNode temp1=I1.next();
       lista1.add(temp1);
       if(temp1.getType()=="v")
       {
           db1++;
       }
       else
       {
           lb1 = false;
       }
       last2=temp1;
    }while(I1.hasNext()&&lb1);
    List<MyLink> k2 = alg.getPath(v2, last2);
    Number dist2 = alg.getDistance(v2, last2);
    System.out.println("The shortest path from " + v2 + " to " + last2 + " is:");
    System.out.println(k2.toString());
    System.out.println("and the length of the path is: " + dist2);
    
    //két autópályacsomópont közötti legközelebbi útvonal és távolság
    List<MyLink> k3 = alg.getPath(last, last2);
    Number dist3 = alg.getDistance(last, last2);
    System.out.println("The shortest path from " + last + " to " + last2 + " is:");
    System.out.println(k3.toString());
    System.out.println("and the length of the path is: " + dist3);
    System.out.println("full length of the path is: ");
    System.out.println(dist1.doubleValue()+dist3.doubleValue()+dist2.doubleValue());
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
    
    //Összehasonlítás
    
    //test
    /*
       Collection<MyNode> myCollection = g.getNeighbors(u1);
       List<MyNode> neighbor = new ArrayList<MyNode>(myCollection);
       
     System.out.println(neighbor.get(1).getType());
    */
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