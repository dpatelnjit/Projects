
/* DHRUV PATEL    CS435  */

#include<iostream>
#include<stdio.h>
#include <string.h>
#include<fstream>
#include <stdlib.h>
#include <functional>
#include <queue>
#include <bitset>
#include <vector>
#include<math.h>
#include <iomanip>


using namespace std;

int curr_count=256;
int root_pointer;

char filename[30];

struct Frequency{
	int freq;
	int character;
	char bitcode;
	int flag;
	int childof;
	string bitstring;
};

struct Frequency f[512];

struct BinaryTree{
	int character;
	int left;
	int right;
	bool isleaf;
	int flag;
};


struct BinaryTree nodes[512];

void initfreq8596()

{
	for (int i =0; i<256; i++)
	{
		f[i].freq=0;
		f[i].character = i;
		f[i].bitcode='\0';
		f[i].bitstring="";
		f[i].flag=0;
		f[i].childof=-1;
	}
}

void displayfreq8596()
{
	char v;
	for(int i=0; i<256; i++)
	{
		if(f[i].freq==0)
		continue;
		v=(char)f[i].character;
		cout<<v<< " ";
		cout<<f[i].freq;
		cout<<"\n";
	}
}

void calcfreq8596(char ch)
{
	int character;

	character = (int) ch;
	if(character<0)
	character = 256+character;
	f[character].freq++;
}


void readfile8596()
{
	char ch;
	ifstream ifile;
	ifile.open(filename , ios::binary);

	if ( !ifile.is_open() )
	{
		cout<<"File not found!";
		exit(1);
	}

	while(!ifile.eof())
	{
	ifile.read ((char*)&ch, sizeof(char));

	calcfreq8596(ch);
	}
	ifile.close();
}

/*
Priority Queue
*/

class CompareF {
    public:
    bool operator()(Frequency& t1, Frequency& t2) // Returns true if t1 is earlier than t2
    {
       if (t1.freq < t2.freq) return false;
       	return true;
    }
};

template<typename T> void print_queue(T& q) {
    while(!q.empty()) {
    	Frequency ff = q.top();
        cout << ff.character<<" ";
        cout<<ff.freq<<endl;
        q.pop();
    }
    std::cout << '\n';
}

/*
End of Priority Queue
*/

template<typename T> void createtree8596(T& pq)
{
	while( pq.size() > 1)
	{

		Frequency ff1 = pq.top();
        pq.pop();
	    Frequency ff2 = pq.top();
        pq.pop();


        f[curr_count];
        f[curr_count].freq= ff2.freq + ff1.freq;
        f[curr_count].character=curr_count;


        int f1=ff1.character;
        int f2=ff2.character;


		f[f2].bitcode='1';
        f[f1].bitcode='0';
        f[f1].childof=curr_count;
        f[f2].childof=curr_count;

     	pq.push(f[curr_count]);
     	curr_count++;
     }

    Frequency Root = pq.top();
    pq.pop();
    f[Root.character].flag=1;
}


string rev (string str)
{
	string str1;

    for (std::string::reverse_iterator rit=str.rbegin(); rit!=str.rend(); ++rit)
    {
        str1 =str1+ *rit;
    }
    return str1;
}


void makedictionary8596()
{
	for(int i=0;i<256;i++)
	{
		if(f[i].freq!=0)
		{
			Frequency Curr = f[i];
			while(Curr.flag!=1)
			{
				f[i].bitstring = f[i].bitstring + Curr.bitcode;
				Curr = f[Curr.childof];
			}
			f[i].bitstring = rev(f[i].bitstring);
		}
	}
}


char convertIntToChar8596(int n)
{
	char ch = (char)n;
	return ch;
}


void createHuffNode8596(int val, int left, int right, bool leaf)
{
	nodes[val].character = val;
	if(left!=-1)
	nodes[val].left = left;
	if(right!=-1)
	nodes[val].right = right;
	nodes[val].isleaf = leaf;
	nodes[val].flag = 1;
}

void createHuffTree8596()
{
	for(int i=0;i<256;i++)
	{
		if(f[i].freq!=0)
		{
			createHuffNode8596(i, -1, -1, true);
			Frequency Curr = f[i];
			while(Curr.flag!=1)
			{
				if(Curr.bitcode=='1')
				{
					createHuffNode8596(f[Curr.childof].character, -1, Curr.character, false);
				}
				else
				{
					createHuffNode8596(f[Curr.childof].character, Curr.character, -1, false);
				}
				Curr = f[Curr.childof];
			}
			createHuffNode8596(Curr.character, -1, -1, false);
			root_pointer=Curr.character;
		}
	}
}

