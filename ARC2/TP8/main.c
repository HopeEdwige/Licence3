/* -----  ARC2 - TP8  -----
	ANDRIAMILANTO Tompoariniaina
	NGUYEN NHON Bérenger
*/

#include "nios2.h"
#include "system.h"
#include "sys/alt_irq.h"
#include "init.h"

#include "graphlib.h"
#include "cursor.h"
#include "nuages.h"

#define MOD(a,b) ((a)%(b)+(b))%(b)

volatile unsigned int* TIMER_STATUS = (unsigned int *) INTERVAL_TIMER_BASE;
volatile unsigned int* TIMER_CTRL = (unsigned int *) (INTERVAL_TIMER_BASE + 4);
volatile unsigned int* PS2_DATA = (unsigned int *) PS2_PORT_BASE;

// Question 1.1
void activer_interruptions() {

	// Get the current status
	unsigned int status;
	NIOS2_READ_STATUS(status);
	
	// The mask to put the first bit to 1
	unsigned int bit_a_un = 1;
	
	// Write new value
	NIOS2_WRITE_STATUS(bit_a_un | status);
}

// Question 1.2
void activer_interruption(int num) {

	// Get the current value
	unsigned int ienable;
	NIOS2_READ_IENABLE(ienable);

	// The mask used here
	unsigned int mask = 1;
	
	// Put the mask at the right position
	mask = mask << num;
	
	// Write the new value
	NIOS2_WRITE_IENABLE(mask | ienable);
}

int x_pos = 100, y_pos = 100;
int middle_pos = 0;

void tick() {
	middle_pos = MOD(middle_pos - 1, 320);
}

static void TIMER_ISR(void *context, alt_u32 id) {
	
	// Call the function to update the animation
	tick();
	
	// Interruption received
	*TIMER_STATUS = 0;
}

// Question 4.2
static void MOUSE_ISR(void *context, alt_u32 id)  {
	
	// Parameters
	int current_mouse_data = 0;
	int mouse_data[3];
	int tmp;

	// If we don't have the whole data
	while (current_mouse_data < 3) {
		tmp = *PS2_DATA;
		mouse_data[current_mouse_data] = tmp & 0x0001;
		++current_mouse_data;
	}
	
	// If we have the whole information
	x_pos = MOD(x_pos + mouse_data[1], 320);
	y_pos = MOD(y_pos - mouse_data[2], 240);
}

int main() {
	init();
	
	// Question 1.3
	activer_interruptions();
	
	// Question 2.2
	*TIMER_CTRL = 7;
	
	// Question 2.3
	activer_interruption(INTERVAL_TIMER_IRQ);
	
	// Question 4.1
	activer_interruption(PS2_PORT_IRQ);
	
	// Enregistrement des routines d'interruption.
	alt_irq_register ( INTERVAL_TIMER_IRQ, 0, TIMER_ISR );
	alt_irq_register( PS2_PORT_IRQ, 0, MOUSE_ISR );
	
	while(1) {
		clear_screen();
		draw_image((unsigned short *)nuages_img, 320, 240, middle_pos-320, 0);
		draw_image((unsigned short *)nuages_img, 320, 240, middle_pos, 0);
		draw_piou(x_pos, y_pos);
		swap_buffers();
	}
}