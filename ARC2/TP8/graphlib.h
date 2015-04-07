#ifndef GRAPHLIB_H
#define GRAPHLIB_H

#include "altera_up_avalon_video_pixel_buffer_dma.h"

extern alt_up_pixel_buffer_dma_dev *pixel_buffer_dev;

int init_graph();

void clear_screen();

void swap_buffers();

void put_pixel(int x, int y, unsigned short val);

unsigned short rgb(unsigned char r, unsigned char g, unsigned char b);

void draw_image(unsigned short *img, int w, int h, int x, int y);

#endif
