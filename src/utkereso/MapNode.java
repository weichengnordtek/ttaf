package utkereso;
/**
*
* @author Cheng
*/
public class MapNode {
    String name;
    String type;
    
    public MapNode(String name,String type) {
        this.name = name;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public String toString() {
         return name;
    }
    public String getType() {
         return type;
    }
}
