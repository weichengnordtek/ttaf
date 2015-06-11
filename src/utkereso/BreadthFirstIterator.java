package utkereso;
import java.util.*;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class BreadthFirstIterator implements Iterator<MyNode> {
    private Set<MyNode> visited = new HashSet<MyNode>();
    private Queue<MyNode> queue = new LinkedList<MyNode>();
    private UndirectedSparseGraph graph;

    public BreadthFirstIterator(UndirectedSparseGraph g, MyNode startingVertex) {
        this.graph = g;
        this.queue.add(startingVertex);
        this.visited.add(startingVertex);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
        return !this.queue.isEmpty();
    }
    
    public MyNode current() {
        MyNode current = queue.peek();
        return current;
    }

    @Override
    public MyNode next() {  
       MyNode next = queue.remove();
       Collection<MyNode> myCollection = this.graph.getNeighbors(next);
       List<MyNode> neighbor = new ArrayList<MyNode>(myCollection);
       for (int i = 0; i < neighbor.size(); i++) {
            if (!this.visited.contains(neighbor.get(i))) {
                this.queue.add(neighbor.get(i));
                this.visited.add(neighbor.get(i));
            }
       }
        return next;
    }

               
}