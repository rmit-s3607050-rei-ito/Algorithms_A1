import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T> extends Multiset<T>
{
  // Parameters of LinkedListMultiset (Doubly Linked List)
  protected Node nHead;      // Node at the start of the list
  protected Node nTail;      // Node at the end of the list
  protected int nCount;      // Total number of elements in the list

  // [ADDDED]: Node class, nodes present in the linked list
  private class Node {
    private T nValue;        // Value stored at a Node
    private Node nPrev;      // Previous node connected
    private Node nNext;      // Next node connected
    private boolean marked;  // Used for printing, when node visited, mark it

    // Node constructor
    public Node(T value) {
      nValue = value;
      nPrev = null;
      nNext = null;
      marked = false;
    }

    // Node Getters and Setters
    public T getValue() { return nValue; }
    public Node getPrev() { return nPrev; }
    public Node getNext() { return nNext; }

    public void setValue(T value) { nValue = value; }
    public void setPrev(Node prev)  { nPrev = prev; }
    public void setNext(Node next)  { nNext = next; }

    public boolean isMarked() { return marked; }
    public void unMark() { marked = false; }
    public void setMarked() { marked = true; }
  }

  // [ADDDED]: Function to return whether a match is made
  private boolean matchItem(T item1, T item2) {
    if (item1.equals(item2))
      return true;
    else
      return false;
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

    // If list is Empty, add node and set it as both head and tail
    if (nHead == null) {
      nHead = newNode;
      nTail = newNode;
    }

    // List is populated, add it to the start of the list only setting as head
    else {
      newNode.setNext(nHead);	  // Set new node's next to be previous node at head
      nHead.setPrev(newNode); 	// Set old head node's previous to be the new node
      nHead = newNode;			    // Set head to be at the new node
    }

    // Increase total count of nodes
    nCount += 1;
  } // end of add()

  // [USAGE]: Search the count of instances of element in the multiset, return result
  public int search(T item) {
    Node currNode = nHead;
    int count = 0;

    for (int i = 0; i < nCount; ++i)
    {
      // Whenever match made with element, increment count by one
      if (matchItem(currNode.getValue(), item))
        count++;

      currNode = currNode.getNext();
    }

    // Return number of instances found of element
    return count;
  } // end of search()

  // [USAGE]: Removes first instance of element when match is found
  public void removeOne(T item) {
    Node currNode = nHead;
    Node prevNode = null;
    Node nextNode = null;

    // [Case 1]: If node to remove matches the first index position (HEAD)
    if (matchItem(currNode.getValue(), item)) {
      // [i]. List is of length 1, empty list
      if (nCount == 1) {
        nHead = null;
        nTail = null;
      }
      // [i]. List length > 1, remove connection to first node
      else {
        nHead = currNode.getNext(); // node after deleted node is now the head node
        nHead.setPrev(null);        // remove link to deleted node
      }

      nCount--;
      return;
    }

    // [Case 2]: Node to remove is not at head (middle and end node)
    else {
      // [i]. Start at second node
      currNode = currNode.getNext();

      // [ii]. Iterate through rest of nodes to the end
      while (currNode != null) {
        if (matchItem(currNode.getValue(), item)) {
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

          nCount--;
          return;
        }
        // Continue iterating through list if match is not found
        currNode = currNode.getNext();
      }
    }
  } // end of removeOne()

  // [USAGE]: Remove all instances of element that matches in the search
  public void removeAll(T item) {
    if (search(item) == 0)
      return;

    removeOne(item);
    removeAll(item);
  } // end of removeAll()

  // [USAGE]: Print all unique nodes and their total count in the list
  public void print(PrintStream out) {
    // ########## OLD PRINT ##########
    Node currNode = nHead;

    StringBuffer str = new StringBuffer();

    while (currNode != null) {
        str.append(currNode.getValue() + " ");
        currNode = currNode.getNext();
    }

    out.println(str.toString());

    // ########## NEW PRINT ##########
    // Node currNode = nHead;
    // Node countNode = null;
    //
    // int total = 0;
    // int itemCount = 1;
    // T key = null;
    //
    // while (total != nCount) {
    //   if (!currNode.isVisited()) {
    //     key = currNode.getValue();
    //     countNode = currNode;
    //     total++;
    //
    //     for (int i = total; i < nCount-1; i++) {
    //       if (matchItem(key, countNode.getValue())){
    //           countNode.setVisited();
    //           itemCount++;
    //           total++;
    //       }
    //       countNode = countNode.getNext();
    //     }
    //
    //     currNode.setVisited();
    //     out.println(key + " " + printDelim + " " + itemCount);
    //   }
    //
    //   itemCount = 1;
    //   currNode = currNode.getNext();
    // }
    //
    // // Reset all visited nodes for next time when called
    // currNode = nHead;
    // for (int i = 0; i < nCount-1; i++) {
    //   currNode.setNotVisited();
    //   currNode = currNode.getNext();
    // }
  } // end of print()

} // end of class LinkedListMultiset