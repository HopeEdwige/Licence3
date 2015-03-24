			.equ PRINT_INT,		1
			.equ PRINT_STRING,	4
			.equ READ_INT,		5
			.equ EXIT,		10

			.text

			####################################
			# Fonction rechercheDichotomique() #
			####################################
rechercheDichotomique:
			# Les registres utilisees ici sont d'abord sauvegardees
			addi 	sp, sp, -24
			stw 	r16, 20(sp)
			stw		r17, 16(sp)
			stw 	r18, 12(sp)
			stw		r19, 8(sp)
			stw 	r20, 4(sp)
			stw 	r21, 0(sp)

			# Stocke tous les parametres dans des registres "locales"
			mov 	r16, r4
			mov 	r17, r5
			mov 	r18, r6
			mov 	r19, r7
			addi	r20, zero, -1
			# pos initialisee a -1
			
			# Si debut > fin, retour
			bgt		r18, r19, retourRechDicho
			
			# debut + (fin - debut) / 2 sauvegardee dans r20
			sub		r20, r19, r18
			addi	r2, zero, 2
			div 	r20, r20, r2
			add		r20, r20, r18

			# i * 4 car 4 octets
			slli	r21, r20, 2

			# Recupere un pointeur sur tab[pos] stockee ensuite dans r21
			add 	r21, r21, r20
			
			# Enfin, stocke la valeur situee a tab[pos] dans r21
			ldw 	r21, 0(r21)
			
			# Si cette valeur etait celle recherchee
			beq 	r21, r16, retourRechDicho
			
			# Si plus petite, recherche dans l'ensemble superieure
			mov		r4, r16
			mov 	r5, r17
			mov 	r6, r20
			addi 	r6, r6, 1
			mov 	r7, r19
			call 	rechercheDichotomique
			
			# Si plus grande, recherche dans l'ensemble inferieure
			mov		r4, r16
			mov 	r5, r17
			mov		r6, r18
			mov 	r7, r20
			subi	r7, r7, 1
			call 	rechercheDichotomique
			
			
# Retourne la valeur et replace le contenu des variables
retourRechDicho:
			# Stocke la valeur de retour (r20) dans celle recuperee par la suite (r2)
			mov 	r2, r20

			# Replace le contenu des registres utilisees par la fonction
			stw 	r16, 20(sp)
			stw		r17, 16(sp)
			stw 	r18, 12(sp)
			stw		r19, 8(sp)
			stw 	r20, 4(sp)
			stw 	r21, 0(sp)
			addi 	sp, sp, 24

			# Retour
			ret

			
			
			##############################
			#       Fonction main()      #
			##############################
			.globl main
main:		
boucle:
			# Imprime "Entrez un nombre: "
			movia	r4, msgNb
			addi	r2, zero, PRINT_STRING
			trap
			
			# Lit un nombre
			addi	r2, zero, READ_INT
			trap

			# Place les parametres dans les registres correspondants
			mov		r4, r2
			movia 	r5, tableau
			addi	r6, zero, 0
			addi	r7, zero, 99
			
			# Lance la fonction et stocke la valeur de retour
			call 	rechercheDichotomique
			mov 	r7, r2
			
			# Si non trouve
			blt 		r7, zero, nonTrouve
			
			# Si trouve, affiche la valeur
			movia 	r4, msgPos
			addi	r2, zero, PRINT_STRING
			trap
			mov 	r4, r7
			addi	r2, zero, PRINT_INT
			trap
	
			# Boucle
			br	boucle
			
	
# Si position non trouvee
nonTrouve:
			# Affiche juste un message
			movia 	r4, msgErreur
			addi	r2, zero, PRINT_STRING
			trap
			
			# Retour a la boucle ensuite
			br boucle
	

			.data
	
msgNb:		.asciz "Entrez un nombre: \n"
msgErreur:	.asciz "Nombre non trouve.\n"
msgPos:		.asciz "La position du nombre est: "
