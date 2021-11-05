#include <stdio.h>

int main(void)
{
    int counter = 0;
    int num;
    int nums[1000];
    while (scanf("%d", &num) == 1 && counter<1000)
    {
        nums[counter] = num;
        counter++;
    }
    while (counter>0)
    {
        printf("%d\n",nums[counter-1]);
        counter--;
    }
    return 0;
}
