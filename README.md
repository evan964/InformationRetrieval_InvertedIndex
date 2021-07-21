# InformationRetrieval_InvertedIndex

In this program, I built Inverted index for the AP collection and retrieve documents using Boolean queries.

AP_Coll_Parsed– a directory containing files of 242,918 documents from the AP
dataset in a trectext format. Each file contains several documents, separated by
DOC tags. Each document has a unique document ID, specified by the
<DOCNO> tag, which comes right after the opening DOC tag. The text of the
document to be indexed is contained within TEXT tags. (Several documents
contain several <EXT tags.)
Note: The text was lowercased, and the following punctuation marks were
removed:
  
!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
  
Here is an example document:
 
![image](https://user-images.githubusercontent.com/87755953/126476382-c2e08ff3-48a3-4b82-9624-ec3ae841dd8d.png)

  
## Boolean Query Structure
  
A Boolean Query is composed of terms and the following logical operators:
AND, OR, NOT.
We Consider an example Boolean Query:
southwest Airlines OR Africa NOT
Queries are represented using the Reverse Polish Notation.
See the link for more details: https://www.programcreek.com/2012/12/leetcodeevaluate-reverse-polish-notation/
For this query, we want to retrieve all the documents containing the term “southwest”
or “Airlines” but not the term “Africa”.


## Inverted Index Building
The class that creates an inverted index for the APcollection. It takes the AP corpus as input and produce an index of all the
words.
![image](https://user-images.githubusercontent.com/87755953/126476798-08a9f95d-10ef-4eb4-af97-b14b6142488e.png)
  
  ## Boolean Retrieval Model Building
  
  The second function retrieves a set of matching documents that given an inverted index and a Boolean query.
![image](https://user-images.githubusercontent.com/87755953/126477073-544c10da-e6e2-49db-95bd-77e0d1fd1854.png)

