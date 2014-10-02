CFLAGS = -Wall -g

#
# Edition de lien
#
bin/test_list : obj/list.o obj/test_list.o
	gcc -o bin/test_list obj/list.o obj/test_list.o


#
# Modules objets du TP Listes
#

obj/list.o : src/list.c ./include/list.h
	gcc $(CFLAGS) -o obj/list.o -c src/list.c -I ./include
	

obj/test_list.o : src/test_list.c ./include/list.h
	gcc $(CFLAGS) -o obj/test_list.o -c src/test_list.c -I ./include

#
# Génération du listing en pdf
#
listing :
	a2ps -o listing.ps include/list.h src/list.c src/test_list.c
	ps2pdf listing.ps
	rm listing.ps


#
# Nettoyage des modules
#
clean :
	rm obj/*.o
	rm bin/test_list