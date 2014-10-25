#include<physical_io.h>
#include<directory.h>
#include<stdio.h>

unsigned char buffer[IO_BLOCK_SIZE];
dir_entry entry;

int main() {
	int i;
	// Creating disk image file
	FILE *file=fopen(IMAGE,"w");
	if (file==NULL) {
		fprintf(stderr,"coudl not open file %s in create mode.\n",IMAGE); 
 		return -1;
 	} 
	printf("0) Creating an empty disk image file\n"); 
	for (i=0;i<IO_BLOCK_SIZE;i++) 
		buffer[i]=0;
	for (i=0;i<NB_DISK_BLOCKS;i++) {
		int res = fwrite(buffer,IO_BLOCK_SIZE,1,file);
 		if (res!=1) {
			fprintf(stderr,"Error during step 0 (%d data wrote).\n",res); 
 			return -1;
 		}
	}
	printf("%d blocks written\n",NB_DISK_BLOCKS); 

	// Initializing free blocks tables

	printf("1) Initializing free/used data blocks table\n"); 
 	for (i=0;i<IO_BLOCK_SIZE;i++) 
		buffer[i]=FREE_BLOCK;

 	for (i=ALLOCTABLE_START;i<=ALLOCTABLE_END;i++) {
		printf("."); 
 		int res = write_block(i,buffer);
 		if (res!=1) {
			fprintf(stderr,"Error during step 1 (error code %d).\n",res); 
 			return -1;
 		}
 	} 
	printf("\n%d blocks written\n",ALLOCTABLE_SIZE); 

	// Initializing directory entries

	printf("2) Initializing directory entries\n"); 
	entry.free=FREE_ENTRY;
	entry.desc.size=-1;
 	for (i=0;i<=MAX_BLOCK_PER_FILE;i++) {
 		entry.desc.alloc[i]=FREE_BLOCK;
 	}
 	for (i=DIRECTORY_START;i<=DIRECTORY_END;i++) {
		sprintf(entry.desc.name,"EMPTY_ENTRY %d",i);
 		int res = write_block(i,(unsigned char *)(&entry));
 		if (res!=1) {
			fprintf(stderr,"Error during step 2 (error code %d).\n",res); 
 			return -1;
 		}
		printf("."); 
 	} 
	printf("\n%d entry initialized\n",DIRECTORY_SIZE); 

	// Initializing data blocks 

	printf("3) Initializing data block content\n"); 
 	for (i=0;i<IO_BLOCK_SIZE;i++) 
		buffer[i]=0xFF;
 	for (i=DATABLOCKS_START;i<=DATABLOCKS_END;i++) {
 		int res = write_block(i,buffer);
 		if (res<0) {
			fprintf(stderr,"Error during step 3 (error code %d).\n",res); 
 			return -1;
 		}
		printf("."); 
 	} 
	printf("\n%d data blocks initialized\n",DATABLOCKS_SIZE); 
	return 0;
}
