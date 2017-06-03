Andriamilanto Tompoariniaina
Boucherie Thomas

Question 1:
	Ce programme créer un segment de memoire partagé à un key prédifini (le même pour toutes les instances du programme) et l'attache au processus.

Question 2:
	Il ne créer pas de processus fils et n'a donc pas besoin de partager la mémoire partagée avec des fils.

Question 3:
	Le programme ne detruit pas le segment de memoire partagé il le detache du procesus en cours.
	Si on appel le programme plusieurs fois un seul segment est créé et memorisé.

Question 4:
	Les differents procesus ecrient en meme temps sur le tableau et donc sur la memoire partagée ce qui nous donne une ecriture concurante entre les differents procesus.
