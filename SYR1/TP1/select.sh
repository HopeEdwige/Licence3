#!/usr/bin/sh

if
	[ $# -eq 3 ]
then
	if
		[ -n $1 -a -f $2 -a -f $3 ]
	then
		echo $1 | cat - $2 | ./prog2 > $3
	else
		echo "Les paramètres entrés sont non valides!"
	fi
else
	echo "select.sh i file1 file2"
	exit
fi
