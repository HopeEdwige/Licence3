# Global vars
CFLAGS = -g -Wall


# Compilations
all: audio.c lecteur.c
	gcc $(CFLAGS) -c -I ./ -o audio.o audio.c
	gcc $(CFLAGS) -o lecteur lecteur.c audio.o


# Clean the workspace
clean:
	rm audio.o lecteur


# Test programs
test:
	padsp ./lecteur
