#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<syr1_file.h>
#include<physical_io.h>
#include<directory.h>

extern int count_allocation_unit();

int main(int argc, char **argv) {
	int j=0;
	dir_entry entry;
	int print_fat=0;
	int debug=0;
	int nb_file=0;
	if (argc==2) {
		if (strcmp(argv[1],"-l")==0) {
			print_fat=1;
		}
		if (strcmp(argv[1],"-d")==0) {
			print_fat=1;
			debug=1;
		}
	}

	printf("Directory contains : \n\n");
	printf("     Name     |  Size  |\n");
	printf("------------------------\n");

	for (j=DIRECTORY_START;j<=DIRECTORY_END;j++) {
		int res = read_block(j,(unsigned char*)(&entry));
		if (res!=1) {
			fprintf(stderr,"I/O Error code %d for block %d\n",res,j);
			return -1;
		}
		if (entry.free==USED_ENTRY) {
			if (print_fat==1) {
				printf("%12s\t%5d\t",entry.desc.name,entry.desc.size);
				int i;
				printf("[ ");
				for (i=0;i<MAX_BLOCK_PER_FILE-1;i++) {
					if (entry.desc.alloc[i]!=FREE_BLOCK)
						printf("%3d ",entry.desc.alloc[i]);
				}
				if (entry.desc.alloc[i]!=FREE_BLOCK)
					printf("%d ]\n",entry.desc.alloc[i]);
				else
				//	printf("%d ]\n",entry.desc.alloc[i])
				        printf("]\n");
			} else {
				printf("%12s\t%5d\n",entry.desc.name,entry.desc.size);
			}
			nb_file++;
		} else if (debug==1) {
			printf("DUMMY -- %12s\t%5d\t%c\n",entry.desc.name,entry.desc.size,entry.free);
		}
	}
	printf("------------------------\n");
	printf("Directory contains %d file(s)\n",nb_file);
	printf("Filesystem has %d available blocks\n",count_allocation_unit());

	return 0;
}
