# Global vars
CFLAGS = -g -Wall
VGOPTIONS = --leak-check=full --show-leak-kinds=all --trace-children=yes

# Compilations
all: shell1.c shell2.c shell3.c
	gcc $(CFLAGS) -o shell1 shell1.c
	gcc $(CFLAGS) -o shell2 shell2.c
	gcc $(CFLAGS) -o shell3 shell3.c

shell1: shell1.c
	gcc $(CFLAGS) -o shell1 shell1.c

shell2: shell2.c
	gcc $(CFLAGS) -o shell2 shell2.c

shell3: shell3.c
	gcc $(CFLAGS) -o shell3 shell3.c


# Clean the workspace
clean:
	rm shell1
	rm shell2
	rm shell3


# Test programs
test_shell1:
	gdb ./shell1
	valgrind $(VGOPTIONS) ./shell1

test_shell2:
	gdb ./shell2
	valgrind $(VGOPTIONS) ./shell2

test_shell3:
	gdb ./shell3
	valgrind $(VGOPTIONS) ./shell3
