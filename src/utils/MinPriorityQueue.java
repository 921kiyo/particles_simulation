package utils;
import java.util.*;

/**
 *
 * @author kiyo
 * @param <T>
 */
public class MinPriorityQueue<T extends Comparable<T>> {
    // has to run with O(log2N)
     // int pointer;
     ArrayList<T> heap;
    public MinPriorityQueue() {
       // pointer = 0; // Keeps track of the next available index
       heap = new ArrayList<T>();
        // Compare between parent and child node, if the child one is greater, switch the two keys
    }

    /**
     * Returns the number of elements currently in the queue.
     */
    public int size() {
        return heap.size();
    }
    /**
     * Adds elem to the queue.
     */
    public void add(T elem) {
        int position = size();
        heap.add(position, elem);

        // While position is not at the top,
        // compare it with child nodes.
        while( position > 0){
          int parent = (position + 1) / 2 - 1;
          // If heap[parent] is the smallest, break the while loop
          if(heap.get(parent).compareTo(heap.get(position)) <= 0) break;
          swapIndex(parent, position);
          position = parent;
        }
    }

    private void swapIndex(int i, int j){
      T temp = (T)heap.get(i);
      heap.set(i, heap.get(j));
      heap.set(j, temp);
    }
    // This is just helper function to see the elements in the list ]
    // TODO Delete this function
    public void print_queue(){
      System.out.println("tree is now ");
      for(int i = 0; i < heap.size(); i++){
          System.out.println(heap.get(i));
      }
    }

    /**
     * Removes, and returns, the element at the front of the queue.
     */
    public T remove() {
      // returns the smallest element, and remove it from the queue
        // return the smallest element
        // Remove it, take the bottom right node and put it at the top
        // After that, compare the two children nodes,
        // If empty, throw an exception
//        if(heap.isEmpty()) throw new IllegalStateException();
        T min_value = (T)heap.get(0);
        // Move end of node to the beginning (top)
        heap.set(0, heap.get(size()-1));
        heap.remove(size()-1);
        int position = 0;

        // Example of tree structure for the priority queue
        // Parent -> Child, Children
        // 0      -> 1,     2
        // 1      -> 3,     4
        // 2      -> 5,     6
        // Children are multiple of 2 away from the parent
        
        while(position < size() / 2){
          int left_child_index = position * 2 + 1; // look at the above example
          int right_child_index = left_child_index + 1;
          // If right child exists and is less than left child,
          // swap it with parent node
          if (right_child_index < size() && heap.get(left_child_index).compareTo(heap.get(right_child_index)) > 0){
            if(heap.get(position).compareTo(heap.get(right_child_index)) <= 0) break;
            swapIndex(position, right_child_index);
            position = right_child_index;
          }else{
            // If the left child is less than parent, swap it.
            if(heap.get(position).compareTo(heap.get(left_child_index)) <= 0) break;
            swapIndex(position, left_child_index);
            position = left_child_index;
          }
        }
        return min_value;
    }

    /**
     * Returns true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (size() == 0);
    }

}
