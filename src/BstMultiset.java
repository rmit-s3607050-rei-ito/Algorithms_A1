import java.io.PrintStream;
import java.util.*;

public class BstMultiset<T> extends Multiset<T>
{
  private class Node {
    String key;
    Node left;
    Node right;
    int count;

    public Node (String key) {
      this.key = key;
      left = null;
      right = null;
      count = 1;
    }

    String getKey() { return key; }
    Node getLeft() { return left; }
    Node getRight() { return right; }
    int getCount() { return count; }

    void setKey(String newKey) { key = newKey; }
    void setLeft(Node newNode) { left = newNode; }
    void setRight(Node newNode) { right = newNode; }
    void setCount(int newCount) { count = newCount; }

    void incCount() { count++; }
    void decCount() { count--; }
  }

  protected Node mRoot;

  public BstMultiset() {
    mRoot = null;
  } // end of BstMultiset()

  public void add(T item) {
    mRoot = insert(mRoot, (String)item);
  } // end of add()

  private Node insert(Node root, String key) {
    // check if root is empty
    if (root == null) {
      root = new Node(key);
    }
    else if (root.getKey().compareTo(key) < 0) {
      root.setLeft(insert(root.getLeft(), key));
    }
    else if (root.getKey().compareTo(key) > 0) {
      root.setRight(insert(root.getRight(), key));
    }
    else {
      root.incCount();
    }

    return root;
  } // end of insert()

  private Node searchNode(Node root, String key) {
    if (root == null)
      return root;

    if (root.getKey().compareTo(key) < 0)
      return searchNode(root.getLeft(), key);
    else if (root.getKey().compareTo(key) > 0)
      return searchNode(root.getRight(), key);

    return root;
  }

  public int search(T item) {
    Node searched = searchNode(mRoot, (String)item);
    if (searched == null)
      return 0;

    return searched.getCount();
  } // end of search()


  public void removeOne(T item) {
    Node searched = searchNode(mRoot, (String)item);
    if (searched == null)
      return;

    if (searched.getCount() > 1)
      searched.decCount();
    else
      removeAll(item);
  } // end of removeOne()

  public void removeAll(T item) {
    mRoot = deleteNode(mRoot, (String)item);
  }

  private Node minElement(Node root) {
    if (root.left == null)
      return root;
    else
      return minElement(root.left);
  }

  private Node deleteNode(Node root, String key) {
    if (root == null)
      return null;

    if (root.getKey().compareTo(key) < 0)
      root.setLeft(deleteNode(root.getLeft(), key));
    else if (root.getKey().compareTo(key) > 0)
      root.setRight(deleteNode(root.getRight(), key));
    else {
      // if nodeToBeDeleted have both children
      if (root.getLeft() != null && root.getRight() != null) {
        //Node temp = root;
        // Finding minimum element from right
        Node minNodeForRight = minElement(root.getRight());
        // Replacing current node with minimum node from right subtree
        root.setKey(minNodeForRight.getKey());
        root.setCount(minNodeForRight.getCount());
        // Deleting minimum node from right now
        root.setRight(deleteNode(root.getRight(), minNodeForRight.getKey()));
      }
      // if nodeToBeDeleted has only left child
      else if (root.getLeft() != null)
        root = root.getLeft();
      // if nodeToBeDeleted has only right child
      else if (root.getRight() != null)
        root = root.getRight();
      // if nodeToBeDeleted do not have child (Leaf node)
      else
        root = null;
    }
    return root;
  }

  public void print(PrintStream out) {
    printTree(mRoot, out);
  } // end of print()

  private void printTree(Node root, PrintStream out) {
    if (root != null) {
      out.println((String)root.getKey()+printDelim+root.getCount());
      printTree(root.getLeft(), out);
      printTree(root.getRight(), out);
    }
  }

} // end of class BstMultiset
