package com.tetcolor;

import java.util.Random;

public class TColor {
	static Random rand = new Random();
	// rgba � ��������� �� 0 �� 1
	float r;
	float g;
	float b;
	float a;
	public TColor() {
		r = rand.nextFloat();
		g = rand.nextFloat();
		b = rand.nextFloat();
		a = 1;
	}

	public TColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
}