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
    this.mRoot = null;
  } // end of BstMultiset()

  public void add(T item) {
    Node newItem = new Node((String)item);

    if (mRoot == null) {
      mRoot = newItem;
      return;
    }

    Node current = mRoot;
    Node parent = null;

    while (true) {
      parent = current;
      if (current.getKey().compareTo((String)item) < 0) {
        current = current.getLeft();
        if (current == null) {
          parent.setLeft(newItem);
          return;
        }
      }
      else if (current.getKey().compareTo((String)item) > 0 ) {
        current = current.getRight();
        if (current == null) {
          parent.setRight(newItem);
          return;
        }
      }
      else {
        current.incCount();
        return;
      }
    }
  } // end of add()

  private Node searchNode(T item) {
    Node current = mRoot;
    while (current != null) {
      if (current.getKey().compareTo((String)item) == 0) {
        return current;
      }
      else if (current.getKey().compareTo((String)item) < 0) {
        current = current.getLeft();
      }
      else {
        current = current.getRight();
      }
    }
    return null;
  }

  public int search(T item) {
    Node sNode = searchNode(item);
    if (sNode == null) {
      return 0;
    }
    else {
      return sNode.getCount();
    }
  } // end of search()


  public void removeOne(T item) {
    Node found = searchNode(item);
    if (found.getCount() > 1) {
      found.decCount();
    }
    else {
      removeAll(item);
    }
  } // end of removeOne()

  //private Node getSuccessor(Node delNode) {
  //  Node successor = null;
  //  Node successorParent = null;
  //  Node current = delNode.getRight();

  //  while (current != null) {
  //    successorParent = successor;
  //    successor = current;
  //    current = current.getLeft();
  //  }

  //  if (successor != delNode.getRight()) {
  //    successorParent.setLeft(successor.getRight());
  //    successor.setRight(delNode.getRight());
  //  }

  //  return successor;
  //}

  //public void removeAll(T item) {
  //  Node parent = mRoot;
  //  Node current = mRoot;
  //  boolean isLeftChild = false;

  //  while (current.getKey() != (String)item) {
  //    parent = current;
  //    if (current.getKey().compareTo((String)item) < 0) {
  //      isLeftChild = true;
  //      current = current.getLeft();
  //    }
  //    else {
  //      isLeftChild = false;
  //      current = current.getRight();
  //    }

  //    if (current == null)
  //      return;
  //  }

  //  if (current.getLeft() == null && current.getRight() == null) {
  //    if (current == mRoot)
  //      mRoot = null;
  //    if (isLeftChild)
  //      parent.setLeft(null);
  //    else
  //      parent.setRight(null);
  //  }
  //  else if (current.getRight() == null) {
  //    if (current == mRoot)
  //      mRoot = current.getLeft();
  //    else if (isLeftChild)
  //      parent.setLeft(current.getLeft());
  //    else
  //      parent.setRight(current.getLeft());
  //  }
  //  else if (current.getLeft() == null) {
  //    if (current == mRoot)
  //      mRoot = current.getRight();
  //    else if (isLeftChild)
  //      parent.setLeft(current.getRight());
  //    else
  //      parent.setRight(current.getRight());
  //  }
  //  else if (current.getLeft() != null && current.getRight() != null) {
  //    Node successor = getSuccessor(current);

  //    if (current == mRoot)
  //      mRoot = successor;
  //    else if (isLeftChild)
  //      parent.setLeft(successor);
  //    else
  //      parent.setRight(successor);

  //    successor.setLeft(current.getLeft());
  //  }
  //} // end of removeAll()

  public void removeAll(T item) {
    System.out.println("called");
    deleteNode(mRoot, (String)item);
  }

  private Node minElement(Node root) {
    if (root.left == null)
      return root;
    else
      return minElement(root.left);
  }

  private Node deleteNode(Node root, String value) {
    if (root == null)
      return null;

    if (root.getKey().compareTo(value) < 0)
      root.setLeft(deleteNode(root.getLeft(), value));
    else if (root.getKey().compareTo(value) > 0)
      root.setRight(deleteNode(root.getRight(), value));
    else {
      // if nodeToBeDeleted have both children
      if (root.getLeft() != null && root.getRight() != null) {
        Node temp = root;
        // Finding minimum element from right
        Node minNodeForRight = minElement(temp.getRight());
        // Replacing current node with minimum node from right subtree
        root.setKey(minNodeForRight.getKey());
        // Deleting minimum node from right now
        deleteNode(root.getRight(), minNodeForRight.getKey());
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
