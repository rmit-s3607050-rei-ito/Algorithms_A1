import java.io.*;

class Test {
  public static void main(String[] args) {
    Multiset<String> multiset = new BstMultiset<String>();
    PrintStream outStream = System.out;
    multiset.add("hello");
    multiset.add("hello");
    multiset.add("well");
    multiset.add("allo");
    multiset.add("well");
    multiset.removeOne("well");
    multiset.removeOne("well");
    multiset.removeAll("hello");
    multiset.print(outStream);
    System.out.println("done");
  }
}
