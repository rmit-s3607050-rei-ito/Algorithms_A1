import java.io.PrintStream;
import java.util.*;

public class BstMultiset<T> extends Multiset<T>
{
  protected BstNode mRoot;

  public BstMultiset() {
    this.mRoot = null;
  } // end of BstMultiset()

  public void add(T item) {
    BstNode newItem = new BstNode((String)item);

    if (mRoot == null) {
      mRoot = newItem;
      return;
    }

    BstNode current = mRoot;
    BstNode parent = null;

    while (true) {
      parent = current;
      if (current.key.compareTo((String)item) < 0) {
        current = current.left;
        if (current == null) {
          parent.left = newItem;
          return;
        }
      }
      else if (current.key.compareTo((String)item) > 0 ) {
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

    BstNode current = mRoot;

    while (current != null) {
      if (current.key.compareTo((String)item) == 0) {
        count++;
      }
      else if (current.key.compareTo((String)item) < 0) {
        current = current.left;
      }
      else {
        current = current.right;
      }
    }

    return count;
  } // end of search()


  public void removeOne(T item) {
    BstNode parent = mRoot;
    BstNode current = mRoot;
    boolean isLeftChild = false;

    while (current.key != (String)item) {
      parent = current;
      if (current.key.compareTo((String)item) < 0) {
        isLeftChild = true;
        current = current.left;
      }
      else {
        isLeftChild = false;
        current = current.right;
      }

      if (current == null)
        return;
    }

    if (current.left == null && current.right == null) {
      if (current == mRoot)
        mRoot = null;
      if (isLeftChild)
        parent.left = null;
      else
        parent.right = null;
    }
    else if (current.right == null) {
      if (current == mRoot)
        mRoot = current.left;
      else if (isLeftChild)
        parent.left = current.left;
      else
        parent.right = current.left;
    }
    else if (current.left == null) {
      if (current == mRoot)
        mRoot = current.right;
      else if (isLeftChild)
        parent.left = current.right;
      else
        parent.right = current.right;
    }
    else if (current.left != null && current.right != null) {
      BstNode successor = getSuccessor(current);

      if (current == mRoot)
        mRoot = successor;
      else if (isLeftChild)
        parent.left = successor;
      else
        parent.right = successor;

      successor.left = current.left;
    }
  } // end of removeOne()

  private BstNode getSuccessor(BstNode delNode) {
    BstNode successor = null;
    BstNode successorParent = null;
    BstNode current = delNode.right;

    while (current != null) {
      successorParent = successor;
      successor = current;
      current = current.left;
    }

    if (successor != delNode.right) {
      successorParent.left = successor.right;
      successor.right = delNode.right;
    }

    return successor;
  }

  public void removeAll(T item) {
    BstNode parent = mRoot;
    BstNode current = mRoot;
    boolean isLeftChild = false;

    while (current.key != (String)item) {
      parent = current;
      if (current.key.compareTo((String)item) < 0) {
        isLeftChild = true;
        current = current.left;
      }
      else {
        isLeftChild = false;
        current = current.right;
      }

      if (current == null)
        return;
    }

    if (current.left == null && current.right == null) {
      if (current == mRoot)
        mRoot = null;
      if (isLeftChild)
        parent.left = null;
      else
        parent.right = null;
    }
    else if (current.right == null) {
      if (current == mRoot)
        mRoot = current.left;
      else if (isLeftChild)
        parent.left = current.left;
      else
        parent.right = current.left;
    }
    else if (current.left == null) {
      if (current == mRoot)
        mRoot = current.right;
      else if (isLeftChild)
        parent.left = current.right;
      else
        parent.right = current.right;
    }
    else if (current.left != null && current.right != null) {
      BstNode successor = getSuccessor(current);

      if (current == mRoot)
        mRoot = successor;
      else if (isLeftChild)
        parent.left = successor;
      else
        parent.right = successor;

      successor.left = current.left;
    }
  } // end of removeAll()


  public void print(PrintStream out) {
    printTree(mRoot, out);
  } // end of print()

  private void printTree(BstNode root, PrintStream out) {
    if (root != null) {
      printTree(root.left, out);
      out.print(" " + (String)root.key);
      printTree(root.right, out);
    }
  }

} // end of class BstMultiset
