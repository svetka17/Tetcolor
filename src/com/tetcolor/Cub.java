package com.tetcolor;


class Cub {
	//static final Random rand = new Random();
	//FloatBuffer vertexModel, vertexModelDown;
	int line;
	int column;
	int color;
	int number;
	int tmp;

	public Cub() {
		this.line = 0;
		this.column = 0;
		this.color = 0;// new TColor(1, 1, 1, 1);
		this.number = 0;
		this.tmp = 0;
	}

	public Cub(int line, int column, int color, int number,	int tmp) {
		this.line = line;
		this.column = column;
		this.color = color;
		this.number = number;
		this.tmp = tmp;
	}
	
	
	public void update_line(int line) {
		this.line=line;
	}
	
	public void update_column(int column) {
		this.column=column;
	}
	
	public void update_tmc(int tmp) {
		this.tmp=tmp;
	}
}