/*
 * Max Heap for "Edge" objects
 *
 * @author Nikhilesh pandey
 */

import java.util.ArrayList;

public class EdgeHeap {
    
    ArrayList<Edge> edges;
    int index;
    int ptr;

    
    public EdgeHeap(ArrayList<Edge> edges){
        this.edges = edges;
        index = edges.size() - 1;
        ptr = index;
        heapSort();
    }
    
    private void heapSort(){
        heapify();
        ptr = index;
        for (int i = ptr; i >= 0; i--)
        {
            swap(0, i);
            ptr = ptr-1;
            index  = index - 1;
            maxheap(0);
        }
        ptr = index;
    }
    
    public void heapify()
    {
    if(index==1 || index==0) return;
        
    for (int i = (index-2)/2; i >= 0; --i)
            maxheap(i);    
    }
    
    public void maxheap(int i)
    { 
        int left = 2*i+1 ;
        int right = 2*i + 2;
        int max = i;
        if (left < index && edges.get(left).weight < edges.get(i).weight)
            max = left;
        if (right < index && edges.get(right).weight < edges.get(max).weight)        
            max = right;
 
        if (max != i)
        {
            swap(i, max);
            maxheap(max);
        }
    }
    
    public void swap(int i, int j)
    {
        Edge tmp = edges.get(i);
        edges.set(i, edges.get(j));
        edges.set(j , tmp);     
    }   
    
}
