# Global vars
CFLAGS = -g -Wall


# Compilations
all: audio.c lecteur.c audioserver.c
	gcc $(CFLAGS) -c -I ./ -o audio.o audio.c
	gcc $(CFLAGS) -o lecteur lecteur.c audio.o
	gcc $(CFLAGS)  -I ./ -o audioserver audioserver.c


# Clean the workspace
clean:
	rm audio.o lecteur audioserver


# Test programs
test-lecteur:
	padsp ./lecteur

test-server:
	./audioserver