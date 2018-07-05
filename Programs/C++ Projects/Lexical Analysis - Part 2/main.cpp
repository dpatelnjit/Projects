#include "p2lex.h"
#include <fstream>
#include <map>
#include <algorithm>
#include <string>
#include <stdlib.h>
#include <sstream>

#define SSTR( x ) dynamic_cast< std::ostringstream & >( \
        ( std::ostringstream() << std::dec << x ) ).str()
void
usage(char *progname, string msg)
{
    cerr << "Error: " << msg << endl;
    cerr << "Usage is: " << progname << " [filename]" << endl;
    cerr << " specifying filename reads from that file; no filename reads standard input" << endl;
}

string removestr (string input, std::string rem )
{
    int i = 0;
    int appearances = 0;
    for ( i = input.find ( rem, 0 ); i != std::string::npos;
          i = input.find ( rem, i ) )
    {
      appearances++;
      input.erase ( i, rem.length() );
      i++;
    }

    return input;
}

string analyze(string x){
    x.erase(remove( x.begin(), x.end(), '\"' ),x.end());
    string newString = "";

    string stradd;
    string strsub;
    string strtimes;
    string strdiv;
    string firstNum;
    string nextNum;
    string nextNumStr;
    bool numOperator = false;
    bool str = false;
    bool num = false;
    bool add = false;
    bool sub = false;
    bool div = false;
    bool divstr = false;
    bool times = false;

    if(isdigit(x[0])){
        num = true;
    }
    else{
        str = true;
    }

    for(int i = 0; i < x.length(); i++)
    {
        if(str == true){
            if(x[i] == '\+' ){
                add = true;
                continue;
            }

            if(x[i] == '\-' ){
                sub = true;
                continue;
            }

            if(x[i] == '\*' ){
                times = true;
                continue;
            }

            if(x[i] == '\/' ){
                div = true;
                continue;
            }

            if(add == true){
                stradd += x[i];
                continue;
            }

            if(sub == true){
                strsub += x[i];
                continue;
            }

            if(times == true){
                strtimes += x[i];
                continue;
            }

            if(div == true){
                strdiv += x[i];
                continue;
            }

            newString += x[i];
        }
        else if(num == true){
            if(x[i] == '\+' || x[i] == '\-' || x[i] == '\/' || x[i] == '\*'  ){
                numOperator = true;
                if(x[i] == '\+'){add = true;}
                if(x[i] == '\-'){sub = true;}
                if(x[i] == '\/'){div = true;}
                if(x[i] == '\*'){times = true;}
                continue;
            }

            if(numOperator == true){
                if(isalpha(x[i])){
                    divstr = true;
                    nextNumStr += x[i];
                    continue;
                }
                nextNum += x[i];
                continue;
            }

            firstNum += x[i];
        }
    }

    if(str == true){

        const char* my_c_string = strtimes.c_str();
        int timestoadd = atoi(my_c_string) - 1;
        string tempstrtimes = newString;

        const char* my_c_string2 = strdiv.c_str();
        int substrdiv = atoi(my_c_string2);

        if(add == true){
            if(isdigit(stradd[0])){ newString = "Invalid Combination";}
            else{
                newString += stradd;
            }
        }
        else if(sub == true){
            if(isdigit(strsub[0])){newString = "Invalid Combination";}
            else{
                if (newString.find(strsub) != string::npos) {
                    newString = removestr(newString, strsub);
                }
            }
        }
        else if(times == true){
            if(isalpha(strtimes[0])){newString = "Invalid Combination";}
            else{
                for(int i = 0; i < timestoadd; i++){
                    newString += tempstrtimes;
                }
            }
        }
        else if(div == true){
            if(isalpha(strdiv[0])){newString = "Invalid Combination";}
            else{
                newString = newString.substr (0,substrdiv);
            }
        }
    }

    else if(num == true){

        const char* my_c_string = firstNum.c_str();
        int num1 = atoi(my_c_string);
        const char* my_c_string2 = nextNum.c_str();
        int num2 = atoi(my_c_string2);

        if(add == true){
            if(nextNumStr.length() > 0){newString = "Invalid Combination";}
            else{
            int calc = num1 + num2;
            newString = SSTR( calc );
            }
        }
        else if(sub == true){
            if(nextNumStr.length() > 0){newString = "Invalid Combination";}
            else{
            int calc = num1 - num2;
            newString = SSTR( calc );
            }
        }
        else if(times == true){
            if(nextNumStr.length() > 0){newString = "Invalid Combination";}
            else{
            int calc = num1 * num2;
            newString = SSTR( calc );
            }
        }
        else if(divstr == true){
            newString = nextNumStr.substr (nextNumStr.length()-num1,nextNumStr.length());
        }
        else if(div == true){
            int calc = num1 / num2;
            newString = SSTR( calc );
        }
        else{
            newString = firstNum;
        }
    }

    return newString;
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
                    cout << analyze(setIDs[IDname]) << endl;
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
                    cout << analyze(IDvalue) << endl;
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
                    cout << analyze(IDvalue) << endl;
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
