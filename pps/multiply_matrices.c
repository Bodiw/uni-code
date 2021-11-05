#include <stdio.h>

int num;

int main(void)
{
    int m, n, p;
    int sum = 0;

    scanf("%d", &m);
    scanf("%d", &n);
    scanf("%d", &p);

    float m1[m][n], m2[n][p], res[m][p];

    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            scanf("%f", &m1[i][j]);

    for (int i = 0; i < n; i++)
        for (int j = 0; j < p; j++)
            scanf("%f", &m2[i][j]);

    for (int i = 0; i < m; i++)
    {
        for (int j = 0; j < p; j++)
        {
            for (int k = 0; k < n; k++)
            {
                sum += m1[i][k] * m2[k][j];
            }
            res[i][j] = sum;
            sum = 0;
        }
    }
    for (int i = 0; i < m; i++)
    {
        for (int j = 0; j < p; j++)
        {
            printf("%.1f ", res[i][j]);
        }
        printf("\n");
    }
}
