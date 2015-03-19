			.equ PRINT_INT,		1
			.equ PRINT_STRING,	4
			.equ READ_INT,		5
			.equ EXIT,		10

			.text

			####################################
			# Fonction rechercheDichotomique() #
			####################################
rechercheDichotomique:
			addi	r2, zero, -1
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

			# ...
			# Complétez l'implémentation  ici...
			# ...

	
			br	boucle

			.data
	
msgNb:		.asciz "Entrez un nombre: \n"
msgErreur:	.asciz "Nombre non trouve.\n"
msgPos:		.asciz "La position du nombre est: "
