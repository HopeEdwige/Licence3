# Global vars
CFLAGS = -g -Wall

# Compilations
shell1: shell1.c
	gcc $(CFLAGS) -o shell1 shell1.c
	gdb shell1

shell2: shell2.c
	gcc $(CFLAGS) -o shell2 shell2.c
	gdb shell2

all: shell1.c shell2.c
	gcc $(CFLAGS) -o shell1 shell1.c
	gcc $(CFLAGS) -o shell2 shell2.c

clean:
	rm shell1
	rm shell2