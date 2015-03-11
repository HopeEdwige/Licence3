# Global vars
CFLAGS = -g -Wall
VGOPTIONS = --leak-check=full --show-leak-kinds=all --trace-children=yes

# Compilations
all: tp4.c nettoie.c
	gcc $(CFLAGS) -o tp4 tp4.c
	gcc $(CFLAGS) -o nettoie nettoie.c


# Clean the workspace
clean:
	rm tp4 nettoie


# Test programs
test:
	gdb ./tp4
	valgrind $(VGOPTIONS) ./tp4