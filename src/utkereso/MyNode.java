package utkereso;
/**
*
* @author Cheng
*/
public class MyNode {
    String name;
    String type;// good coding practice would have this as private
    public MyNode(String name,String type) {
        this.name = name;
        this.type = type;
    }
    public String toString() { // Always a good idea for debuging
         return name; // JUNG2 makes good use of these.
    }
    public String getType() { // Always a good idea for debuging
         return type; // JUNG2 makes good use of these.
    }
}
