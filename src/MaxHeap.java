/*
 * Max Heap for values in "Node" objects
 *
 * @author Nikhilesh pandey
 */

import java.util.Arrays;

public class MaxHeap {
    
    int[] H = new int[50000];
    int index;
    
    public MaxHeap(){
        Arrays.fill(H, -1);
        index = -1;
    }
    
    public int maximum(){
        if(H[0] >= 0){
            return H[0];
        }
        
        return -1;
    }
    
    public void insert(int val, int[] D){
        index++;
        H[index] = val; 
        int ptr = index;
        
        while(ptr > 0 && D[val] > D[H[(ptr + 1)/2 -1]] ){
            int temp = val;
            H[ptr] = H[(ptr + 1)/2 -1];
            H[(ptr + 1)/2 -1] = temp;
            ptr = (ptr + 1)/2 -1;
        }
    }
    
    public int delete(int[] D){
        
        if(index < 0){
            return -1;
        }
        
        int deleted = H[0];
        H[0] = H[index];
        H[index] = -1;
        index--;
        
        int ptr = 0;
        
        while(ptr <= ((index+1)/2 -1)){
            
            int lChild = H[ptr*2 + 1];
            
            int rChild = H[ptr*2 + 2];
            
            if(isLarger(lChild, H[ptr], D) && isLarger(lChild, rChild, D)){
                int temp = H[ptr*2 + 1];
                H[ptr*2 + 1] = H[ptr];
                H[ptr] = temp;
                ptr = ptr*2 + 1;
            }else if(isLarger(rChild, H[ptr], D)){
                int temp = H[ptr*2 + 2];
                H[ptr*2 + 2] = H[ptr];
                H[ptr] = temp;
                ptr = ptr*2 + 2;                
            }else{
                break;
            }
        }
        
        return deleted;
        
    }
    
    public void remove(int val, int[] D){
        int remObjIdx = -1;
        for(int i = 0; i < 15000; i++){
            if(val == H[i]){
               remObjIdx = i;
               break;
            }
        }
        
        if(remObjIdx == -1) return;
        
        H[remObjIdx] = H[index];
        H[index] = -1;
        index--; 
        
        int ptr = remObjIdx;
        
        while(ptr <= ((index+1)/2 -1)){

            int lChild = H[ptr*2 + 1];
            int rChild = H[ptr*2 + 2];
            
            if(isLarger(lChild, H[ptr], D) && isLarger(lChild, rChild, D)){
                int temp = H[ptr*2 + 1];
                H[ptr*2 + 1] = H[ptr];
                H[ptr] = temp;
                ptr = ptr*2 + 1;
            }else if(isLarger(rChild, H[ptr], D)){
                int temp = H[ptr*2 + 2];
                H[ptr*2 + 2] = H[ptr];
                H[ptr] = temp;
                ptr = ptr*2 + 2;                
            }else{
                break;
            }
        }        
        
    }

    public boolean isEmpty(){
        return (H[0] < 0);
    }
    
    private boolean isLarger(int first, int second, int[] D) {
        if(first < 0){
            return false;
        }
        
        if(second < 0) return true;
        
        return (D[first] > D[second]);
    }    
    
}
