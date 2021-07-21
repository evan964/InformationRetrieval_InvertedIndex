/*Originaly our program is divided on three class files:
reTrievalMain (the main class where we create the inverted index)
InvertedIndex
Utils (some useful function) located in the first file (InvertedIndex)

the function BooleanRetrieval is in our implementation a method of the class InvertedIndex.
We execute it with the main after creating the index:
*/
public class retrievalMain {

    /**
     * Pass two arguments: 1. The path of the directory of documents, 2. The path of the boolean query file
     *
     * @param args
     */
    public static void main(String[] args) {

        InvertedIndex invertedIndex = new InvertedIndex(args[0]); //creation of the index

        for (String query : Utils.readLines(new File (args[1]))){

            Utils.printList(invertedIndex.BooleanRetrieval(query)); //we call the method with the query in parameter

        }
	}
}

//this method is originally contained in the class InvertedIndex
//we took it from his original file only to fit to the instruction as possible with our implementation
public TreeSet<String> BooleanRetrieval(String query){ //take query one after one

        String[] myTokens = Utils.splitBySpace(query);

        Stack<TreeSet<Integer>> myStack = new Stack<>(); //contains lists of internal nb of document, corresponding to the query

        boolean present = true;

		//term query analysis
        for (String single : myTokens) { //for each word of the query

            //in the case the queries are not case sensitive
           // if (!(single.equals("AND")||single.equals("OR")||single.equals("NOT"))){
              //  single = single.toLowerCase();
          //  }


            switch (single) {

                case "OR" :

                    if(myStack.size()<2){
                        break;
                    }
                    TreeSet<Integer> m_or1 = myStack.pop();
                    TreeSet<Integer> m_or2 = myStack.pop();

                    m_or2.addAll(m_or1);
                    myStack.push(m_or2);

                    break;


                case "AND" :
                    if(myStack.size()<2){
                        TreeSet<String> result = new TreeSet<>();
                        return result;
                    }
                    TreeSet<Integer> m_and1 = myStack.pop();
                    TreeSet<Integer> m_and2 = myStack.pop();


                    TreeSet<Integer> join = new TreeSet<>();


                    if (m_and1.size() >= m_and2.size()) {
                        for (Integer elm : m_and1) {
                            if (m_and2.contains(elm)) {
                                join.add(elm);
                            }
                        }
                        myStack.push(join);
                        break;
                    } else {
                        for (Integer elm : m_and2) {
                            if (m_and1.contains(elm)) {
                                join.add(elm);
                            }

                        }
                        myStack.push(join);
                        break;
                    }


                case "NOT" :

                    if((present==false)){
                        break;
                    }
                    if(myStack.size()<2){
                        TreeSet<Integer> forbidden_word = myStack.pop();
                        TreeSet<Integer> m_not = new TreeSet<Integer>();
                        for (int j=1;j<=myMap.size();j++){
                            m_not.add(j);
                        }


                        for (Integer elm : forbidden_word) {
                            if (m_not.contains(elm)) {
                                m_not.remove(elm);
                            }
                        }
                        myStack.push(m_not);
                        break;
                    }
                    TreeSet<Integer> forbidden_word = myStack.pop();
                    TreeSet<Integer> m_not = myStack.pop();

                    for (Integer elm : forbidden_word) {
                        if (m_not.contains(elm)) {
                            m_not.remove(elm);
                        }
                    }
                    myStack.push(m_not);
                    break;

                default:
                    String word1 = single;
                    TreeSet<Integer> indexWord1 = myHashMap.get(word1);
                    if(indexWord1==null){
                        present = false;
                        break;
                    }
                    myStack.push(indexWord1);
                    present = true;

            }
        }


        TreeSet<String> result = new TreeSet<>();
        if(myStack.isEmpty()){//in the case there is no document that fit
            return result;
        }
		
        TreeSet<Integer> myIndex = myStack.pop();

        for (Integer i: myIndex)
        {
            result.add(myMap.get(i)); //we get the orignial reference number
        }

        return result; //return a set with the document
    }