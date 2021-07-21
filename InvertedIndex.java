import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*Originaly our program is divided on three class files:
reTrievalMain (the main class where we create the inverted index)
InvertedIndex
Utils (some useful function)

for the submission we include all these files in one "InvertedIndex

if you want to compile to verify it works well, please tell us we'll give you the orinals files.
*/


public class retrievalMain {
	  /**
     * Pass two arguments: 1. The path of the directory of documents, 2. The path of the boolean query file
     *
     * @param args
     */
public static void main(String[] args) {

// we create the object invertedIndex with as parameter the path of the collection
//given in arguments in the IDE
InvertedIndex invertedIndex = new InvertedIndex(args[0]);

}
}



public class InvertedIndex {

    protected Map<Integer, String> myMap; //map between intern ID and real DocID
    protected HashMap<String, TreeSet<Integer>> myHashMap; //in this map each word is associated with a set of number wich correspond to the relevant doc


    public InvertedIndex(String arg) {
        File[] files = (new File(arg)).listFiles();
        myMap = new HashMap<>();
        myHashMap = new HashMap<>();
        
		
        int internalDocID =0;
        boolean space = false;
        for (int i = 0; i < files.length; i++){ //for each file
        

            File filePath = new File(files[i].getPath());
            List<String> fileLines = Utils.readLines(filePath); //split into a List each line

            for (String line : fileLines) {

                if(space==true){
                    space = false;
                    continue;
                }

                if (line.indexOf("<DOC>")!=-1){ //that means we enter in a new doc 
                    internalDocID++;
                    continue;
                }
                else if(line.indexOf("<DOCNO>")!=-1){
                    //extract the name of the current document and insert it into a map
                    String myTitle = Utils.substringBetween(line,"<DOCNO> ", " </DOCNO>");
                    myMap.put(internalDocID, myTitle);
                    continue;
                }
                else if ((line.indexOf("<TEXT>")!=-1)||(line.indexOf("</TEXT>")!=-1)) {
                    continue;
                }
                else if (line.indexOf("</DOC>")!=-1){
                    space = true;
                    continue;
                }

                String[] myLine = Utils.splitBySpace(line); //create a list of each word (myLine)

                for (String myWord : myLine) {
                    if (!myHashMap.containsKey(myWord)) //if the word hasn't been encounter yet
                    {
                        myHashMap.put(myWord, new TreeSet<>()); // we create a new key for him
                    }
                    myHashMap.get(myWord).add(internalDocID); // record data
                }
            }

        }

    }
	}
	
	
	

// these are some useful function that help the creation of the index
public class Utils {

    /**
     * Read all the file lines into a list of string
     * @param file - An example of file creation: new File ("path to file on disk")
     * @return - The lines of the file as list
     */
    public static List<String> readLines (File file) {
        List<String> fileLines = null;
        try {
            fileLines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error reading file: " + file.getAbsolutePath());
            e.printStackTrace();
            System.exit(1);
        }
        return fileLines;
    }

    /**
     * Split text by space and placed into an array of strings
     * exp: splitBySpace("a b c") -> [a,b,c]
     * @param row
     * @return
     */
    public static String[] splitBySpace (String row) { return row.split("\\s+");}

    /**
     * Gets the String that is nested in between two Strings. Only the first match is returned.
     * A null input String returns null. A null open/close returns null (no match). An empty ("") open and close returns an empty string.
     * @param str - the String containing the substring, may be null
     * @param open - the String before the substring, may be null
     * @param close - the String after the substring, may be null
     * @return the substring, null if no match
     * substringBetween("yabcz", "y", "z")   = "abc"
     */
    public static String substringBetween(String str, String open, String close) {
        if (str != null && open != null && close != null) {
            int start = str.indexOf(open);
            if (start != -1) {
                int end = str.indexOf(close, start + open.length());
                if (end != -1) {
                    return str.substring(start + open.length(), end);
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * Print the retrieved results sorted in an Alphabetical Order
     * @param docRetrievedList - A sorted set of retrieved document names
     */
    public static void printList (TreeSet<String> docRetrievedList) {
        for (String doc : docRetrievedList) {
            System.out.print(doc + " ");
        }
        System.out.println();
    }


}
