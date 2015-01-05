/*
 * Class for representing graph objects
 *
 * @author Nikhilesh pandey
 */

import java.util.ArrayList;

public class Graph {
    
    ArrayList<Node> nodes;
    ArrayList<Edge> graphEdges;
    
    public Graph(int V, int deg) {
        graphEdges = new ArrayList<Edge>();
        this.nodes = new ArrayList<Node>(V);
        for(int i = 0; i < V; i++){
            nodes.add(i, new Node(i,deg));
        }
    }
    
    public void addEdge(int u, int v, int w){
        nodes.get(u).edges.add(new Edge(u, v, w));
        nodes.get(v).edges.add(new Edge(v, u, w));
        graphEdges.add(new Edge(u, v, w));
    }
    
}
