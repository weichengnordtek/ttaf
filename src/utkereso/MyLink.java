package utkereso;

public class MyLink {
    String name;
    double weight; // should be private for good practice
    public MyLink(String name, double weight) {
       this.name = name;
       this.weight = weight;
    }
    public String toString() { // Always good for debugging
       return "Edge :"+name;
    }
}
