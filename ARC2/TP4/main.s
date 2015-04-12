			########################
			#	ANDRIAMILANTO T.   #
			#	 NGUYEN NHON B.	   #
			#					   #
			#	L3INFO - TP ARC2   #
			# 	 Calcul de PGCD    #
			########################
		
			.equ PRINT_INT, 1
			.equ PRINT_STRING, 4
			.equ READ_INT, 5

			.text
			.global main
		
main:
			# Invite à entrer les entiers
			movi	r2, PRINT_STRING
			movia	r4, invite
			trap

			# Lit x
			movi 	r2, READ_INT
			trap
			mov		r4, r2

			# Lit y
			movi 	r2, READ_INT
			trap
			mov		r5, r2
			
			# Lance la fonction
			call pdcd
			
			# Met la valeur de retour dans r10
			mov		r10, r2
			movi	r2, PRINT_STRING
			movia 	r4, message
			trap
			movi	r2, PRINT_INT
			mov		r4, r10
			trap
			ret
			
			# Fin du programme.


pgcd:
			# La fonction lance juste le while
			br while
			
			
while:		
			# Si a == b fin
			beq		r4, r5, finpgcd
			
			# Si 
			bge 	r5, r4, ygx
			sub		r4, r4, r5
			br while


ygx:
			sub		r5, r5, r4
			br while
			

finpgcd:			
			mov		r2, r4
		
		
			.data
resultat:	.word 0
invite:		.asciz "Entrez deux entiers: "
message:	.asciz "Le pgcd est : "

