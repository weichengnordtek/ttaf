/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * asd
 */
package utkereso;
import java.util.*;
import java.util.Arrays;
import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
/**
 *
 * @author Meev
 */
public class Bejaras {
    private List<MyLink> shortestpath = new ArrayList<MyLink>();
    private UndirectedSparseGraph graph;
    private MyNode vegpont;
    private Double distance;

    public Bejaras(UndirectedSparseGraph g, MyNode startingVertex, List<MyNode> path) {
        this.graph = g;
        
        Transformer<MyLink, Double> wtTransformer = new Transformer<MyLink,Double>() {
        public Double transform(MyLink link) {
            return link.weight;
        }
        };
        DijkstraShortestPath<MyNode,MyLink> alg = new DijkstraShortestPath(g,
        wtTransformer);
        MyNode pp= null;
        double min = alg.getDistance(startingVertex, path.get(0)).doubleValue();
        this.vegpont=path.get(0);
        for(int i = 1; i < path.size(); i++) {

            if(alg.getDistance(startingVertex, path.get(i)).doubleValue()<min)
            {
                min=alg.getDistance(startingVertex, path.get(i)).doubleValue();
                this.vegpont=path.get(i);
            }
       }
        
        this.shortestpath=alg.getPath(startingVertex, vegpont);
        this.distance=min;
    }
    public MyNode getVegpont() {
        return vegpont;
    }
    public Double getDistance() {
        return distance;
    }
    public List<MyLink> getShortestPath() {
        return shortestpath;
    }
}
