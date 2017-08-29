import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T> extends Multiset<T>
{
  // Parameters of SortedLinkedListMultiset (Sorted Doubly Linked List), same as non-sorted
  protected Node nHead;
  protected Node nTail;
  protected int nCount;

  // Node class, nodes present in the linked list, same as non-sorted
  private class Node {
    private T nValue;
    private Node nPrev;
    private Node nNext;
    private int count;

    // Node constructor
    public Node(T value) {
      nValue = value;
      nPrev = null;
      nNext = null;
      count = 1;
    }

    // Node Getters and Setters
    public T getValue() { return nValue; }
    public Node getPrev() { return nPrev; }
    public Node getNext() { return nNext; }
    public int getCount() { return count; }

    public void setValue(T value) { nValue = value; }
    public void setPrev(Node prev) { nPrev = prev; }
    public void setNext(Node next) { nNext = next; }

    public void addCount() { count++; }
    public void reduceCount() { count--; }
  }

  public SortedLinkedListMultiset() {
    // Initialize same parameters as empty doubly linked list
    nHead = null;
    nTail = null;
    nCount = 0;
  } // end of SortedLinkedListMultiset()

  // Extra function added: Retrieve node through index
  public Node getNode(int index) {
    // Throw an exception if index requested is too high or too low
    if (index >= nCount || index < 0) {
        throw new IndexOutOfBoundsException("Supplied index is invalid.");
    }

    // Request first node (return head node)
    if (index == 0)
      return nHead;
    // Request last node (return tail node)
    else if (index == nCount-1)
      return nTail;

    // Request any other node in between, iterate until node reached
    Node currNode = nHead.getNext();
    for(int i = 1; i < index; ++i)
      currNode = currNode.getNext();

    // Node should be reached once loop above has ended, return reached node
    return currNode;
  }

  public void sortList(){
    /* NOTE: Sorted using selection sort method following pseudocode in lecture notes
     * A strange thing i found, was that the lecture notes had:
     * i loop has: i = 0; i to n - 2
     * j loop has: j = i + 1; to n - 1
     * However implementing this did not work, so i found simply going through the
     * entirety of both loops fixed the problem as the first element would be skipped */

    int min = 0;
    int comparison = 0;
    // Nodes to store and swap values
    Node temp = null;
    Node n1 = null;
    Node n2 = null;
    // String values to compare if one is less than the other
    String value1 = "";
    String value2 = "";

    for (int i = 0; i < nCount; i++) {
      min = i;
      for (int j = i; j < nCount; j++) {
        value1 = (String) (getNode(j).getValue());
        value2 = (String) (getNode(min).getValue());
        comparison = value1.compareTo(value2);

        if(comparison < 0)
          min = j;
      }
      // SWAP A[i] and A[min] here
      n1 = getNode(i);
      n2 = getNode(min);
      temp = new Node(n1.getValue());

      n1.setValue(n2.getValue());
      n2.setValue(temp.getValue());
    }
  }

  public void add(T item) {
    /* NOTE: Same functionality as linkedListMultiset's add(), so no commenting
     * has been done to old sections */

    Node newNode = new Node(item);
    Node currNode = nHead;

    if (nHead == null) {
      nHead = newNode;
      nTail = newNode;
    }
    else {
      while (currNode != null) {
        if (currNode.getValue().equals(item)) {
          currNode.addCount();
          return;
        }
        currNode = currNode.getNext();
      }

      newNode.setNext(nHead);
      nHead.setPrev(newNode);
      nHead = newNode;
    }

    nCount += 1;
    sortList();                 // [NEW] - sorting done after adding the node
  } // end of add()

  public int search(T item) {
    /* NOTE: Same functionality as linkedListMultiset's search(), so no commenting
     * has been done to old sections*/
    Node currNode = nHead;
    int count = 0;

    while (currNode != null) {
      if (currNode.getValue().equals(item)) {
        count = currNode.getCount();
        break;
      }
      currNode = currNode.getNext();
    }

    return count;
  } // end of add()

  public void removeOne(T item) {
    /* NOTE: Same functionality as linkedListMultiset's removeOne(), so no commenting
     * has been done to old sections*/
    Node currNode = nHead;
    while (currNode != null) {
      if (currNode.getValue().equals(item)) {
        if (currNode.getCount() > 1) {
          currNode.reduceCount();
          return;
        } else {
          removeAll(item);
          return;
        }
      }
      currNode = currNode.getNext();
    }
  } // end of removeOne()

  public void removeAll(T item) {
    /* NOTE: Same functionality as linkedListMultiset's removeAll(), so no commenting
     * has been done to old sections*/

     Node currNode = nHead;
     Node prevNode = null;
     Node nextNode = null;

     if (currNode.getValue().equals(item)) {
       if (nCount == 1) {
         nHead = null;
         nTail = null;
       }
       else {
         nHead = currNode.getNext();
         nHead.setPrev(null);
       }
       nCount--;
       return;
     }
     else {
       currNode = currNode.getNext();

       while (currNode != null) {
         if (currNode.getValue().equals(item)) {
           prevNode = currNode.getPrev();
           prevNode.setNext(currNode.getNext());
           if (currNode.getNext() == null)
             nTail = prevNode;
           else {
             nextNode = currNode.getNext();
             nextNode.setPrev(prevNode);
           }
           nCount--;
           return;
         }
         currNode = currNode.getNext();
       }
     }
  } // end of removeAll()

  public void print(PrintStream out) {
    /* NOTE: Same functionality as linkedListMultiset's print(), so no commenting
     * has been done to old sections*/
    Node currNode = nHead;
    while (currNode != null) {
      out.println(currNode.getValue() + printDelim + currNode.getCount());

      currNode = currNode.getNext();
    }
  } // end of print()

} // end of class SortedLinkedListMultiset
