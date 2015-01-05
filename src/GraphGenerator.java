/*
 * This file has methods useful for graph generation
 *
 * @author Nikhilesh pandey
 */

import java.util.ArrayList;
import java.util.Random;

public class GraphGenerator {
    
    private static final int PER_STEP_LIMIT = 500;
       
    public static Graph generateGraph(int vertCount, int maxDegree) {
        Graph G = new Graph(vertCount, maxDegree + 1);
        int[] nodeDegree = new int[vertCount];
        int limit;
        for (Node n : G.nodes){
            limit = 0;
            while(nodeDegree[n.name] < maxDegree && limit < PER_STEP_LIMIT){
                Random rn = new Random();
                int tempD = rn.nextInt(vertCount);
                if(nodeDegree[tempD] < maxDegree && noEdgeExists(G, n.name, tempD) && n.name != tempD){
                    int wt = rn.nextInt(1000) + 1;
                    G.addEdge(n.name, tempD, wt);
                    nodeDegree[n.name]++;
                    nodeDegree[tempD]++;
                }
                limit ++;
            }
        }
        
        return G;
    }   
    
    public static Graph generateGraph(int vertCount, int maxDegree, ArrayList<Edge> maxSpanningTree) {
        Graph G = new Graph(vertCount, maxDegree + 1);
        
        for(Edge edge : maxSpanningTree){
            G.addEdge(edge.source, edge.dest, edge.weight);
        }
        
        return G;
    } 

/*
 * This method gurantees that a random graph generated becomes connected
 */    
    public static void connnectGraph(Graph G, int source, int dest, int vertCount){
        
        if(source == dest) return;
        
        Random rn = new Random();
        
        int  first = Math.min(source, dest);
        int second = Math.max(source, dest);
        
        for(int i = first; i > 0; i-- ){
            if(noEdgeExists(G, i, i-1)){
                int wt = rn.nextInt(1000) + 1;
                G.addEdge(i, i-1, wt);
            }
        }
        
            if(noEdgeExists(G, 0, first + 1)){
                int wt = rn.nextInt(1000) + 1;
                G.addEdge(0, first + 1, wt);
            }

        for(int i = first + 1; i < second - 1; i++ ){
            if(noEdgeExists(G, i, i+1)){
                int wt = rn.nextInt(1000) + 1;
                G.addEdge(i, i+1, wt);
            }
        }

            if(noEdgeExists(G, second - 1, vertCount - 1)){
                int wt = rn.nextInt(1000) + 1;
                G.addEdge(0, second - 1, vertCount - 1);
            }   
            
        for(int i = vertCount - 1; i > second; i-- ){
            if(noEdgeExists(G, i, i-1)){
                int wt = rn.nextInt(1000) + 1;
                G.addEdge(i, i-1, wt);
            }
        }            
        
    }

/*
 * Check if two nodes have an edge between them
 */ 
    public static boolean noEdgeExists(Graph G, int name, int tempD) {
        for(Edge edge : G.nodes.get(name).edges){
            if(edge.dest == tempD){
                return false;
            }                
        }
        return true;
    }
    
}
