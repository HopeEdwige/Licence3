# Global vars
CFLAGS = -g -Wall
SRCDIR = src/
BINDIR = bin/
INCDIR = includes/


# Compilations
all: $(BINDIR)audioserver $(BINDIR)audioclient


# The components of the compilation
$(BINDIR)audio.o: $(SRCDIR)audio.c $(INCDIR)audio.h
	gcc $(CFLAGS)  -c -I $(INCDIR) -o $(BINDIR)audio.o $(SRCDIR)audio.c

$(BINDIR)packethelper.o: $(SRCDIR)packethelper.c $(INCDIR)packethelper.h
	gcc $(CFLAGS)  -c -I $(INCDIR) -o $(BINDIR)packethelper.o $(SRCDIR)packethelper.c

$(BINDIR)audioserver: $(SRCDIR)audioserver.c $(INCDIR)clientserver.h $(BINDIR)audio.o $(BINDIR)packethelper.o
	gcc $(CFLAGS)  -I $(INCDIR) -o $(BINDIR)audioserver $(SRCDIR)audioserver.c $(BINDIR)packethelper.o $(BINDIR)audio.o

$(BINDIR)audioclient: $(SRCDIR)audioclient.c $(INCDIR)clientserver.h $(BINDIR)audio.o $(BINDIR)packethelper.o
	gcc $(CFLAGS)  -I $(INCDIR) -o $(BINDIR)audioclient $(SRCDIR)audioclient.c $(BINDIR)packethelper.o $(BINDIR)audio.o



# Clean the workspace
clean:
	rm $(BINDIR)audioserver $(BINDIR)audioclient $(BINDIR)packethelper.o $(BINDIR)audio.o


# Run the server (and free the semaphore in the end)
run: all
	$(BINDIR)audioserver
