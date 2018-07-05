
/* DHRUV PATEL    CS435  */

#include<iostream>
#include<stdio.h>
#include <string.h>
#include<string>
#include<fstream>
#include <functional>
#include <bitset>
#include <queue>
#include <vector>
#include <iomanip>
#include <stdlib.h>


using namespace std;

struct BinaryTree{
	int character;
	int left;
	int right;
	bool isleaf;
	int flag;
};


struct BinaryTree nodes[512];
int root_pointer;
char filename[25];

int convchartoint8596(char ch)
{
	int n;
	n = (int)ch;
	if(n<0)
	n=256+n;
	return n;

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




string convToString8596(int a, int v)
{
	std::string binary = std::bitset<8>(a).to_string(); //to binary
    //cout<<binary;
	if(v==0)
	return(binary);

	else
	{
		 binary.erase (0,(8-v));
		 return(binary);
	}
}


void readHuff8596()
{
	ifstream ifile;
	ifile.open(filename, ios::binary);
	if ( !ifile.is_open() )
	{
		cout<<"File not found!";
		exit(1);
	}

	ifile.seekg (0);

	ifile.read ((char*)&root_pointer, sizeof(int));

    for(int i=0;i<512; i++)
    ifile.read ((char*)&nodes[i], sizeof(struct BinaryTree));

    char ch;
    ofstream ofile;
    ofile.open("compressed.bin", ios::binary);

    ifile.read ((char*)&ch, sizeof(char));
    if((int)ch!=0)
    ofile.write ((char*)&ch, sizeof (char));

	streampos size;
	streampos size2;
  	char * memblock;

	size = ifile.tellg();
	ifile.seekg(0,ifstream::end);
	size2 = ifile.tellg();
	ifile.seekg(size);
	size2=size2-size;

	memblock = new char [size2];
	ifile.read (memblock, size2);
	ofile.write(memblock, size2);
	delete[] memblock;


	ofile.close();
	ifile.close();
}



void dellastchar8596()
{
	std::ifstream ifile ("output1.txt", std::ifstream::binary);
  	ofstream ofile(filename, std::ofstream::binary);
   // get length of file:
    ifile.seekg (0, ifile.end);
    long length = ifile.tellg();
    ifile.seekg (0, ifile.beg);


	length--;
    char * buffer = new char [length];
    ifile.read (buffer,length);
	ofile.write(buffer, length);

	delete[] buffer;
	ofile.close();
	ifile.close();
}

int main(int argc, char** argv)
{
	char *filename111 = argv[1];
	strcpy(filename, filename111);
	readHuff8596();

	string l = "";
	ifstream ifile;

	ifile.open("compressed.bin", ios::binary);
	ifile.seekg (0);

	ofstream ofile;
	ofile.open("output1.txt", ios::binary);

	char ch;
    int p;
    ifile.read ((char*)&ch, sizeof(char));
    p = convchartoint8596(ch);

    int a;
    ifile.read ((char*)&ch, sizeof(char));
    a = convchartoint8596(ch);
	l = l + convToString8596(a,p);

    while(!ifile.eof())
    {
    	ifile.read ((char*)&ch, sizeof(char));
    	a = convchartoint8596(ch);
    	l = l + convToString8596(a,0);
	}

    l.erase (l.size()-8,8);
    int count=0;
	int curr_pointer = root_pointer;

    for(int i=0; i<l.size(); i++)
    {
    	if(l[i]=='1')
    	{
    		curr_pointer = nodes[curr_pointer].right;
    	}
    	else
    	{
    		curr_pointer = nodes[curr_pointer].left;
    	}

    	if(nodes[curr_pointer].isleaf==true)
    	{
    		count++;
    		ch=nodes[curr_pointer].character;
    		ofile.write ((char*)&ch, sizeof (char));
    		curr_pointer=root_pointer;
    	}
    }

    ofile.close();
	ifile.close();
	remove(filename);
	int l1= strlen(filename);
	l1 = l1-4;
	filename[l1]='\0';
	dellastchar8596();
	remove("output1.txt");
	remove("compressed.bin");
	return 1;
}
