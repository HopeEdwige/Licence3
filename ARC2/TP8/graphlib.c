#include <string.h>
#include <io.h>

#include "graphlib.h"

alt_up_pixel_buffer_dma_dev *pixel_buffer_dev;

int init_graph() {
	pixel_buffer_dev = alt_up_pixel_buffer_dma_open_dev ("/dev/VGA_Pixel_Buffer");
	if (!pixel_buffer_dev) return -1;
	alt_up_pixel_buffer_dma_change_back_buffer_address(pixel_buffer_dev, 0x08040000);
	return 0;
}

void swap_buffers() {
	alt_up_pixel_buffer_dma_swap_buffers(pixel_buffer_dev);
	while (alt_up_pixel_buffer_dma_check_swap_buffers_status(pixel_buffer_dev));
}

void put_pixel(int x, int y, unsigned short val) {
	if (x < 0 || x > 319 || y < 0 || y > 239) return;
	alt_up_pixel_buffer_dma_draw(pixel_buffer_dev, val, x, y);
}

unsigned short rgb(unsigned char r, unsigned char g, unsigned char b) {
	return (r>>3<<11) + (g>>2<<5) + (b<<3);
}

void clear_screen() {
	alt_up_pixel_buffer_dma_clear_screen(pixel_buffer_dev, 1);
}

void draw_image(unsigned short *src, int w, int h, int x, int y) {
	int left_bound, right_bound;
	int top_bound, bottom_bound;
	
	int last_x = x+w-1;
	int last_y = y+h-1;
	
	int i,j;

	// Quick check to see if the image is off screen.
	if (last_y < 0 || last_x < 0 || x > 319 || y > 239) return;
		
	// Compute intersection between [0, 319] and [x, last_x]
	// left_bound = max(0, x);
	if (x < 0) left_bound = 0;
	else left_bound = x;
	
	// right_bound = min(last_x, 319);
	if (319 < last_x) right_bound = 319;
	else right_bound = last_x;
	
	// Compute intersection between [0,239] and [y, last_y];
	// top_bound = max(0, y);
	if (y < 0) top_bound = 0;
	else top_bound = y;
	
	// bottom_bound = min(last_y, 239);
	if (239 < last_y) bottom_bound = 319;
	else bottom_bound = last_y;
	
	// Copying each line.
	for (i=top_bound; i<=bottom_bound; i++) {
		memcpy(
			((unsigned short *)pixel_buffer_dev->back_buffer_start_address) + (i<<9) + left_bound,
			//bb + (i<<9) + left_bound,
			src + (i-y)*320 + left_bound-x,
			(right_bound - left_bound + 1) << 1
		);

	}
}
