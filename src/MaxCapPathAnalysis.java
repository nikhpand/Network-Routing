/*
 * This is the code for doing an analysis on the performance of below algorithms
 * for finding Maximum Capacity Path(MCP) for a given weighted graph.
 * This analysis is useful for finding the best paths in a network for routing.
 * 
 * 1. Dijkstra's Algorithm without using a heap to store fringe nodes
 * 2. Dijkstra's Algorithm with using a heap to store fringe nodes
 * 3. Kruskal's Algorithm for finding Maximum Spanning Tree
 *    The path in a Maximum Spanning Tree between any two nodes is MCP
 *
 * @author Nikhilesh pandey
 */
  
 
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;

public class MaxCapPathAnalysis {
    
    private static final int VERT_COUNT = 5000;
    private static int MAX_DEGREE;      
    
    public static void main(String[] args) throws InterruptedException {
        MAX_DEGREE = Integer.parseInt(args[0]);
        
        for(int i = 1; i <= 5; i++ ){
            System.out.println("###########################################################");
            System.out.println("\n\n");
            System.out.println("Inside Graph number : " + i + "\n\n\n");
            
            Graph myGraph = GraphGenerator.generateGraph(VERT_COUNT, MAX_DEGREE);
         
        for(int j = 1; j <= 5; j++ ){    
            System.out.println("----------------------------------------------------------------------");
            System.out.println("For source - destination pair : " + j + "\n\n");
        
        Random rn = new Random();
        int source = rn.nextInt(VERT_COUNT);
        int dest = rn.nextInt(VERT_COUNT);
        
        System.out.println("Source and Destination : " + source + " -> " + dest);
        System.out.println("\n");
        
        GraphGenerator.connnectGraph(myGraph, source, dest, VERT_COUNT);
        
        ArrayList<Edge> myGraphEdges = new ArrayList<>(myGraph.graphEdges);
        
        System.out.println("MCP using modified Dijkstra without using Heap starts : ");
        Long time1 = System.currentTimeMillis();
        invokeMCPDijWithoutHeap(myGraph, source, dest);
        Long time2 = System.currentTimeMillis();
        System.out.println("MCP using modified Dijkstra without using Heap ends. Time taken in milli secs : " + (time2 -time1));
        System.out.println("\n");
        
        System.out.println("MCP using modified Dijkstra with using Heap starts : ");
        Long time3 = System.currentTimeMillis();
        invokeMCPDijWithHeap(myGraph, source, dest);
        Long time4 = System.currentTimeMillis();
        System.out.println("MCP using modified Dijkstra with using Heap ends. Time taken in milli secs : " + (time4 -time3));
        System.out.println("\n");
        
        System.out.println("MCP by using Kruskal Max Spanning tree starts : ");
        Long time5 = System.currentTimeMillis();
        invokeMCPKruskal(myGraphEdges, source, dest);
        Long time6 = System.currentTimeMillis();
        System.out.println("MCP by using Kruskal Max Spanning tree ends. Time taken in milli secs: " + (time6 -time5) + "\n");
        
        }
        
        }
        
    }
    
    
    public static void invokeMCPKruskal(ArrayList<Edge> myGraphEdges, int mySrc, int myDest){    

        int[] dad = new int[5000];
        int[] rank = new int[5000];
        
        Arrays.fill(dad, -1);
        
        EdgeHeap edgeHeap = new EdgeHeap(myGraphEdges);
 
        ArrayList<Edge> maxSpanningTree = new ArrayList<>();
        
        for(Edge edge : myGraphEdges){
            int source = edge.source;
            int dest = edge.dest;
            
            // Find
            int r1 = find(source, dad);
            int r2 = find(dest, dad);
            
            // Union
            if(r1 != r2){
                maxSpanningTree.add(edge);
                if(rank[r1] > rank[r2]){
                    dad[r2] = r1;
                }else if(rank[r1] < rank[r2]){
                    dad[r1] = r2;
                }else{
                    dad[r2] = r1;
                    rank[r1]++;
                }
            }
        }            

        createMCPfromMST(maxSpanningTree, mySrc, myDest);

    }
    
    private static void createMCPfromMST(ArrayList<Edge> maxSpanningTree, int source, int dest) {
        Graph mstGraph = GraphGenerator.generateGraph(VERT_COUNT, MAX_DEGREE, maxSpanningTree);
        int[] dad = new int[5000];
        int[] D = new int[5000];
        char[] status = new char[5000];
        Arrays.fill(status, 'w');
        
        Arrays.fill(dad, -1);
        
        D[source] = 10000000;
        
        dfsOnTree(source, dest, mstGraph, dad, D, status);
        printMaxPath(D,dad,source,dest);
        
    }    
    
    private static void dfsOnTree(int source, int dest, Graph mstGraph, int[] dad, int[] D, char[] status) {
        
        status[source] = 'b';
        if(source == dest){ return;}
        
        for(Edge edge : mstGraph.nodes.get(source).edges){
            if(status[edge.dest] == 'b') continue;
            dad[edge.dest] = source;
            if(edge.weight < D[source]){
                D[edge.dest] = edge.weight;
            }else{
                D[edge.dest] = D[source];
            }
            
            dfsOnTree(edge.dest, dest, mstGraph, dad, D, status);
        }
    }
    
