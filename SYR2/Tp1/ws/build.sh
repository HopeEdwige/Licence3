#!/bin/sh
gcc -Wall -g -I../include -o ../obj/reverse.o ../src/reverse.c 
gdb ../obj/reverse.o
