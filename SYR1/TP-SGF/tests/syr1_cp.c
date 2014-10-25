#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<physical_io.h>
#include<syr1_file.h>
#include<directory.h>

int main(int argc, char **argv) {
	if (argc!=3) {
		fprintf(stderr,"usage : syr1_cp nom1 nom2\n");
		return -1;
	} 

	char* nom1=argv[1];
	char* nom2=argv[2];

	FILE* file2= fopen(nom1,"r");
	SYR1_FILE* file1= syr1_fopen(nom2,"w");

	if (file2==NULL)  {
		fprintf(stderr,"Impossible d'ouvrir le fichier local %s en lecture\n",nom1);
		return -1;
	}

	if (file1==NULL) {
		fprintf(stderr,"Impossible d'ouvrir le fichier du mini-SGF %s en écriture \n",nom2);
		return -1;
	}

	int res=0;
	while (res!=EOF) {
		res = getc(file2);
		if (res !=EOF) 	syr1_putc(res,file1);
	}

	fflush(stdout);  
	syr1_fclose(file1);
	fclose(file2);
	printf("Number of physical IOs involved : %d\n",physical_io_count);  
	return 0;
}
