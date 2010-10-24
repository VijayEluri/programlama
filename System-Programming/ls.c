#include "apue.h"
#include <dirent.h>
 
int
main(int argc, char *argv[])
{
        DIR  *dp;
        struct dirent   *dirp;
 
        if (argc != 2)
                exit(0); 
        if ((dp = opendir(argv[1])) == NULL)
                exit(0);
        while ((dirp = readdir(dp)) != NULL)
                printf("%s\n", dirp->d_name);
 
        closedir(dp);
        exit(0);
}