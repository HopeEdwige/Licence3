#include<stdio.h>
#include<syr1_file.h>
#include<directory.h>

int main(int argc, char **argv) {
	if (argc!=2) {
		fprintf(stderr,"usage : syr1_cat nom\n");
		return -1;
	} 
	char* nom=argv[1];
	SYR1_FILE* file= syr1_fopen(nom,"r");

	if (file!=NULL) {
		if (verbose) printf("File %s opened successfully\n",nom);
		int res =0;
		while(res>=0) {
			res = syr1_getc(file);
			if (res>=0) printf("%c",(res));
		}
		return syr1_fclose(file);
	} else {
		fprintf(stderr,"Le fichier %s n'existe pas sur le disque disk.img\n",nom);
		return -1;
	}  
}