void printHuffTree8596()
{
	for(int i = 0; i<512; i++)
	{
		if(nodes[i].character!=0)
		{
            cout<<endl<<nodes[i].character<<"\t"<<nodes[i].left<<"\t"<<nodes[i].right;
		}
	}
}

void printdictionary8596()
{
	cout<<endl<<"This is the dictionary"<<endl;
	for(int i=0;i<256;i++)
	{
		if(f[i].freq!=0)
		{
			cout<<endl<<(char)f[i].character<<" ";
			cout<<f[i].bitstring;
		}
	}
}


int convCharToInt8596(char ch)
{
	if(ch=='1')
	return 1;
	else
	return 0;
}

int convToInt8596(int x)
{
	int ans =0;
    for(int i=0; i<8; i++)
    {
    	int digit = x%10;
    	x = x/10;
    	ans = ans + (digit * (pow(2, i)));
    }
    return ans;
}


void createactualcompressedfile8596(string s)
{
	char ch;
	ifstream ifile;
	ifile.open("FinalString.txt");

	ofstream ofile;
	ofile.open("compressed.bin",  ios::binary);

	while(!ifile.eof())
	{
        string line;
        getline( ifile, line );

        int p=line.size()%8;
        ch = convertIntToChar8596(p);
        ofile.write ((char*)&ch, sizeof (char));
        int i;

        for(i=0;i<(8-p);i++)
        {
            line = "0" + line;
        }

        i=0;
        string l = line;
        while(i<line.size())
        {
            int curr_pointer = root_pointer;
            int a=0;
            for(int j=0;j<8;j++)
            {
                int strchar = convCharToInt8596(line[i]);
                a= a*10 + strchar;
                i++;
            }
            a =  convToInt8596(a);
            ch=convertIntToChar8596(a);
            ofile.write ((char*)&ch, sizeof (char));
        }
	}
	ofile.close();
}

void createcompressedfile8596()
{
	char ch;
	ifstream ifile;
	ifile.open(filename, ios::binary );

	ofstream ofile;
	ofile.open("FinalString.txt");

	while(!ifile.eof())
	{
		ifile.read ((char*)&ch, sizeof(char));
		int v;
		string s;
		v=(int)ch;
		if(v<0)
		v=256+v;
		s=f[v].bitstring;
		ofile<<s;
	}
	ifile.close();
	ofile.close();

	createactualcompressedfile8596("FinalString.txt");
}


void writeHuff8596()
{
	ofstream ofile;
	ofile.open("dict.bin",  ios::binary);

	ofile.write ((char*)&root_pointer, sizeof (int));

	for(int i=0;i<512;i++)
	{
		ofile.write ((char*)&nodes[i], sizeof (BinaryTree));
	}
	ofile.close();
}



void deletefiles8596()
{
	streampos size;
  	char * memblock;

  	streampos size2;
  	char * memblock2;

	char str1[35];
	char ch;

	strcpy(str1, filename);
	strcat (str1,".huf");

	ofstream ofile;
	ofile.open(str1, ios::binary);

	ifstream ifile1("dict.bin", ios::in | ios::binary);
	ifstream ifile2("compressed.bin", ios::in | ios::binary);

	ifile1.seekg(0,ifstream::end);
	size = ifile1.tellg();
    memblock = new char [size];
    ifile1.seekg (0, ios::beg);
    ifile1.read (memblock, size);
    ifile1.close();

	ofile.write(memblock, size);

	delete[] memblock;

	ifile2.seekg(0,ifstream::end);
	size2 = ifile2.tellg();
    memblock2 = new char [size2];
    ifile2.seekg (0, ios::beg);
    ifile2.read (memblock2, size2);
    ifile2.close();


	ofile.write(memblock2, size2);

	delete[] memblock2;

	ofile.close();


	remove(filename);
	remove("FinalString.txt");
	remove("dict.bin");
	remove("compressed1.bin");
}

int main(int argc, char** argv)
{
    char *filename111 = argv[1];
    strcpy(filename, filename111);
    initfreq8596();

    readfile8596();

    priority_queue<Frequency, vector<Frequency>, CompareF> pq;

    for(int n=0; n<256; n++)
    {
        if(f[n].freq!=0)
        pq.push(f[n]);
    }

    createtree8596(pq);
    makedictionary8596();

    createHuffTree8596();
    createcompressedfile8596();
    writeHuff8596();

    deletefiles8596();

    return 0;
}
