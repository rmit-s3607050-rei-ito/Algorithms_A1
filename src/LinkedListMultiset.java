import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T> extends Multiset<T>
{
  // Parameters of LinkedListMultiset (Doubly Linked List)
  protected Node nHead;      // Node at the start of the list
  protected Node nTail;      // Node at the end of the list
  protected int nCount;      // Total number of elements in the list

  // Node class, nodes present in the linked list
  private class Node {
    private T nValue;        // Value stored at a Node
    private Node nPrev;      // Previous node connected
    private Node nNext;      // Next node connected
    private int count;       // Used for counting duplicate data entries

    // Node constructor
    public Node(T value) {
      nValue = value;
      nPrev = null;
      nNext = null;
      count = 1;
    }

    // Node Getters and Setters
    public T getValue() { return nValue; }
    public int getCount() { return count; }
    public Node getPrev() { return nPrev; }
    public Node getNext() { return nNext; }

    public void setValue(T value)  { nValue = value; }
    public void setPrev(Node prev) { nPrev = prev; }
    public void setNext(Node next) { nNext = next; }

    public void addCount() { count++; }
    public void reduceCount() { count--; }
  }

  public LinkedListMultiset() {
    // Initialize empty doubly linked list
    nHead = null;
    nTail = null;
    nCount = 0;
  } // end of LinkedListMultiset()

  // [USAGE]: Adds an item to the start of the list
  public void add(T item) {
    Node newNode = new Node(item);
    Node currNode = nHead;

    // [1]. List is Empty, add node and set it as both head and tail
    if (nHead == null) {
      nHead = newNode;
      nTail = newNode;
    }

    // [2]. List is populated
    else {
      // i) check if it is already in the list, if so increment count then exit
      while (currNode != null) {
        if (currNode.getValue().equals(item)) {
          currNode.addCount();
          return;
        }
        currNode = currNode.getNext();
      }

      // ii) otherwise add it to the start of the list setting it as head
      newNode.setNext(nHead);	  // Set new node's next to be previous node at head
      nHead.setPrev(newNode); 	// Set old head node's previous to be the new node
      nHead = newNode;			    // Set head to be at the new node
    }

    nCount += 1;                // Increase total count of nodes
  } // end of add()

  // [USAGE]: Search the count of instances of element in the multiset, return result
  public int search(T item) {
    Node currNode = nHead;
    int count = 0;

    while (currNode != null) {
      // Whenever match made with element, obtain count then immediately exit to return
      if (currNode.getValue().equals(item)) {
        count = currNode.getCount();
        break;
      }
      currNode = currNode.getNext();
    }

    // Return number of instances found of element
    return count;
  } // end of search()

  // [USAGE]: Removes first instance of element when match is found
  public void removeOne(T item) {
    Node currNode = nHead;

    // Loop through the entire linked list
    while (currNode != null) {
      // When item is found check its count
      if (currNode.getValue().equals(item)) {
        if (currNode.getCount() > 1) { // More than one item, simply reduce count
          currNode.reduceCount();
          return;
        } else { // Exactly one, remove the node itself
          removeAll(item);
          return;
        }
      }
      currNode = currNode.getNext();
    }
  } // end of removeOne()

  // [USAGE]: Remove all instances of element that matches in the search
  public void removeAll(T item) {
    Node currNode = nHead;
    Node prevNode = null;
    Node nextNode = null;

    // [Case 1]: If node to remove matches the first index position (HEAD)
    if (currNode.getValue().equals(item)) {
      // [ii]. List is of length 1, empty list
      if (nCount == 1) {
        nHead = null;
        nTail = null;
      }
      // [iii]. List length > 1, remove connection to first node
      else {
        nHead = currNode.getNext(); // node after deleted node is now the head node
        nHead.setPrev(null);        // remove link to deleted node
      }
      nCount--; // Reduce total number of nodes
      return;
    }

    // [Case 2]: Node to remove is not at head (middle or end node)
    else {
      // [i]. Start at second node
      currNode = currNode.getNext();

      // [ii]. Iterate through rest of nodes to the end
      while (currNode != null) {
        if (currNode.getValue().equals(item)) {
          // When matched, set previous node's next to be deleted node's next
          prevNode = currNode.getPrev();
          prevNode.setNext(currNode.getNext());
          /* Check if the deleted node was the tail node
           * [A]: Deleted node was at the tail, set the previous node as new tail */
          if (currNode.getNext() == null)
            nTail = prevNode;
          // [B]: Deleted node has another node in front, relink next node
          else {
            nextNode = currNode.getNext();
            nextNode.setPrev(prevNode);
          }
          nCount--; // Reduce total node count
          return;
        }
        // Continue iterating through list if match is not found
        currNode = currNode.getNext();
      }
    }
  } // end of removeAll()

  // [USAGE]: Print all nodes and their total count in the list
  public void print(PrintStream out) {
    /* Since their count is stored internally within them, we can simply print out
     * the count value */
    Node currNode = nHead;
    while (currNode != null) {
      out.println(currNode.getValue() + printDelim + currNode.getCount());

      currNode = currNode.getNext();
    }
  } // end of print()

} // end of class LinkedListMultiset
