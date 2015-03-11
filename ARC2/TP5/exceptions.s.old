#include "syscalls.h"

	.equ PRINT_INT, 1
	.equ PRINT_STRING, 4
	.equ READ_INT, 5
	.equ READ_STRING, 8
	.equ PRINT_CHAR, 11
	.equ READ_CHAR, 12
	.equ EXIT, 10

	.global handle_exception
	.section .exceptions, "ax"
handle_exception:
	subi	sp, sp, 36
	stw		ra, 0(sp)
	stw		r2, 4(sp)
	stw		r3, 8(sp)
	stw		r4, 12(sp)
	stw		r5, 16(sp)
	stw		r6, 20(sp)
	stw		r7, 24(sp)
	stw		r8, 28(sp)
	stw		r9, 32(sp)
	
	ldw		r8, -4(ea)
	/* Mov "trap" opode into register */
	movhi	r9, 0x003b
    ori		r9, r9, 0x683a
    bne		r8, r9, not_trap

#  PRINT_INT
	movi	r8, PRINT_INT
	bne		r2, r8, NOT_PRINT_INT
	call	print_int
	br		done
NOT_PRINT_INT:

#  PRINT_STRING
	movi	r8, PRINT_STRING
	bne		r2, r8, NOT_PRINT_STRING
	call	print_string
	br		done
NOT_PRINT_STRING:

#  READ_INT
	movi	r8, READ_INT
	bne		r2, r8, NOT_READ_INT
	call	read_int
	br		done
NOT_READ_INT:

#  READ_STRING
	movi	r8, READ_STRING
	bne		r2, r8, NOT_READ_INT
	call	read_string
	br		done
NOT_READ_STRING:
	
#  PRINT_CHAR
	movi	r8, PRINT_CHAR
	bne		r2, r8, NOT_PRINT_CHAR
	call	print_char
	br		done
NOT_PRINT_CHAR:

#  READ_CHAR
	movi	r8, READ_CHAR
	bne		r2, r8, NOT_READ_CHAR
	call	print_char
	br		done
NOT_READ_CHAR:
	
	movi	r8, EXIT
	bne		r2, r8, NOT_EXIT
	break

NOT_EXIT:

	/* Unknown trap code. */
	break
	
not_trap:
	/* No exception handling code ! */
	break
	
done:
		
	ldw		ra, 0(sp)
#	ldw		r2, 4(sp)
	ldw		r3, 8(sp)
	ldw		r4, 12(sp)
	ldw		r5, 16(sp)
	ldw		r6, 20(sp)
	ldw		r7, 24(sp)
	ldw		r8, 28(sp)
	ldw		r9, 32(sp)
	addi	sp, sp, 36
	eret