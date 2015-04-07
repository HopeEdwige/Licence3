#include "init.h"
#include "mouse.h"
#include "sys/alt_stdio.h"
#include "altera_up_ps2_mouse.h"
#include "nios2.h"

alt_up_ps2_dev *mouse;

void init() {
	NIOS2_WRITE_STATUS(0);
	NIOS2_WRITE_IENABLE(0);
	
	mouse = init_mouse();
	if (!mouse) {
		alt_printf("Failed to initialize mouse.\n");
	}
	alt_up_ps2_enable_read_interrupt(mouse);
	
	if (init_graph() < 0) {
		alt_printf("Failed to initialize VGA device.\n");
		return -1;
	}
}