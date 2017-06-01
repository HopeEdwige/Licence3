#include "mouse.h"
#include "altera_up_ps2_mouse.h"

#define INIT 1
#define ERROR 2
#define ACK 3
#define OK 4
#define ENABLING 5
#define MSG1 6
#define MSG2 7
#define MSG3 8


alt_up_ps2_dev *init_mouse() {
	alt_up_ps2_dev *mouse = alt_up_ps2_open_dev("/dev/PS2_Port");

	alt_up_ps2_init(mouse);
	alt_up_ps2_clear_fifo(mouse);

	alt_up_ps2_write_data_byte(mouse, 0xFF);

	int state = INIT;
	int status;

	while (state != ERROR && state != MSG1) {
		unsigned char data;
		do {
			status = alt_up_ps2_read_data_byte(mouse, &data);
		} while (status < 0);

		switch (state) {
			case INIT:
				if (data == 0xF4) {
					state = ACK;
					alt_printf("Mouse sent ACK (0xF4).");
				} else if (data == 0xAA) {
					state = OK;
					alt_printf("Mouse skipped ACK (0xF4).");
				} else {
					if (data == 0xFC) {
						state = ERROR;
						alt_printf("Mouse replied with an error message (0xFC).\n");
					} else {
						alt_printf("Mouse replied with an unknown message: 0x%x. Ignoring...\n", data);
					}
				}
				break;
			case ACK:
				if (data == 0xAA) {
					state = OK;
				} else {
					alt_printf("Mouse replied sent unknown message: %x. Expected 0xAA. Ignoring...\n");
				}
				break;
			case OK:
				if (data == 0x00) {
					state = ENABLING;
					alt_up_ps2_write_data_byte(mouse, 0xF4);;
					alt_printf("Mouse ready. Enabling position and button status messages...\n");
				} else {
					state = ACK; // Back to waiting for 0xAA00.
					alt_printf("Mouse replied sent unknown byte: %x. Expected 0x00. Ignoring...\n");
				}
				break;
			case ENABLING:
				if (data == 0xFA) {
					state = MSG1;
					alt_printf("OK ! Waiting for messages.\n");
				} else {
					alt_printf("Mouse replied sent unknown message: %x. Expected 0xF4. Ignoring...\n");
				}
				break;
		}
	}

	if (state == ERROR)
		return 0;

	return mouse;
}
