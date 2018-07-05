#include "p2lex.h"
#include <fstream>
#include <map>

void
usage(char *progname, string msg)
{
    cerr << "Error: " << msg << endl;
    cerr << "Usage is: " << progname << " [filename]" << endl;
    cerr << " specifying filename reads from that file; no filename reads standard input" << endl;
}

int
main( int argc, char *argv[] )
{
    istream *br;
    ifstream infile;

    if( argc == 1 )
        br = &cin;
    else if( argc == 2 ) {
        infile.open(argv[1]);
        if( infile.is_open() )
            br = &infile;
        else {
            usage(argv[0], "Cannot open " + string(argv[1]));
            return 1;
        }
    }
    else {
        usage(argv[0], "More than one file name was given");
        return 1;
    }

    Token t;
    string lexeme;
    string IDname;
    string IDvalue = "";
    map<string, string> setIDs;
    bool newline = false;
    bool invToken = false;
    bool validTree = true;
    int leaves = 0;

    bool debug = false;

    while (true) {
        t = getToken(br, lexeme);
        if(t == DONE){
            break;
        }
        if(newline == true){
            if(debug == true){cout << "--------------------------------" << endl;}
        }

        if(t == PRINT){
             if(debug == true){cout << "Rule 1 " << lexeme << endl;}
            newline = false;
            t = getToken(br, lexeme);
            if(t == ID){
                if(debug == true){cout << "Rule 2 " << lexeme << endl;}
                IDname = lexeme;
                map<string, string>::iterator i = setIDs.find(IDname);
                if(i != setIDs.end())
                {
                    //cout << "Found the word" << endl;// commented out since this assignment doesn't require any output other than the leaves;
                    //cout << setIDs[IDname] << endl;
                }
                else{
                    cout << "Error: ID not set" << endl;
                    validTree = false;
                    break;
                }
                t = getToken(br, lexeme);
                if(t == SC){
                    if(debug == true){cout << "Rule 3 " << lexeme << endl;}
                    newline = true;
                    IDvalue = "";
                    leaves++;
                    continue;
                }
                else{
                    cout << "Error: Expected Semicolon" << endl;
                    validTree = false;
                    break;
                }
            }
            else if(t == INT || t == STRING){
                IDvalue += lexeme;
                leaves++;
                t = getToken(br, lexeme);
                if(t == SC){
                    if(debug == true){cout << "Rule 3 " << lexeme << endl;}
                    newline = true;
                    IDvalue = "";
                    leaves++;
                    continue;
                }
                else if(t != PLUS && t != MINUS && t != STAR && t != SLASH){
                    if(debug == true){cout << "Rule 2 " << lexeme << endl;}
                    cout << "Error: Invalid token found, expected: Semicolon" << endl;
                    validTree = false;
                    break;
                }

                while(t == PLUS || t == MINUS || t == STAR || t == SLASH){
                    IDvalue += lexeme;
                    t = getToken(br, lexeme);
                    if(t == INT || t == STRING){
                        IDvalue += lexeme;
                        leaves++;
                        t = getToken(br, lexeme);
                        if(t == SC){
                            newline = true;
                            break;
                        }
                        if(t != PLUS && t != MINUS && t != STAR && t != SLASH){
                            invToken = true;
                            break;
                        }
                        continue;
                    }
                    else{
                        invToken = true;
                        break;
                    }
                }
                //Since we are only compiling here, we don't use cout << IDvalue;
                if(invToken == true){
                    if(debug == true){cout << "Rule 3 " << IDvalue << endl;}
                    cout << "Error: Invalid token found, expected: Semicolon" << endl;
                    invToken = false;
                    validTree = false;
                    break;
                }

                if(newline == true){
                    if(debug == true){cout << "Rule 2 " << IDvalue << endl;}
                    if(debug == true){cout << "Rule 3 " << lexeme << endl;}
                    IDvalue = "";
                    continue;
                }
                else{
                    cout << "Error: Invalid token found, expected: Semicolon" << endl;
                    newline = true;
                    validTree = false;
                    break;
                }
            }
        }
        //if I find set keyboard
        else if(t == SET){
            newline = false;
            if(debug == true){cout << "Rule 1 " << lexeme << endl;}
            t = getToken(br, lexeme);
            if(t == ID){
                IDname = lexeme;
                leaves++;
                if(debug == true){cout << "Rule 2 " << lexeme << endl;}
                t = getToken(br, lexeme);
                if(t == INT || t == STRING){
                    IDvalue += lexeme;
                    leaves++;
                    t = getToken(br, lexeme);
                    if(t == SC){
                        setIDs[IDname] = IDvalue;
                        newline = true;
                        if(debug == true){cout << "Rule 3 " << IDvalue << endl;}
                        if(debug == true){cout << "Rule 3 " << lexeme << endl;}
                        IDvalue = "";
                        continue;
                    }
                    else if(t != PLUS && t != MINUS && t != STAR && t != SLASH){
                        cout << "Error: Invalid token found, expected: Semicolon" << endl;
                        validTree = false;
                        break;
                    }
                    while(t == PLUS || t == MINUS || t == STAR || t == SLASH){
                        IDvalue += lexeme;
                        t = getToken(br, lexeme);
                        if(t == INT || t == STRING){
                            IDvalue += lexeme;
                            leaves++;

                            t = getToken(br, lexeme);
                            if(t == SC){
                                setIDs[IDname] = IDvalue;
                                newline = true;
                                break;
                            }
                            if(t != PLUS && t != MINUS && t != STAR && t != SLASH){
                                invToken = true;
                                break;
                            }
                            continue;
                        }
                        else{
                            invToken = true;
                            break;
                        }
                    }

                    if(invToken == true){
                        if(debug == true){cout << "Rule 3 " << IDvalue << endl;}
                        cout << "Error: Invalid token found, expected: Semicolon" << endl;
                        invToken = false;
                        validTree = false;
                        break;
                    }

                    if(newline == true){
                        if(debug == true){cout << "Rule 3 " << IDvalue << endl;}
                        if(debug == true){cout << "Rule 4 " << lexeme << endl;}
                        IDvalue = "";
                        continue;
                    }

                    //in assignment 4, you mentioned we actually evaluate the expression
                    //therefore for this assignment, the IDvalue is the actual expression
                    setIDs[IDname] = IDvalue;
                }
                else{
                    cout << "Error: No INT or STRING to set to ID" << endl;
                    validTree = false;
                    break;
                }
            }
            else{
                cout << "Error: Invalid ID";
                validTree = false;
                break;
            }
        }
        //didn't find either set or print
        else{
            cout << "Error: Couldn't find token SET or PRINT" << endl;
            validTree = false;
            break;
        }

        IDvalue = "";
    }
    if(debug == true){cout << "--------------------------------" << endl;}
    if(validTree == true){
        cout << "Total leaves: " << leaves;
    }
    else{
        cout << "Unable able to parse tree";
    }
    return 0;
}

//        checking all pairs in the map
//for (map<string, string>::iterator i = setIDs.begin(); i != setIDs.end(); i++) {
//  cout << i->first << " : " << i->second << endl;
//}
