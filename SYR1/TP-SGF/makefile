NAME = dupond-dupont
CC = gcc 
CFLAGS = -g -Wall -I./include
LOG = resultats.txt
EXPORT_DIR = /dev/null

OBJ = \
	obj/openclose.o \
	obj/physical_io.o \
	obj/block_alloc.o \
	obj/directory.o \
	obj/file_alloc.o \
	obj/file_read.o \
	obj/file_write.o 

PROGS = \
	bin/test_read \
	bin/test_write \
	bin/simple_read \
	bin/simple_write 


INC_FILES = \
	include/physical_io.h \
	include/syr1_file.h \
	include/directory.h 

all: 
	@echo "==============================================================" 
	@echo "usage : make [progs] [modules] [listing] [run_test] [clean] [format]"
	@echo "  "
	@echo "  progs       : Compilation et EdL des programmes de tests"
	@echo "  modules     : Compilation des modules objets "
	@echo "  listing     : Génère un fichier pdf (imprimable) des sources"
	@echo "  clean       : efface les fichiers objets (*.o) et binaires"
	@echo "  init_fs     : regénère un systeme de fichier 'complet'"
	@echo "  inspect     : ouvre le disque virtuel avec l'éditeur héxadécimal" 
	@echo "  test_read   : lance le test de lecture avec simple_read"
	@echo "  test_write  : lance le test d'écriture avec simple_write"
	@echo "==============================================================" 
	@echo "  "

progs : $(PROGS)

modules : $(OBJ)

#
# les fichiers sources du TP à compléter (openclose.c, file_read.c, file_write.c)
#

obj/openclose.o : student/openclose.c $(INC_FILES)
	$(CC) -c  $(CFLAGS) -o obj/openclose.o student/openclose.c

obj/file_read.o : student/file_read.c $(INC_FILES)
	$(CC) -c $(CFLAGS) -o obj/file_read.o student/file_read.c

obj/file_write.o : student/file_write.c $(INC_FILES)
	$(CC) -c  $(CFLAGS) -o obj/file_write.o student/file_write.c

#
# modules de test
#

bin/simple_read: tests/simple_read.c $(OBJ) $(INC_FILES)
	$(CC) -o  bin/simple_read  $(CFLAGS) $(OBJ) tests/simple_read.c 

bin/simple_write : tests/simple_write.c $(OBJ) $(INC_FILES)
	$(CC) -o bin/simple_write $(CFLAGS) $(OBJ) tests/simple_write.c 

bin/test_read: tests/test_read.c $(OBJ) $(INC_FILES)
	$(CC) -o bin/test_read  $(CFLAGS) $(OBJ) tests/test_read.c 

bin/test_write : tests/test_write.c $(OBJ) $(INC_FILES)
	$(CC) -o bin/test_write $(CFLAGS) $(OBJ) tests/test_write.c 

#
# Regénération du SGF
#
init_fs: $(PROGS)
	cp /share/l3info/syr1/tp_sgf/disk.img ./

#
# Génération du listing en pdf
#
listing:
	@a2ps --tabsize=4 --medium=A4 --title=$(NAME) -o tp_sgf.ps student/openclose.c student/file_read.c student/file_write.c result_test_read.txt result_test_write.txt
	@ps2pdf -sPAPERSIZE=a4 tp_sgf.ps


inspect:
	ghex2 disk.img


# test de la lecture
test_read: $(PROGS)
	./bin/simple_read


# test de l'écriture 
test_write: $(PROGS)
	./bin/simple_write

#
# Nettoyage des modules
#
clean :
	\rm -f obj/file_read.o obj/file_write.o obj/openclose.o bin/test_* bin/simple_*

# Tous les tests
test_all: $(PROGS)
	./bin/test_read > result_test_read.txt
	./bin/test_write > result_test_write.txt