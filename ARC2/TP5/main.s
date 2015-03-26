			##################################
			#	     L3INFO - TP ARC2        #
			# 	 Manipulation de tableaux    #
			##################################
		
			.equ PRINT_INT, 1
			.equ PRINT_STRING, 4
			.equ READ_INT, 5
			.equ EXIT,	10

			.text
			.global main

lectureTableau:
			addi sp, sp, -12
			stw	 r5, 8(sp)
			stw	 r4, 4(sp)
			stw	 zero, 0(sp)

forLecture:
			ldw		r18, 0(sp)
			ldw		r16, 4(sp)
			ldw		r17, 8(sp)
			bge 	r18, r17, finLecture
			movia 	r4, msgEntree
			addi 	r2, zero, PRINT_STRING
			trap
			addi 	r2, zero, READ_INT
			trap
			movia 	r19, r16
			slli 	r20, r18, 2
			add		r19, r19, r20
			stw		r2, 0(r19)
			addi	r18, r18, 1
			stw		r18, 0(sp)
			br forLecture

finLecture:
			addi	sp, sp, 12
			ret
			
affichageTableau:
			addi sp, sp, -12
			stw	 r5, 8(sp)
			stw	 r4, 4(sp)
			stw	 zero, 0(sp)

forAffichage:
			ldw		r18, 0(sp)
			ldw		r16, 4(sp)
			ldw		r17, 8(sp)
			bge 	r18, r17, finAffichage
			movia 	r19, r16
			slli 	r20, r18, 2
			add		r19, r19, r20
			ldw		r4, 0(r19)
			addi	r2, zero, PRINT_INT
			trap
			addi	r18, r18, 1
			stw		r18, 0(sp)
			br forAffichage

finAffichage:
			addi	sp, sp, 12
			ret
			
inversionTableau:
			addi	sp, sp, -12
			stw		r4, 8(sp)
			stw		zero, 4(sp)
			addi	r5, r5, -1
			stw 	r5, 0(sp)
			
whileInversion:
			ldw		r16, 0(sp)
			ldw		r17, 4(sp)
			ldw		r18, 8(sp)
			
			# Saut si fin du while (si i >= j)
			bge		r17, r16, finInversion
			
			# Choppe 
			# i*4
			slli 	r19, r17, 2
			# adresse de tableau[i]
			add 	r20, r18, r19
			# tmp <- tableau[i]
			ldw		r21, 0(r20)
			
			slli 	r19, r16, 2
			add		r22, r18, r19
			stw 	r21, 0(r22)
			
			
finInversion:
			ret
			
			
main:
			# Affichage du message "Lecture du tableau"
			movia	r4, msgLecture
			addi	r2,	zero, PRINT_STRING
			trap
			
			# Lecture du tableau
			movia r4, tableau
			addi r5, zero, 10
			call lectureTableau
			
			# Inverse le tableau
			movia r4, tableau
			addi r5, zero, 10
			call inversionTableau
			
			# Affichage du message "Affichage du tableau"
			movia	r4, msgAffiche
			addi	r2,	zero, PRINT_STRING
			trap

			# Affichage du tableau
			movia r4, tableau
			addi r5, zero, 10
			call affichageTableau
			
			# On rend la main au système.
			addi	r2, zero, EXIT
			trap
	
			.data
msgLecture: .asciz "Lecture du tableau.\n"
msgEntree: 	.asciz "Entrez un nombre.\n"
msgAffiche: .asciz "Affichage du tableau.\n"

			.align 4
			# Tableau de 10 éléments
tableau:	.word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0