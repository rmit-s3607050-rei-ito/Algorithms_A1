/* Implementation of the assignment data generator, program will produce a .in file which can then be read in the tester
 * NOTE: Ideas for functions obtained from file: MultisetTester.java from assignment code
 * Dictionary of words obtained from C++ assignment, dict.dat (1507 total words)
 * Idea for getting random string from list: https://stackoverflow.com/questions/12028205/randomly-choose-a-word-from-a-text-file
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class Generator {
  protected static final String progName = "Generator";
  protected static final String dictionaryFile = "./dict.dat";
  protected static final String outputExtension = ".in";

  protected static final String add = "a ";
  protected static final String removeOne = "ro ";
  protected static final String removeAll = "ra ";
  protected static final String search = "s ";

  protected static final String print = "p";
  protected static final String quit = "q";

  protected static final int numRequiredArgs = 4;

  // [USAGE]: Opens the dictionary file, storing all entries into the dictionary list
  public static void loadDictionary(List<String> dictionaryList) {
    try {
      String line = "";
      BufferedReader br = new BufferedReader(new FileReader(dictionaryFile));
      while((line = br.readLine()) != null) {
        dictionaryList.add(line);
      }
      // Close dictionary file when done
      br.close();
    }
    catch(FileNotFoundException ex) {
      System.err.println("[ERROR] - File not found: " + dictionaryFile);
    }
    catch(IOException ex) {
      System.err.println("[ERROR] - Unable to read file: " + dictionaryFile);
    }
  }

  // [USAGE]: Save stored list data into an output file
  public static void saveOutput(List<String> outputList, String outputFileName) {
    try {
      // Open a filewriter in order to save contents to file
      FileWriter output = new FileWriter(outputFileName + outputExtension);
      // Iterate through each line of the list, then write
      for(String line: outputList)
        output.write(line + "\n");

      // Close the file when done
      output.close();
    }
    catch(FileNotFoundException ex) {
      System.err.println("[ERROR] - File not found: " + dictionaryFile);
    }
    catch(IOException ex) {
      System.err.println("[ERROR] - Unable to read file: " + dictionaryFile);
    }
  }

  // [USAGE]: Obtains a random string from the list and returns it
  public static String getRandomFromList(List<String> list) {
    int listSize = list.size();
    Random rand = new Random();
    String randomString = list.get(rand.nextInt(listSize));

    return randomString;
  }

  // [USAGE]: Runs scenario1 with additions/removals (as specified in assignment specs)
  public static void addScenarioOne(List<String> outList, List<String> searchWords,
                                    int numCommands) {
    Random rand = new Random();
    int min = 40;
    int max = 60;
    double addRatio = (rand.nextInt(max - min + 1) + min) / 100.0;
    int numAdd = (int)(numCommands * addRatio);
    int numRemove = numCommands - numAdd;

    List<String> removals = Arrays.asList(removeOne, removeAll);
    List<String> ratioList = new ArrayList<String>();
    String command = "";
    String word = "";

    // Add add commands
    command = add;
    for (int i = 0; i < numAdd; i++) {
      word = getRandomFromList(searchWords);
      ratioList.add(command + word);
    }
    // Add removal commands, will randomly select between ro and ra
    for (int i = 0; i < numRemove; i++) {
      word = getRandomFromList(searchWords);
      command = getRandomFromList(removals);

      ratioList.add(command + word);
    }

    // Shuffle all elements in the temporary generated list
    Collections.shuffle(ratioList);

    // Append all these elements onto main list
    outList.addAll(ratioList);
  }

  // [USAGE]: Runs scenario2 with searches (as specified in assignment specs)
  public static void addScenarioTwo(List<String> outList, List<String> searchWords,
                                    int numCommands) {
    // Preset ratio of (60%,20%,20%) = (search, remove, add)
    double sRatio = 0.6;       double aRatio = 0.2;       double rRatio = 0.2;

    int numSearch = (int) (sRatio * numCommands);
    int numAdd = (int) (aRatio * numCommands);
    int numRemove = (int) (aRatio * numCommands);

    List<String> removals = Arrays.asList(removeOne, removeAll);
    List<String> ratioList = new ArrayList<String>();
    String command = "";
    String word = "";

    // Add search commands
    command = search;
    for (int i = 0; i < numSearch; i++) {
      word = getRandomFromList(searchWords);
      ratioList.add(command + word);
    }
    // Add add commands
    command = add;
    for (int i = 0; i < numAdd; i++) {
      word = getRandomFromList(searchWords);
      ratioList.add(command + word);
    }
    // Add removal commands, will randomly select between ro and ra
    for (int i = 0; i < numRemove; i++) {
      word = getRandomFromList(searchWords);
      command = getRandomFromList(removals);

      ratioList.add(command + word);
    }

    // Shuffle all elements in the temporary generated list
    Collections.shuffle(ratioList);

    // Append all these elements onto main list
    outList.addAll(ratioList);
  }

  // [USAGE]: Append custom scenario onto the base adds
  public static void addCustomScenario(List<String> outList, List<String> searchWords,
                                       List<String> commands, String commandType,
                                       int numCommands) {
    String command = "";
    String word = "";
    boolean random = false;

    switch(commandType) {
      case "add":
        command = add;
        break;
      case "removeOne":
        command = removeOne;
        break;
      case "removeAll":
        command = removeAll;
        break;
      case "search":
        command = search;
        break;
      case "print":
        command = print;
        break;
      case "random":
        random = true;
        break;
    }

    for (int i = 0; i < numCommands; i++) {
      if (random) // Get a random command
        command = getRandomFromList(commands);

      word = getRandomFromList(searchWords);
      outList.add(command + word);
    }
  }

  public static void printUsage(String progName) {
    System.err.println(progName + ": ");
    System.err.println(" 1. <outputName> [Filename for output file (saved as .in)]");
    System.err.println(" 2. <multisetSize> [Number of elements by default in the multiset]");
    System.err.println(" 3. <commandType> [Type of commands to append]");
    System.err.println("    <commandType> = < add | removeOne | removeAll | search | random | scenario1 | scenario2 >");
    System.err.println(" 4. <numCommands> [Number of extra commands to append]");
    System.exit(1);
  } // end of usage

  public static void main(String[] args) {
    // Fixed size list with all commands
    List<String> commands = Arrays.asList(add, removeOne, removeAll, search);
    // Overall output to save to file
    List<String> outputList = new ArrayList<String>();
    // Dictionary for words that can be selected
    List<String> dictionaryList = new ArrayList<String>();
    // Words that have been picked from the dictionary (used for search)
    List<String> wordSet = new ArrayList<String>();

    String randWord = "";
    String outFileName = "";
    int size = 0;
    String commandType = "";
    int numCommands = 0;

    // Check number of command line arguments
    if (args.length != numRequiredArgs) {
      System.err.println("Incorrect number of arguments.");
      printUsage(progName);
    }

    // Store command line args are variables
    outFileName = args[0];
    size = Integer.parseInt(args[1]);
    commandType = args[2];
    numCommands = Integer.parseInt(args[3]);

    // Read in dictionary of all possible words
    loadDictionary(dictionaryList);

    // [1]. Save in default adds statements into the output based on the size
    for (int i = 0; i < size; i++) {
      randWord = getRandomFromList(dictionaryList);
      outputList.add(add + randWord);
      wordSet.add(randWord);          // Add all possible words into wordSet
    }

    // [2]. Add in extra depending on request passed into command line
      // [A]. Using fluctuating remove and adds
    if (commandType.compareTo("scenario1") == 0)
      addScenarioOne(outputList, wordSet, numCommands);
    // [B]. Using a ratio of search, remove and add
    else if (commandType.compareTo("scenario2") == 0)
      addScenarioTwo(outputList, wordSet, numCommands);
    // [C]. Custom scenarios
    else
      addCustomScenario(outputList, wordSet, commands, commandType, numCommands);
    // [3]. Append print and quit onto the end
    outputList.add(print);
    outputList.add(quit);

    // [4]. Save all content into the specified file
    saveOutput(outputList, outFileName);
  }
}
