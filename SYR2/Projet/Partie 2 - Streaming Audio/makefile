# Global vars
CFLAGS = -g -Wall
SRCDIR = src/
BINDIR = bin/
INCDIR = includes/


# Compilations
all: $(SRCDIR)audioserver.c $(SRCDIR)audioclient.c $(SRCDIR)packethelper.c $(SRCDIR)audio.c
	gcc $(CFLAGS)  -c -I $(INCDIR) -o $(BINDIR)audio.o $(SRCDIR)audio.c
	gcc $(CFLAGS)  -c -I $(INCDIR) -o $(BINDIR)packethelper.o $(SRCDIR)packethelper.c
	gcc $(CFLAGS)  -I $(INCDIR) -o $(BINDIR)audioserver $(SRCDIR)audioserver.c $(BINDIR)packethelper.o $(BINDIR)audio.o
	gcc $(CFLAGS)  -I $(INCDIR) -o $(BINDIR)audioclient $(SRCDIR)audioclient.c $(BINDIR)packethelper.o $(BINDIR)audio.o


# Clean the workspace
clean:
	rm $(BINDIR)audioserver $(BINDIR)audioclient $(BINDIR)packethelper.o $(BINDIR)audio.o