#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main(int argc,
         char *argv[]) // argc = num argumentos, el segundo es un puntero a los argumentos en
{
    FILE *f = fopen("numbers.txt", "w");
    srand(time(NULL));
    if (f == NULL)
    {
        printf("Error opening file!\n");
        exit(1);
    }
    float r;
    int fin = rand() % 1300;
    for (int i = 1; i < 16; i++)
    {
        if (i == fin)

            fprintf(f, "fin\n");

        r = (rand() % 4000) / 100.0;
        fprintf(f, "%f\n", r);
    }
    fclose(f);
    return 0;
}
