# Global vars
CFLAGS = -g -Wall


# Compilations
all: sysprog-audio-1.5/audio.c lecteur.c
	gcc $(CFLAGS) -c -I sysprog-audio-1.5 -o audio.o sysprog-audio-1.5/audio.c
	gcc $(CFLAGS) -o lecteur lecteur.c audio.o


# Clean the workspace
clean:
	rm audio.o lecteur


# Test programs
test:
	padsp ./lecteur