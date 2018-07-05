/* strtok example */
#include <stdio.h>
#include <string.h>

int main ()
{
    char point[] = "a?,?b,,??c#,,";
    char* lel;

    printf ("%s\n",point);

    lel = strtok(point, "?");
    printf ("%s\n",lel); //a

    lel = strtok(NULL, ",?");
    printf ("%s\n",lel); //b

    lel =  strtok(NULL, "?#,");
    printf ("%s\n",lel); //c

    char point2[] = "?a???b,,,#c";;
    char* lel2;

    printf ("%s\n",point2);

    lel2 = strtok(point2, "?");
    printf ("%s\n",lel2); //a

    lel2 = strtok(NULL, ",");
    printf ("%s\n",lel2); //??b

    lel2 =  strtok(NULL, "#,");
    printf ("%s\n",lel2); //c

    float f = 10.40;
    printf("%d\n", &f);

    char *str;

   /* Initial memory allocation */
   str = (char *) malloc(15);

   strcpy(str, "tutorialspoint");
   printf("String = %s,  Address = %u\n", str, str);

   /* Reallocating memory */
   str = (char *) realloc(str, 25);
   strcat(str, ".com");
   printf("String = %s,  Address = %u\n", str, str);

   free(str);

    /* Create an example variable capable of holding 50 characters */
    char example[] = "reddddd";

    /* Copy the string "TechOnTheNet.com knows strcpy!" into the example variable */
    strcpy (example, "TechOnTheNet.com knows strcpy!");

    /* Display the contents of the example variable to the screen */
    printf("%s\n", example);

    struct x { int a,b; struct x *p,*q; } zz;
    printf("%d\n", sizeof(struct x)); //16

    struct y { int **a,**b; struct y **p,**q; };
    printf("%d\n", sizeof(struct y)); //16

    struct example {
        int x,y,*p;
        char yaa;
        struct example *k,*q;
    };
    struct example an_example; /* Treating it like a normal variable type except with the addition of struct*/
    an_example.x = 10;
    printf("%d\n", sizeof(an_example)); //24


    char point3[] = "#,#,a$?ab$?,,#c";
    char* lel3;

    printf ("%s\n",point3);

    lel3 = strtok(point3, "#");
    printf ("%s\n",lel3); //,

    lel3 = strtok(NULL, ",$a");
    printf ("%s\n",lel3); //?

    lel3 =  strtok(NULL, "#,");
    printf ("%s\n",lel3); //b$?

    lel3 =  strtok(NULL, "#");
    printf ("%s\n",lel3); //,

    return 0;
}
