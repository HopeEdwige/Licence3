# Global vars
CFLAGS = -g -Wall


# Compilations
all: audioserver.c audioclient.c
	gcc $(CFLAGS)  -I ./ -o audioserver audioserver.c
	gcc $(CFLAGS)  -I ./ -o audioclient audioclient.c


# Clean the workspace
clean:
	rm audioserver audioclient


# Test programs
test:
	./audioserver && ./audioclient