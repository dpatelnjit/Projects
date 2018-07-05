#include <iostream>
#include <algorithm>
#include <cctype>
#include <string>
#include <fstream>

using namespace std;

string to_lower(string s) {
    string newString = "";
    for(int i = 0; i < s.length(); i++)
		newString += tolower(s[i]);
	return newString;
};

string to_upper(string s) {
    string newString = "";
    for(int i = 0; i < s.length(); i++)
		newString += toupper(s[i]);
	return newString;
};

bool is_vowel(char c) {
    c = tolower(c);
    return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
}

bool is_upper(string s) {
    for(int i = 0; i < s.length(); i++){
        if(isupper(s[i]))
           continue;
        else
            return false;
    }
    return true;
}

int main(int argc, char* argv[])
{
    if(argc != 3){   //Invalid command line arguments
        cout << "Put 2 command line arguments";
        return 0;
    }

    string adj = argv[1];
    char* filename = argv[2];
    ifstream readFile;
    readFile.open(filename);

    string currWord;
    string nextWord;
    string lowercurrWord;
    string lowernextWord;
    string newMemo = "";

    if(!readFile.is_open()){
        cout << "Error when opening file: " << filename;
        return 0;
    }

    while(readFile >> currWord){

        if(newMemo != "")          //add a empty space for the new article/noun
            newMemo+= " ";

        lowercurrWord = to_lower(currWord);
        if(lowercurrWord == "a" || lowercurrWord == "an" || lowercurrWord == "the"){
            readFile >> nextWord;
            if (readFile.peek() == EOF) { //incase the last word is an article
                if  (readFile.eof()){
                    newMemo += currWord;
                    break;
                }
            }
            lowercurrWord = to_lower(currWord);
            if(isalpha(nextWord[0])){
                if(islower(nextWord[0]))  //noun[0] lowercase then adj lowercase
                    adj = to_lower(adj);

                else if(is_upper(nextWord)) //noun uppercase then adj uppercase
                    adj = to_upper(adj);

                else{                        //only capitalize first letter in adj
                    adj[0] = toupper(adj[0]);
                    for(int i = 1; i < adj.length();i++)
                        adj[i] = tolower(adj[i]);
                }

                if(is_vowel(nextWord[0]) && !is_vowel(adj[0]) && lowercurrWord == "an"){ ////if noun is a vowel and adj is not and word is "an" then switch to "a"
                    if(isupper(currWord[0]))
                        currWord = "A";
                    else
                        currWord = "a";
                }

                else if(lowercurrWord == "a" && is_vowel(adj[0])){    //if adj starts with "a" then switch to "an"
                    if(isupper(currWord[0]))
                        currWord = "An";
                    else
                        currWord = "an";
                }
                newMemo += currWord + " " + adj + " " + nextWord;
            }

            else{
                newMemo += currWord;
                newMemo += nextWord;
            }
        }
        else
            newMemo += currWord;
    }
    cout << newMemo;

    readFile.close();
    return 0;

}
