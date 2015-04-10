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
server: $(BINDIR)audioserver
	$(BINDIR)audioserver

# Run the client
client: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav

client-mono: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav mono

client-echo: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav echo

client-volume-mute: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav volume 0

client-volume-same: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav volume 1

client-volume-up: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav volume 3

client-volume-highest: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav volume 5

client-speed-same: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav speed 1

client-speed-twice: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav speed 2

client-speed-up: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav speed 3

client-speed-fastest: $(BINDIR)audioclient
	padsp $(BINDIR)audioclient localhost test.wav speed 4