    private static void printMST(ArrayList<Edge> maxSpanningTree) {
        for(Edge edge : maxSpanningTree){
            System.out.print(edge.source + "--" + edge.weight + "--" + edge.dest + ",  ");
        }
    }
    
    public static int find(int r, int[] dad){

// Commented out code can be used to introduce "path compression"        
//        LinkedList<Integer> findpath = new LinkedList<>();
        while(dad[r] != -1){
//            findpath.add(r);
            r = dad[r];
        }
        
//        while(!findpath.isEmpty()){
//            int c = findpath.poll();
//            dad[c] = r;
//        }
        
        return r;
    }
    
    
    public static void invokeMCPDijWithHeap(Graph myGraph, int source, int dest) throws InterruptedException{
        char[] status = new char[5000];  // u = unseen; i = intree; f = fringe
        int[] D = new int[5000];
        int[] dad = new int[5000];

        MaxHeap fringe = new MaxHeap(); 
        
        Arrays.fill(status, 'u'); // initialise all nodes to unseen
        D[source] = 10000000;     // initialise max capacity valueof source to infinity
        status[source] = 'i';     // make source intree
        Arrays.fill(dad, -1);     // initialize dad for all nodes to none
        
        for(Edge edge : myGraph.nodes.get(source).edges){
            status[edge.dest] = 'f';
            D[edge.dest] = edge.weight;
            dad[edge.dest] = source;
            fringe.insert(edge.dest, D);
        }

        while(!fringe.isEmpty()){
            
            int maxFringe = fringe.delete(D);
            
            status[maxFringe] = 'i';
            
            for(Edge edge : myGraph.nodes.get(maxFringe).edges){
                if(status[edge.dest] == 'u'){
                    status[edge.dest] = 'f';
                    D[edge.dest] = Math.min(D[edge.source], edge.weight);
                    dad[edge.dest] = edge.source;
                    fringe.insert(edge.dest, D);
                }else if(status[edge.dest] == 'f' && D[edge.dest] < Math.min(D[edge.source], edge.weight)){                    
                    fringe.remove(edge.dest, D);                   
                    D[edge.dest] = Math.min(D[edge.source], edge.weight);
                    dad[edge.dest] = edge.source;
                    fringe.insert(edge.dest, D);
                    
                }
            }
        }
        
        printMaxPath(D,dad,source,dest);        
    }
    
    public static void invokeMCPDijWithoutHeap(Graph myGraph, int source, int dest){
        char[] status = new char[5000];  // u = unseen; i = intree; f = fringe
        int[] D = new int[5000];
        int[] dad = new int[5000];
        LinkedList<Integer> fringe = new LinkedList<>();
        
        Arrays.fill(status, 'u'); // initialise all nodes to unseen
        D[source] = 10000000;     // initialise max capacity valueof source to infinity
        status[source] = 'i';     // make source intree
        Arrays.fill(dad, -1);     // initialize dad for all nodes to none   
        
        
        for(Edge edge : myGraph.nodes.get(source).edges){
            status[edge.dest] = 'f';
            D[edge.dest] = edge.weight;
            dad[edge.dest] = source;
            fringe.add(edge.dest);
        }
        
        while(!fringe.isEmpty()){
            Integer maxFringe = fringe.element();
            for(Integer i : fringe){
                if(D[i] > D[maxFringe]){
                    maxFringe = i;
                }
            }
            
            fringe.remove(maxFringe);
            
            status[maxFringe] = 'i';
            
            for(Edge edge : myGraph.nodes.get(maxFringe).edges){
                if(status[edge.dest] == 'u'){
                    status[edge.dest] = 'f';
                    D[edge.dest] = Math.min(D[edge.source], edge.weight);
                    dad[edge.dest] = edge.source;
                    fringe.add(edge.dest);
                }else if(status[edge.dest] == 'f' && D[edge.dest] < Math.min(D[edge.source], edge.weight)){
                    D[edge.dest] = Math.min(D[edge.source], edge.weight);
                    dad[edge.dest] = edge.source;
                }
            }
        }
                
        printMaxPath(D,dad,source,dest);
        
    }

    private static void printMaxPath(int[] D, int[] dad, int source, int dest) {
        System.out.println("Below is the reversed path with max path values :");
        
        while(dad[dest] != -1){
            System.out.print(dest +" -> " + D[dest] + "; ");
            dest = dad[dest];
        } 
        
        System.out.println(dest +" -> " + D[dest] + "; ");
        
    }
    
    private static void printGraph(Graph G) {
        for (Node n : G.nodes){
            for(Edge edge : G.nodes.get(n.name).edges){
                System.out.print(edge.source + " --" + edge.weight + "-> " + edge.dest + "   ");
            }
            System.out.println("");
        }        
    }
    
    
}
