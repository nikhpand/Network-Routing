/*
 * Class for representing node objects in a graph
 *
 * @author Nikhilesh pandey
 */

import java.util.ArrayList;

public class Node {
    int name;
    ArrayList<Edge> edges;
  
    public Node(int n, int deg){
        name = n;
        edges = new ArrayList<Edge>(deg);
    }   
    
}
