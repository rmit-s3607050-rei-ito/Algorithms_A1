import java.io.PrintStream;
import java.util.*;

public class BstMultiset<T> extends Multiset<T>
{
  protected Node mRoot;

  public BstMultiset() {
    this.mRoot = null;
  } // end of BstMultiset()

  public void add(T item) {
    Node newItem = new Node(item);

    if (mRoot == null) {
      mRoot = newItem;
      return;
    }

    Node current = mRoot;
    Node parent = null;

    while (true) {
      parent = current;
      if ((String)item.compareTo(current.key) < 0) {
        current = current.left;
        if (current == null) {
          parent.left = newItem;
          return;
        }
      }
      else if ((String)item.compareTo(current.key) > 0 ) {
        current = current.right;
        if (current == null) {
          parent.right = newItem;
          return;
        }
      }
      else {

      }
    }
  } // end of add()


  public int search(T item) {
    int count = 0;

    Node current = mRoot;

    while (current != null) {
      if ((String)item.compareTo(current.key) == 0) {
        count++;
      }
      else if (item.compareTo(current.key) < 0) {
        current = current.left;
      }
      else if (item.Comparable(current.key) > 0) {
        current = current.right;
      }
    }

    return count;
  } // end of search()


  public void removeOne(T item) {
    // Implement me!
  } // end of removeOne()


  public void removeAll(T item) {
    // Implement me!
  } // end of removeAll()


  public void print(PrintStream out) {

  } // end of print()

} // end of class BstMultiset
