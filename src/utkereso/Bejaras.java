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
 * @author Cheng
 */
//FLOYD MARSHAL ALGORITMUS 
// G: gráf , staringVertext: kezdőcsúcs, path: tranzittábla
public class Bejaras {
    private List<Road> shortestpath = new ArrayList<Road>(); //legrövidebb út
    private UndirectedSparseGraph<MapNode, Road> graph; // gráf
    private MapNode vegpont; // autópálya csomópont amit keresük
    private Double distance; // legrövidebb útnak a hossza
    
    public Bejaras(UndirectedSparseGraph<MapNode, Road> g, MapNode startingVertex, List<MapNode> path) {
        this.graph = g;
        
        Transformer<Road, Double> wtTransformer = new Transformer<Road,Double>() {
	        public Double transform(Road link) {
	            return link.weight;
	        }
        };
        DijkstraShortestPath<MapNode,Road> alg = new DijkstraShortestPath<MapNode, Road>(g,wtTransformer);
        double min = alg.getDistance(startingVertex, path.get(0)).doubleValue();
        this.vegpont = path.get(0);
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
    public MapNode getVegpont() {
        return vegpont;
    }
    public Double getDistance() {
        return distance;
    }
    public List<Road> getShortestPath() {
        return shortestpath;
    }
}
