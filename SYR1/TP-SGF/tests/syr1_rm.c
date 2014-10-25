#include<stdio.h>
#include<syr1_file.h>
#include<directory.h>

int main(int argc, char **argv) {
	if (argc!=2) {
		fprintf(stderr,"usage : syr1_rm nom\n");
		return -1;
	}
	int res = remove_entry(argv[1]);
	if (res< 0) {
		fprintf(stderr,"Error %d during syr1_rm\n",res);
	} 
	return 0;	
}


