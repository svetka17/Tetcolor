package com.tetcolor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

class Alfabeto {
	ArrayList<Alfabeto> list_lettera = new ArrayList<Alfabeto>(70);
	char lettera;//буква  
	float [] ml;
	FloatBuffer modelLettera;//
	TColor lettera_color;
	byte width_lettera;
	private float [] v;
	Alfabeto () {
		/*this.lettera='-';
		this.ml=new float[] {1,3, 2,3, 3,3, 4,3, 5,3};
		this.modelLettera=ByteBuffer.allocateDirect(ml.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.modelLettera.put(ml);
		this.lettera_color=new TColor();
		this.width_lettera=7;*/
		make_lettera();
	};
	
	Alfabeto (char lettera, float [] ml, byte width_lettera) {
		this.lettera=lettera;
		this.ml=ml;
		this.modelLettera=ByteBuffer.allocateDirect(ml.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.modelLettera.put(ml);
		this.lettera_color=new TColor();
		this.width_lettera=width_lettera;
	}
	
	void make_lettera () {
		//numero
		v = new float[] {0,4, 1,5, 2,6, 2,5, 2,4, 2,3, 2,2, 2,1, 2,0};
		list_lettera.add(new Alfabeto('1', v, (byte)4));
		v = new float[] {0,5, 1,6, 2,6, 3,5, 3,4, 2,3, 1,2, 0,1, 0,0, 1,0, 2,0, 3,0};
		list_lettera.add(new Alfabeto('2', v, (byte)4));
		v = new float[] {0,6, 1,6, 2,6, 3,6, 2,5, 1,4, 0,3, 1,3, 2,3, 3,2, 3,1, 2,0, 1,0, 0,0};
		list_lettera.add(new Alfabeto('3', v, (byte)4));
		v = new float[] {3,0, 3,1, 3,2, 3,3, 3,4, 3,5, 3,6, 2,5, 1,4, 0,3, 0,2, 1,2, 2,2};
		list_lettera.add(new Alfabeto('4', v, (byte)4));
		v = new float[] {3,6, 2,6, 1,6, 0,6, 0,5, 0,4, 0,3, 1,3, 2,3, 3,2, 3,1, 2,0, 1,0, 0,0};
		list_lettera.add(new Alfabeto('5', v, (byte)4));
		v = new float[] {3,6, 2,5, 1,4, 0,3, 0,2, 0,1, 1,0, 2,0, 3,1, 3,2, 2,3, 1,3};
		list_lettera.add(new Alfabeto('6', v, (byte)4));
		v = new float[] {0,6, 1,6, 2,6, 3,6, 3,5, 3,5, 2,4, 1,3, 0,2, 0,1, 0,0};
		list_lettera.add(new Alfabeto('7', v, (byte)4));
		v = new float[] {0,4, 0,5, 1,6, 2,6, 3,5, 3,4, 2,3, 1,3, 0,2, 0,1, 1,0, 2,0, 3,1, 3,2};
		list_lettera.add(new Alfabeto('8', v, (byte)4));
		v = new float[] {0,0, 1,1, 2,2, 3,3, 3,4, 3,5, 2,6, 1,6, 0,5, 0,4, 1,3, 2,3};
		list_lettera.add(new Alfabeto('9', v, (byte)4));
		v = new float[] {0,1, 0,2, 0,3, 0,4, 0,5, 1,6, 2,6, 3,5, 3,4, 3,3, 3,2, 3,1, 2,0, 1,0};
		list_lettera.add(new Alfabeto('0', v, (byte)4));
		v = new float[] {1,3, 2,3, 3,3, 4,3, 5,3};
		list_lettera.add(new Alfabeto('-', v, (byte)7));	
		//letteri
		v = new float[] {4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 3,6, 2,6, 1,5, 0,4, 0,3, 0,2, 0,1, 0,0, 1,2, 2,2, 3,2};
		list_lettera.add(new Alfabeto('А', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6, 1,3, 2,3, 3,2, 3,1, 2,0, 1,0};
		list_lettera.add(new Alfabeto('Б', v, (byte)4));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 1,3, 2,3, 3,2, 3,1, 2,0, 1,0, 3,5, 3,4};
		list_lettera.add(new Alfabeto('В', v, (byte)4));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6};
		list_lettera.add(new Alfabeto('Г', v, (byte)4));
		v = new float[] {0,-1, 0,0, 1,0, 2,0, 3,0, 4,0, 5,0, 6,0, 6,-1, 5,1, 5,2, 5,3, 5,4, 5,5, 5,6, 4,6, 3,6, 2,5, 2,4, 2,3, 1,2, 1,1};
		list_lettera.add(new Alfabeto('Д', v, (byte)7));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6, 1,3, 2,3, 3,3, 3,0, 2,0, 1,0};
		list_lettera.add(new Alfabeto('Е', v, (byte)4));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6, 1,3, 2,3, 3,3, 3,0, 2,0, 1,0, 1,7, 2,7};
		list_lettera.add(new Alfabeto('Ё', v, (byte)4));
		v = new float[] {0,0, 1,1, 2,2, 3,3, 4,4, 5,5, 6,6, 3,0, 3,1, 3,2, 3,4, 3,5, 3,6, 0,6, 1,5, 2,4, 4,2, 5,1, 6,0};
		list_lettera.add(new Alfabeto('Ж', v, (byte)7));
		v = new float[] {0,0, 3,4, 3,5, 0,3, 0,6, 1,6, 2,6, 1,3, 2,3, 3,2, 3,1, 2,0, 1,0};
		list_lettera.add(new Alfabeto('З', v, (byte)4));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 1,2, 2,3, 3,4};
		list_lettera.add(new Alfabeto('И', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 1,2, 2,3, 3,4, 2,7};
		list_lettera.add(new Alfabeto('Й', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 4,0, 4,1, 3,2, 2,2, 1,3, 2,4, 3,5, 4,6};
		list_lettera.add(new Alfabeto('К', v, (byte)5));
		v = new float[] {4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 3,6, 2,5, 2,4, 1,3, 1,2, 0,1, 0,0};
		list_lettera.add(new Alfabeto('Л', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 6,0, 6,1, 6,2, 6,3, 6,4, 6,5, 6,6, 1,5, 2,4, 3,3, 4,4, 5,5};
		list_lettera.add(new Alfabeto('М', v, (byte)7));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 1,3, 2,3, 3,3};
		list_lettera.add(new Alfabeto('Н', v, (byte)5));
		v = new float[] {0,1, 0,2, 0,3, 0,4, 0,5, 1,6, 1,0, 2,6, 3,6, 4,5, 4,4, 4,3, 4,2, 4,1, 3,0, 2,0};
		list_lettera.add(new Alfabeto('О', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 1,6, 2,6, 3,6};
		list_lettera.add(new Alfabeto('П', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,5, 3,4, 2,3, 1,3};
		list_lettera.add(new Alfabeto('Р', v, (byte)4));
		v = new float[] {0,1, 0,2, 0,3, 0,4, 0,5, 1,6, 2,6, 3,5, 3,1, 2,0, 1,0};
		list_lettera.add(new Alfabeto('С', v, (byte)4));
		v = new float[] {0,5, 0,6, 4,5, 4,6, 3,6, 2,6, 1,6, 2,0, 2,1, 2,2, 2,3, 2,4, 2,5};
		list_lettera.add(new Alfabeto('Т', v, (byte)5));
		v = new float[] {5,6, 5,5, 4,4, 4,3, 3,2, 3,1, 2,0, 1,0, 0,1, 2,2, 2,3, 1,4, 0,5, 0,6};
		list_lettera.add(new Alfabeto('У', v, (byte)6));
		v = new float[] {3,0, 3,1, 3,2, 3,3, 3,4, 3,5, 3,6, 4,6, 5,6, 6,5, 6,4, 5,3, 4,3, 2,6, 1,6, 0,5, 0,4, 1,3, 2,3};
		list_lettera.add(new Alfabeto('Ф', v, (byte)7));
		v = new float[] {5,0, 5,1, 4,1, 4,2, 3,2, 3,3, 2,3, 2,4, 1,4, 1,5, 0,5, 0,6, 0,0, 0,1, 1,1, 1,2, 2,2, 3,4, 4,4, 4,5, 5,5, 5,6};
		list_lettera.add(new Alfabeto('Х', v, (byte)6));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 4,-1, 5,-1, 5,-2, 1,0, 2,0, 3,0};
		list_lettera.add(new Alfabeto('Ц', v, (byte)5));
		v = new float[] {3,0, 3,1, 3,2, 3,3, 3,4, 3,5, 3,6, 6,1, 6,0, 6,2, 6,3, 6,4, 6,5, 6,6, 0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,0, 2,0, 4,0, 5,0};
		list_lettera.add(new Alfabeto('Ш', v, (byte)7));
		v = new float[] {3,0, 3,1, 3,2, 3,3, 3,4, 3,5, 3,6, 6,1, 6,2, 6,3, 6,4, 6,5, 6,6, 6,-1, 7,-1, 7,-2, 0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,0, 2,0, 4,0, 5,0};
		list_lettera.add(new Alfabeto('Щ', v, (byte)7));
		v = new float[] {0,4, 0,5, 0,6, 4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 1,3, 2,3, 3,3};
		list_lettera.add(new Alfabeto('Ч', v, (byte)5));
		v = new float[] {0,4, 0,5, 1,6, 2,6, 3,6, 4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6, 1,3, 2,3, 3,3, 2,2, 1,1, 1,0, 0,0};
		list_lettera.add(new Alfabeto('Я', v, (byte)5));
		v = new float[] {3,1, 3,2, 3,3, 3,4, 3,5, 4,6, 5,6, 6,5, 6,4, 6,3, 6,2, 6,1, 5,0, 4,0, 2,3, 1,3, 0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6};
		list_lettera.add(new Alfabeto('Ю', v, (byte)7));
		v = new float[] {0,6, 1,6, 2,6, 3,5, 4,4, 4,3, 4,2, 3,1, 2,0, 1,0, 0,0, 3,3, 2,3, 1,3};
		list_lettera.add(new Alfabeto('Э', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,3, 2,3, 3,2, 3,1, 2,0, 1,0};
		list_lettera.add(new Alfabeto('Ь', v, (byte)4));
		v = new float[] {1,0, 1,1, 1,2, 1,3, 1,4, 1,5, 1,6, 2,3, 3,3, 4,2, 4,1, 3,0, 2,0, 0,6, -1,6};
		list_lettera.add(new Alfabeto('Ъ', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,3, 2,3, 3,2, 3,1, 2,0, 1,0, 4,0, 4,1, 4,2, 4,3, 4,4, 4,5, 4,6};
		list_lettera.add(new Alfabeto('Ы', v, (byte)5));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,5, 3,4, 2,3, 1,3, 1,2, 2,1, 3,0};
		list_lettera.add(new Alfabeto('R', v, (byte)4));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,0, 2,0, 3,0, 3,1};
		list_lettera.add(new Alfabeto('L', v, (byte)3));
		v = new float[] {6,0};
		list_lettera.add(new Alfabeto('.', v, (byte)2));
		v = new float[] {6,0, 6,-1};
		list_lettera.add(new Alfabeto(',', v, (byte)2));
		v = new float[] {3,6, 3,5, 3,1, 3,0};
		list_lettera.add(new Alfabeto(':', v, (byte)2));
		v = new float[] {
				0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6, 4,6, 5,6, 6,6, 6,1, 6,2, 6,3, 6,4, 6,5, 6,0, 5,0, 4,0, 3,0, 2,0, 1,0,
				1,3, 2,3, 3,3, 4,3, 5,3,
				4,4, 3,5, 4,2, 3,1};
		list_lettera.add(new Alfabeto('>', v, (byte)7));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6, 4,6, 5,6, 6,6, 6,5, 6,4, 6,3, 6,2, 6,1, 6,0, 5,0, 4,0, 3,0, 2,0, 1,0,
				1,3, 2,3, 3,3, 4,3, 5,3,
				2,4, 3,5, 2,2, 3,1};
		list_lettera.add(new Alfabeto('<', v, (byte)7));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6, 4,6, 5,6, 6,6, 6,5, 6,4, 6,3, 6,2, 6,1, 6,0, 5,0, 4,0, 3,0, 2,0, 1,0,
				3,1, 3,2, 3,3, 3,4, 3,5,
				2,4, 1,3, 4,4, 5,3
				};
		list_lettera.add(new Alfabeto('^', v, (byte)7));
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6, 4,6, 5,6, 6,6, 6,5, 6,4, 6,3, 6,2, 6,1, 6,0, 5,0, 4,0, 3,0, 2,0, 1,0,
				3,1, 3,2, 3,3, 3,4, 3,5,
				2,2, 1,3, 4,2, 5,3};
		list_lettera.add(new Alfabeto('v', v, (byte)7));
		v = new float[] {0,2, 0,3, 0,4, 1,1, 1,5, 2,0, 2,6, 3,0, 3,6, 4,0, 4,6, 5,1, 5,5, 5,2, 4,2, 6,2, 6,1, 6,0};
		list_lettera.add(new Alfabeto('@', v, (byte)7));//поворот вправо
		v = new float[] {0,0, 0,2, 0,3, 0,1, 2,3, 3,3, 1,2, 1,3, 1,5, 2,6, 2,1, 3,6, 3,0, 4,6, 4,0, 5,5, 5,1, 6,2, 6,3, 6,4};
		list_lettera.add(new Alfabeto('&', v, (byte)7));//поворот влево 
		v = new float[] {3,6, 3,5, 3,1, 3,0, 3,2, 3,3};
		list_lettera.add(new Alfabeto('|', v, (byte)2));
		v = new float[] {0,2, 0,3, 0,4, 1,2, 1,3, 1,4, 3,1, 3,2, 3,3, 3,4, 3,5, 4,1, 4,2, 4,3, 4,4, 4,5, 5,0, 5,1, 5,2, 5,3, 5,4, 5,5, 5,6, 6,0, 6,1, 6,2, 6,3, 6,4, 6,5, 6,6};
		list_lettera.add(new Alfabeto('(', v, (byte)7));//звук выключен
		v = new float[] {0,3, 1,1, 1,5, 2,2, 2,3, 2,4, 3,0, 3,6, 4,1, 4,5, 5,2, 5,3, 5,4};
		list_lettera.add(new Alfabeto(')', v, (byte)6));//звук включен
		v = new float[] {};
		list_lettera.add(new Alfabeto(' ', v, (byte)6));//пробел
		v = new float[] {0,0, 0,1, 0,2, 0,3, 0,4, 0,5, 0,6, 1,6, 2,6, 3,6, 4,6, 5,6, 1,0, 2,0, 3,0, 4,0, 5,0, 6,5, 6,4, 6,3, 6,2, 6,1, 6,0, 6,6};
		list_lettera.add(new Alfabeto('#', v, (byte)7));//квадрат
	}

};