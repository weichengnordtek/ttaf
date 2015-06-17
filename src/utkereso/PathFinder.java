/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * asd
 */
package utkereso;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
/**
 *
 * @author Cheng
 */

public class PathFinder {
    private List<Road> shortestpath = new ArrayList<Road>();
    private MapNode endPoint;
    private Double distance;
    
    public PathFinder(UndirectedSparseGraph<MapNode, Road> graph, MapNode startingVertex, List<MapNode> potentialEndNodes) {
        Transformer<Road, Double> wtTransformer = new Transformer<Road,Double>() {
	        public Double transform(Road link) {
	            return link.weight;
	        }
        };
        DijkstraShortestPath<MapNode,Road> pathFinder = new DijkstraShortestPath<MapNode, Road>(graph,wtTransformer);
        double min = pathFinder.getDistance(startingVertex, potentialEndNodes.get(0)).doubleValue();
        this.endPoint = potentialEndNodes.get(0);
        for(int i = 1; i < potentialEndNodes.size(); i++) {
            if(pathFinder.getDistance(startingVertex, potentialEndNodes.get(i)).doubleValue() < min)
            {
                min = pathFinder.getDistance(startingVertex, potentialEndNodes.get(i)).doubleValue();
                this.endPoint = potentialEndNodes.get(i);
            }
        }
        
        this.shortestpath=pathFinder.getPath(startingVertex, endPoint);
        this.distance=min;
    }
    public MapNode getEndPoint() {
        return endPoint;
    }
    public Double getDistance() {
        return distance;
    }
    public List<Road> getShortestPath() {
        return shortestpath;
    }
}
