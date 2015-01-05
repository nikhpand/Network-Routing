/*
 * Class for representing edge objects in a graph
 *
 * @author Nikhilesh pandey
 */

public class Edge {
    
    int source;
    int dest;
    int weight;
    
    public Edge(int u, int v, int w){
        this.source = u;
        this.dest = v;
        this.weight = w;
    }
    
}
