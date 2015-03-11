#include <stdio.h>
#include <stdlib.h>
#include "syscalls.h"

void print_string(char *str) {
	puts(str);
}

void print_char(char c )
{
	putchar(c);
}

void print_int(int v) {
	printf("%d\n", v);
}

char read_char() {
	return ((char) getchar());
}

void read_string(char *str, int limit) {
	fgets(str, limit, stdin);
}

int read_int() {
	int val;
	scanf("%d", &val);
	return val;
}